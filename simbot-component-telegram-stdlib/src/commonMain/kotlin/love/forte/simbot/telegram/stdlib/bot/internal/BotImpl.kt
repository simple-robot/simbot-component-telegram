/*
 * Copyright (c) 2024. ForteScarlet.
 *
 * This file is part of simbot-component-telegram.
 *
 * simbot-component-telegram is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * simbot-component-telegram is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with simbot-component-telegram.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package love.forte.simbot.telegram.stdlib.bot.internal

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.http.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import love.forte.simbot.annotations.InternalSimbotAPI
import love.forte.simbot.common.collection.ExperimentalSimbotCollectionApi
import love.forte.simbot.common.collection.createConcurrentQueue
import love.forte.simbot.common.function.invokeWith
import love.forte.simbot.logger.LoggerFactory
import love.forte.simbot.telegram.Telegram
import love.forte.simbot.telegram.api.requestData
import love.forte.simbot.telegram.api.update.Update
import love.forte.simbot.telegram.api.update.UpdateValues
import love.forte.simbot.telegram.api.update.getUpdateFlow
import love.forte.simbot.telegram.stdlib.bot.Bot
import love.forte.simbot.telegram.stdlib.bot.BotConfiguration
import love.forte.simbot.telegram.stdlib.bot.LongPolling
import love.forte.simbot.telegram.stdlib.bot.SubscribeSequence
import love.forte.simbot.telegram.stdlib.event.Event
import love.forte.simbot.telegram.stdlib.event.EventProcessor
import kotlin.concurrent.Volatile
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration.Companion.seconds


/**
 *
 * @author ForteScarlet
 */
