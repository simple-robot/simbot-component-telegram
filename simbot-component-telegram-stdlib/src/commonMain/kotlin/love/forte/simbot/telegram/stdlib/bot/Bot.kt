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

package love.forte.simbot.telegram.stdlib.bot

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.http.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.SerializationException
import love.forte.simbot.annotations.InternalSimbotAPI
import love.forte.simbot.common.function.ConfigurerFunction
import love.forte.simbot.common.function.invokeBy
import love.forte.simbot.suspendrunner.ST
import love.forte.simbot.telegram.Telegram
import love.forte.simbot.telegram.api.requestRaw
import love.forte.simbot.telegram.api.update.Update
import love.forte.simbot.telegram.api.update.getUpdateFlow
import love.forte.simbot.telegram.stdlib.bot.BotConfiguration.Companion.DefaultLongPollingTimeout
import love.forte.simbot.telegram.stdlib.event.Event
import love.forte.simbot.telegram.stdlib.event.EventProcessor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes


/**
 * A Telegram Bot.
 *
 *
 * ## Process Event
 *
 * Instead of using [subscribe] directly,
 * you can also register event handlers using a convenient extension function.
 *
 * **[Bot.subscribe]**
 *
 * ```kotlin
 * bot.process<Message> { event: Event, content: Message ->
 *     // ...
 * }
 * ```
 *
 * See [Bot.subscribe] for more information.
 *
 * **`Bot.onXxx`**
 *
 * For example: [Bot.onEditedMessage]
 *
 * ```kotlin
 * bot.onEditedMessage { event, editedMessage ->
 *     // ...
 * }
 * ```
 *
 * See [Bot.onEditedMessage] (Or other similar functions, which are automatically generated) for more information.
 *
 * @author ForteScarlet
 */
public interface Bot : CoroutineScope {
    override val coroutineContext: CoroutineContext

    /**
     * Ticket.
     */
    public val ticket: Ticket

    /**
     * The server address used by the Bot.
     * If [BotConfiguration.server] is `null`,
     * [Telegram.BaseServerUrl] is returned.
     */
    public val server: Url

    /**
     * Whether the launch [start] was successfully executed once.
     */
    public val isStarted: Boolean

    /**
     * Similar to [Job.isActive]
     */
    public val isActive: Boolean

    /**
     * Similar to [Job.isCancelled]
     */
    public val isCancelled: Boolean

    /**
     * Similar to [Job.isCompleted]
     */
    public val isCompleted: Boolean

    /**
     * [HttpClient] used by the Bot to make API requests internally.
     *
     */
    public val apiClient: HttpClient

    /**
     * Register a processor with the specified sequence level for subscribing to events.
     *
     * @param sequence The subscribe sequence level. The default is [SubscribeSequence.NORMAL].
     * @see SubscribeSequence
     */
    public fun subscribe(sequence: SubscribeSequence, processor: EventProcessor)

    /**
     * Register a processor with the [SubscribeSequence.NORMAL] sequence level for subscribing to events.
     *
     *  @see SubscribeSequence
     */
    public fun subscribe(processor: EventProcessor) {
        subscribe(SubscribeSequence.NORMAL, processor)
    }

    /**
     * Gets or modifies long polling config.
     *
     * When set to `null`,
     * the internal long polling task will be terminated;
     * when modified to a non-null value,
     * the internal long polling task will be enabled or reconfigured.
     *
     * If [isStarted] == `false`,
     * the modified value will be retained,
     * but the long-polling task is not started.
     *
     * If [isActive] == `false`, the change is invalid.
     *
     * The first long-polling task appears either
     * when start is used (the initial [longPolling] is not null)
     * or after start (the initial [longPolling] is null).
     *
     * Internally a [HttpClient] is configured based on the
     * [apiClient]'s engine and [LongPolling.timeout].
     *
     * Since creating an [HttpClient] is not cheap,
     * do not change the value of [longPolling] too often.
     *
     * Modifications and reads are thread-safe
     * and are implemented internally on top of [StateFlow].
     *
     * However, the changes do not take effect in real time.
     * If you make multiple changes before the last modified task completes,
     * only the most recent value will be detected afterward.
     */
    public var longPolling: LongPolling?

    /**
     * Proactively pushes an Update and triggers internal event handling.
     *
     * ```
     *        [ Update ]
     *            |
     *            v
     * [ pre event processors ] (sequentially)
     *            |
     *            | (launch)
     *            v
     * [ event processors ] (asynchronously)
     * ```
     *
     * Long polling also uses [pushUpdate] to push events.
     *
     * @param raw see [Event.raw]
     * @see Update
     * @see Update.decodeFromRawJson
     *
     * @throws IllegalStateException if [isStarted] == false
     * @throws CancellationException if [isActive] == false
     */
    @ST
    public suspend fun pushUpdate(update: Update, raw: String? = null)

    /**
     * Start this bot.
     *
     * Repeating [start] has no effect and the Bot can only start once.
     *
     * [start] is mainly related to [longPolling].
     * Refer to the [longPolling] for more information.
     *
     * @throws CancellationException if [isActive] == `false`
     */
    @ST
    public suspend fun start()

    /**
     * Close the Bot.
     * A closed Bot cannot be [started][start] again,
     * nor can it push or process events.
     *
     * Also closes [apiClient] and the internal long polling client.
     */
    public fun cancel(cause: Throwable?)

    /**
     * Close the Bot.
     * A closed Bot cannot be [started][start] again,
     * nor can it push or process events.
     *
     * Also closes [apiClient] and the internal long polling client.
     */
    public fun cancel() {
        cancel(null)
    }

