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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import love.forte.simbot.annotations.InternalSimbotAPI
import love.forte.simbot.suspendrunner.ST
import love.forte.simbot.telegram.Telegram
import love.forte.simbot.telegram.api.requestRaw
import love.forte.simbot.telegram.api.update.Update
import love.forte.simbot.telegram.api.update.getUpdateFlow
import love.forte.simbot.telegram.stdlib.bot.BotConfiguration.Companion.DefaultLongPollingTimeout
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.jvm.JvmSynthetic
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes


/**
 * A Telegram Bot.
 *
 * @author ForteScarlet
 */
public interface Bot : CoroutineScope {
    override val coroutineContext: CoroutineContext

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
     * Takes precedence over all normal [EventProcessor] ([registerEventProcessor])
     * and is executed during [pushUpdate] suspension.
     * It is suitable for some pre-processing behavior,
     * and it needs to ensure that the number of concurrent transactions is only one.
     *
     * The action should be done quickly,
     * and the resulting exception will be thrown directly in [pushUpdate].
     */
    public fun registerPreEventProcessor(processor: EventProcessor)

    // TODO Doc
    public fun registerEventProcessor(processor: EventProcessor)

    /**
     * Gets or modifies long polling config.
     *
     * When set to `null`,
     * the internal long polling task will be terminated;
     * when modified to a non-null value,
     * the internal long polling task will be enabled or reconfigured.
     *
     * If [isStarted] = false,
     * the modified value will be retained,
     * but the long-polling task is not started.
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
     */
    public var longPolling: LongPolling?

    /**
     * Proactively pushes an Update and triggers internal event handling.
     *
     * ```
     * push
     *  |  (suspend)
     *  |-------------+
     *  |             |
     *  |             v
     *  |  [ pre event processors ] (sequentially)
     *  |             |
     * push <---------+---| (launch)
     *       (resume)     |
     *                    v
     *         [ event processors ] (asynchronously)
     * ```
     *
     * Long polling also uses [pushUpdate] to push events.
     *
     */
    @ST
    public suspend fun pushUpdate(update: Update)

    /**
     * Start this bot.
     *
     * Repeating [start] has no effect and the Bot can only start once.
     *
     * [start] is mainly related to [longPolling].
     * Refer to the [longPolling] for more information.
     */
    @ST
    public suspend fun start()

    /**
     * Close the Bot.
     * A closed Bot cannot be [started][start] again,
     * nor can it push or process events.
     *
     * If the [apiClient] is generated internally,
     * it is closed with [cancel],
     * otherwise it needs to be done manually.
     */
    public fun cancel()
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
     * Bot for API requests [HttpClient].
     * If `null` then an attempt will be made to create one using [`HttpClient()`][HttpClient].
     *
     */
    public var apiClient: HttpClient? = null

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
         * Default value for long polling timeout
         * (includes [LongPolling] and internal long polling [HttpClient])。
         */
        @InternalSimbotAPI
        public val DefaultLongPollingTimeout: Duration = 30.minutes
    }
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
 * ```kotlin
 * bot.process<Message> { update, name, event: Message ->
 *      // ...
 * }
 *
 * bot.process<Message>("edited_message") { update, name, event: Message ->
 *      // ...
 * }
 *
 * bot.process<Message>(UpdateValues.EDITED_MESSAGE_NAME) { update, name, event: Message ->
 *      // ...
 * }
 * ```
 *
 * @see Bot.registerEventProcessor
 * @see EventProcessor.process
 */
@JvmSynthetic
public inline fun <reified T> Bot.process(
    name: String? = null,
    crossinline processor: suspend (Update, String, T) -> Unit
) {
    if (name != null) {
        registerEventProcessor { update, eventName, value ->
            if (name == eventName && value is T) {
                processor(update, eventName, value)
            }
        }
    } else {
        registerEventProcessor { update, eventName, value ->
            if (value is T) {
                processor(update, eventName, value)
            }
        }
    }
}

@JvmSynthetic
public inline fun <reified T : Any> Bot.preProcess(
    name: String? = null,
    crossinline processor: suspend (Update, String, T) -> Unit
) {
    if (name != null) {
        registerPreEventProcessor { update, eventName, value ->
            if (name == eventName && value is T) {
                processor(update, eventName, value)
            }
        }
    } else {
        registerPreEventProcessor { update, eventName, value ->
            if (value is T) {
                processor(update, eventName, value)
            }
        }
    }
}