@OptIn(ExperimentalSimbotCollectionApi::class)
internal class BotImpl(
    override val ticket: Bot.Ticket,
    private val configuration: BotConfiguration,
) : Bot {
    private val logger = LoggerFactory.getLogger("love.forte.simbot.telegram.stdlib.bot")
    private val eventLogger = LoggerFactory.getLogger("love.forte.simbot.telegram.stdlib.bot.event")

    override val server: Url = configuration.server?.let(::Url) ?: Telegram.BaseServerUrl

    override val coroutineContext: CoroutineContext
    private val job: Job
    override val apiClient: HttpClient

    init {
        val coroutineContext = configuration.coroutineContext
        val job = SupervisorJob(coroutineContext[Job])
        this.coroutineContext = coroutineContext.minusKey(Job) + job
        this.job = job
        val apiClient = resolveHttpClient()
        job.invokeOnCompletion { apiClient.close() }
        this.apiClient = apiClient
    }

    private inline fun resolveHttpClient(crossinline block: HttpClientConfig<*>.() -> Unit = {}): HttpClient {
        val apiClientConfigurer = configuration.apiClientConfigurer

        val engine = configuration.apiClientEngine
        val engineFactory = configuration.apiClientEngineFactory
        if (engine != null && engineFactory != null) {
            throw IllegalArgumentException("`apiClientEngine` and `apiClientEngineFactory` can only have one that is not null.")
        }

        return when {
            engine == null && engineFactory == null -> HttpClient {
                apiClientConfigurer?.invokeWith(this)
                block()
            }

            engine != null -> HttpClient(engine) {
                apiClientConfigurer?.invokeWith(this)
                block()
            }

            engineFactory != null -> HttpClient(engineFactory) {
                apiClientConfigurer?.invokeWith(this)
                block()
            }

            else ->
                throw IllegalArgumentException()
        }
    }

    @Volatile
    private var eventChannel: Channel<Event>? = null

    private val preProcessors = createConcurrentQueue<EventProcessor>()
    private val processors = createConcurrentQueue<EventProcessor>()

    @Volatile
    override var isStarted: Boolean = false
        private set

    override val isActive: Boolean
        get() = job.isActive

    override val isCancelled: Boolean
        get() = job.isCancelled

    override val isCompleted: Boolean
        get() = job.isCompleted

    override fun subscribe(sequence: SubscribeSequence, processor: EventProcessor) {
        when (sequence) {
            SubscribeSequence.PRE -> preProcessors.add(processor)
            SubscribeSequence.NORMAL -> processors.add(processor)
        }
    }

    override suspend fun pushUpdate(update: Update, raw: String?) {
        job.ensureActive()
        val channel = eventChannel ?: throw IllegalStateException("Bot not started yet")
        val event = update.resolveToEvent(raw)

        channel.send(event)
    }

    private val startLock = Mutex()

    override suspend fun start() {
        startLock.withLock {
            job.ensureActive()
            if (isStarted) {
                return
            }

            initEventReceivingJob()
            initLongPollingTask()

            isStarted = true
        }
    }

    private fun initEventReceivingJob() {
        val channel = Channel<Event>()
        launch {
            channel.receiveAsFlow().collect { event ->
                eventLogger.debug("Received event: {}", event)
                processEvent(event)
            }
        }
        eventChannel = channel
    }

    private suspend fun processEvent(event: Event) {
        for (preProcessor in preProcessors) {
            kotlin.runCatching {
                preProcessor.process(event)
            }.onFailure { e ->
                eventLogger.error("PreProcessor {} with event {} on failure: {}", preProcessor, event, e.message, e)
            }
        }

        launch {
            for (processor in processors) {
                kotlin.runCatching {
                    processor.process(event)
                }.onFailure { e ->
                    eventLogger.error("Processor {} with event {} on failure: {}", processor, event, e.message, e)
                }
            }
        }
    }

    private val longPollingFlow = MutableStateFlow(configuration.longPolling)

    override var longPolling: LongPolling? by longPollingFlow::value

    @Volatile
    private var longPollingTask: LongPollingTask? = null

    private fun initLongPollingTask() {
        // init update
        val init = longPollingFlow.value
        updateLongPollingTask(init)
        launch {
            longPollingFlow
                .filter { it !== init }
                .collect { longPolling ->
                    updateLongPollingTask(longPolling)
                }
        }

        // job completion callback
        job.invokeOnCompletion {
            longPollingTask?.cancel()
            longPollingTask = null
        }
    }

    // in lock, or in single flow collect
    // 总之应该在不会出现并发的地方, 要按顺序调用
    @OptIn(InternalSimbotAPI::class)
    private fun CoroutineScope.updateLongPollingTask(longPolling: LongPolling?) {
        logger.debug("Updating polling task from {} to {}", longPollingTask?.longPolling, longPolling)
        longPollingTask?.cancel()
        if (longPolling == null) {
            longPollingTask = null
        } else {
            val timeout = longPolling.timeout?.seconds
            val reqTimeout = (timeout ?: BotConfiguration.DefaultLongPollingTimeout) + 5.seconds

            // 创建新的任务
            val newClient = resolveHttpClient {
                install(HttpTimeout) {
                    requestTimeoutMillis = reqTimeout.inWholeMilliseconds
                }
                longPolling.retry?.also { retry ->
                    install(HttpRequestRetry) {
                        // maxRetries = retry.maxRetries
                        retryOnException(maxRetries = retry.maxRetries, retryOnTimeout = true)

                        if (retry.isDelayMillisMultiplyByRetryTimes) {
                            delayMillis { retryTimes ->
                                (retry.delayMillis * retryTimes).also { millis ->
                                    eventLogger.debug("LongPolling next retry delay millis {} in retry {}", millis, retryTimes)
                                }
                            }
                        } else {
                            delayMillis { retryTimes ->
                                retry.delayMillis.also { millis ->
                                    eventLogger.debug("LongPolling next retry delay millis {} in retry {}", millis, retryTimes)
                                }
                            }
                        }
                    }
                }
            }

            val job = launch {
                newClient.longPolling(
                    ticket.token,
                    server,
                    null,
                    timeout?.inWholeSeconds?.toInt(),
                    longPolling.limit,
                    longPolling.allowedUpdates
                )
            }

            job.invokeOnCompletion {
                eventLogger.debug("LongPolling job {} completed: {}", job, it)
                newClient.close()
                longPollingFlow.value = null
            }

            longPollingTask = LongPollingTask(longPolling, job).also {
                eventLogger.debug("New long polling task updated: {}", it)
            }
        }
    }

    private data class LongPollingTask(
        val longPolling: LongPolling,
        val job: Job
    ) {
        fun cancel() {
            job.cancel()
        }
    }

    private suspend fun HttpClient.longPolling(
        token: String,
        server: Url?,
        firstOffset: Int?,
        timeout: Int?,
        limit: Int?,
        allowedUpdates: Collection<String>?,
    ) {
        val client = this
        getUpdateFlow(
            timeout = timeout,
            firstOffset = firstOffset,
            eachLimit = limit,
            allowedUpdates = allowedUpdates,
            onError = { error ->
                when (error) {
                    is CancellationException -> {
                        eventLogger.trace("Handle an cancellation error on long polling task: {}", error.message, error)
                        // throw, and stop the job.
                        throw error
                    }

                    is HttpRequestTimeoutException -> {
                        eventLogger.trace("Handle an timeout error on long polling task: {}", error.message, error)
                    }

                    else -> {
                        eventLogger.error("Handle an error on long polling task: {}", error.message, error)
                        // throw to Bot
                        job.cancel(CancellationException("LongPolling on failure", error))
                        throw error
                    }
                }

                emptyList()
            }) { api ->
            api.requestData(client, token, server)
        }.collect { update ->
            pushUpdate(update)
        }
    }

    override fun cancel(cause: Throwable?) {
        job.cancel(cause?.let { it as? CancellationException ?: CancellationException(it.message, it) })
    }

    override suspend fun join() {
        job.join()
    }
}


private data class EventImpl(
    override val name: String, override val content: Any, override val update: Update,
    override val raw: String?
) : Event

private fun Update.resolveToEvent(raw: String?): Event =
    UpdateValues.resolveTo(this) { name, value -> EventImpl(name, value, this, raw) }