    /**
     * Suspend until bot is canceled
     */
    @ST(asyncBaseName = "asFuture", asyncSuffix = "")
    public suspend fun join()

    /**
     * Bot ticket.
     *
     * @property token Bot full token. e.g.: `bot123456.aaabbbccc`
     */
    public data class Ticket(val token: String)
}


/**
 * A configuration for [Bot].
 *
 * @author ForteScarlet
 */
public class BotConfiguration {
    /**
     * The [CoroutineContext] provided to the Bot.
     * If there is no [Job],
     * one will be created, otherwise it will be used as a parent Job for the internal [Job].
     *
     */
    public var coroutineContext: CoroutineContext = EmptyCoroutineContext

    /**
     * A [HttpClientEngine] of [Bot] for API requests [HttpClient].
     * If `null` then an attempt will be made to create one using [`HttpClient()`][HttpClient].
     *
     * [apiClientEngine] and [apiClientEngineFactory] can only select one, setting one will set the other to null.
     * And in the verification link will also be detected, if they exist at the same time will throw an exception.
     */
    public var apiClientEngine: HttpClientEngine? = null
        set(value) {
            if (value != null) {
                field = value
                apiClientEngineFactory = null
            } else {
                field = null
            }
        }

    /**
     * A [HttpClientEngineFactory] of [Bot] for API requests [HttpClient].
     * If `null` then an attempt will be made to create one using [`HttpClient()`][HttpClient].
     *
     * [apiClientEngine] and [apiClientEngineFactory] can only select one, setting one will set the other to null.
     * And in the verification link will also be detected, if they exist at the same time will throw an exception.
     */
    public var apiClientEngineFactory: HttpClientEngineFactory<*>? = null
        set(value) {
            if (value != null) {
                field = value
                apiClientEngine = null
            } else {
                field = null
            }
        }

    /**
     * A configurer function for API requests [HttpClient].
     */
    public var apiClientConfigurer: ConfigurerFunction<HttpClientConfig<*>>? = null

    /**
     * config with [apiClientConfigurer].
     */
    public fun apiClientConfigurer(configurer: ConfigurerFunction<HttpClientConfig<*>>): BotConfiguration = apply {
        apiClientConfigurer = apiClientConfigurer?.let { old ->
            ConfigurerFunction {
                invokeBy(old)
                invokeBy(configurer)
            }
        } ?: configurer
    }

    /**
     * Used for the `server` parameter in API requests.
     * Default: [Telegram.BASE_SERVER_VALUE]
     *
     * @see requestRaw
     */
    public var server: String? = null

    /**
     * Whether to use the **initial value** for long polling config,
     * if `null` then it is not enabled by default.
     *
     * @see Bot.longPolling
     */
    public var longPolling: LongPolling? = null

    // TODO exception handler?

    public companion object {
        /**
         * Default value for long polling timeout: 30 minutes.
         * (includes [LongPolling] and internal long polling [HttpClient])
         */
        @InternalSimbotAPI
        public val DefaultLongPollingTimeout: Duration = 30.minutes
    }
}

public enum class SubscribeSequence {
    /**
     * The preprocessing sequence level.
     *
     * All processors at this level are executed one after another in synchronous order.
     * The logic in the PRE level should be as fast as possible.
     *
     * This level is designed to perform some pre-processing (such as synchronous caching, etc.)
     * and should not be used to execute business logic.
     */
    PRE,

    /**
     * The normal sequence level.
     * Always executes asynchronously after [PRE].
     */
    NORMAL;
}

/**
 * Long polled configurations.
 *
 * The meanings of most parameters are similar to those described in [getUpdateFlow].
 * The difference is that [timeout] defaults to [DefaultLongPollingTimeout]
 * instead of `null`.
 *
 * @see getUpdateFlow
 */
@OptIn(InternalSimbotAPI::class)
public data class LongPolling(
    val limit: Int? = null,
    val timeout: Int? = DefaultLongPollingTimeout.inWholeSeconds.toInt(),
    val allowedUpdates: Collection<String>? = null,
)

/**
 * [Bot.subscribe] simplified extension.
 * Matches events based on type [T] (and optional name).
 *
 * Note: If the matched type does not match the name, the result may never be matched.
 *
 * ```kotlin
 * bot.subscribe<Message> { event: Event, content: Message ->
 *      // ...
 * }
 *
 * bot.subscribe<Message>("edited_message", sequence = ...) { event: Event, event: Message ->
 *      // ...
 * }
 *
 * bot.subscribe<Message>(UpdateValues.EDITED_MESSAGE_NAME, sequence = ...) { event: Event, event: Message ->
 *      // ...
 * }
 * ```
 *
 * @see Bot.subscribe
 * @see EventProcessor.process
 */
public inline fun <reified T> Bot.subscribe(
    name: String? = null,
    sequence: SubscribeSequence = SubscribeSequence.NORMAL,
    crossinline processor: suspend (Event, T) -> Unit
) {
    if (name != null) {
        subscribe(sequence) { event ->
            event.content.also { content ->
                if (name == event.name && content is T) {
                    processor(event, content)
                }
            }
        }
    } else {
        subscribe(sequence) { event ->
            event.content.also { content ->
                if (content is T) {
                    processor(event, content)
                }
            }
        }
    }
}

/**
 * Push a [Update] raw JSON string.
 *
 * @throws SerializationException see [Update.decodeFromRawJson]
 * @throws IllegalArgumentException see [Update.decodeFromRawJson]
 */
public suspend fun Bot.pushRawUpdate(raw: String) {
    pushUpdate(Update.decodeFromRawJson(raw), raw)
}
