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

package love.forte.simbot.telegram.api.update

import io.ktor.client.*
import io.ktor.client.plugins.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import love.forte.simbot.telegram.api.SimpleBodyTelegramApi
import love.forte.simbot.telegram.api.TelegramApiResult
import love.forte.simbot.telegram.api.requestData
import kotlin.jvm.JvmStatic
import kotlin.math.max


/**
 * [getUpdates](https://core.telegram.org/bots/api#getupdates)
 *
 * Use this method to receive incoming updates using long polling ([wiki](https://en.wikipedia.org/wiki/Push_technology#Long_polling)).
 * Returns an Array of [Update] objects.
 *
 * > Notes
 * > 1. This method will not work if an outgoing webhook is set up.
 * > 2. In order to avoid getting duplicate updates,
 * recalculate offset after each server response.
 *
 * To create a long polling as a [Flow], see [getUpdateFlow].
 *
 * @see getUpdateFlow
 *
 * @author ForteScarlet
 */
public class GetUpdatesApi private constructor(body: Body) : SimpleBodyTelegramApi<List<Update>>() {
    public companion object Factory {
        private const val NAME = "getUpdates"
        private val L_SER = ListSerializer(Update.serializer())
        private val R_SER = TelegramApiResult.serializer(L_SER)
        private val EMPTY_BODY = Body()
        private val INSTANCE = GetUpdatesApi(EMPTY_BODY)

        /**
         * Create an instance of [GetUpdatesApi].
         */
        @JvmStatic
        public fun create(): GetUpdatesApi = INSTANCE

        /**
         * Create an instance of [GetUpdatesApi] via [body].
         */
        @JvmStatic
        public fun create(body: Body): GetUpdatesApi =
            if (EMPTY_BODY == body) INSTANCE else GetUpdatesApi(body)


    }

    override val name: String
        get() = NAME

    override val body: Any = body

    override val responseDeserializer: DeserializationStrategy<List<Update>>
        get() = L_SER

    override val resultDeserializer: DeserializationStrategy<TelegramApiResult<List<Update>>>
        get() = R_SER

    /**
     * Parameters of [GetUpdatesApi]
     * @property offset Optional.
     * Identifier of the first update to be returned.
     * Must be greater by one than the highest among the identifiers of previously received updates.
     * By default, updates starting with the earliest unconfirmed update are returned.
     * An update is considered confirmed as soon as getUpdates is called with an offset higher than its update_id.
     * The negative offset can be specified to retrieve updates starting from -offset update from the end of the updates queue. All previous updates will be forgotten.
     * @property limit Optional.
     * Limits the number of updates to be retrieved.
     * Values between 1-100 are accepted. Defaults to 100.
     * @property timeout Optional.
     * Timeout in seconds for long polling.
     * Defaults to 0, i.e. usual short polling. Should be positive, short polling should be used for testing purposes only.
     * @property allowedUpdates Optional.
     * A JSON-serialized list of the update types you want your bot to receive.
     * For example, specify `["message", "edited_channel_post", "callback_query"]`
     * to only receive updates of these types. See Update for a complete list of available update types.
     * Specify an empty list to receive all update types except chat_member, message_reaction,
     * and message_reaction_count (default). If not specified, the previous setting will be used.
     *
     * Please note that this parameter doesn't affect updates created before the call to the getUpdates,
     * so unwanted updates may be received for a short period of time.
     */
    @Serializable
    public data class Body(
        val offset: Int? = null,
        val limit: Int? = null,
        val timeout: Int? = null,
        @SerialName("allowed_updates")
        val allowedUpdates: Collection<String>? = null,
    )
}

/**
 * Create a [Flow] based on Long Polling with [GetUpdatesApi].
 *
 * @receiver The [HttpClient] to call this **long polling**.
 * It is recommended to configure a larger `requestTimeoutMillis` value,
 * at least larger than the milliseconds value corresponding to timeout.
 * Otherwise, an [HttpRequestTimeoutException] is likely to be thrown.
 * ([HttpRequestTimeoutException] will be caught by default [onError].
 *
 * @param token see `token` in [requestData]
 * @param server see `server` in [requestData]
 * @param timeout see [GetUpdatesApi.Body.timeout]
 * @param limit see [GetUpdatesApi.Body.limit].
 * It is recommended to set a larger value,
 * and its millis value should be smaller than
 * the `requestTimeoutMillis` of the [receiver client][HttpClient].
 * @param allowedUpdates see [GetUpdatesApi.Body.allowedUpdates]
 * @param onEachResult Handle each API result.
 * Default: `{ it }`: return itself.
 * @param onError Handle exceptions during API requests.
 * Default: `{ if (it is HttpRequestTimeoutException) emptyList() else throw it }`:
 * if it is [HttpRequestTimeoutException], ignore. Otherwise, throw it.
 */
public inline fun HttpClient.getUpdateFlow(
    token: String,
    server: String? = null,
    timeout: Int? = null,
    limit: Int? = null,
    allowedUpdates: Collection<String>? = null,
    crossinline onEachResult: (List<Update>) -> List<Update> = { it },
    crossinline onError: (Throwable) -> List<Update> = { if (it is HttpRequestTimeoutException) emptyList() else throw it }
): Flow<Update> {
    val client = this
    return flow {
        var api: GetUpdatesApi
        var offset: Int? = null

        while (true) {
            api = GetUpdatesApi.create(
                GetUpdatesApi.Body(
                    offset = offset,
                    limit = limit,
                    timeout = timeout,
                    allowedUpdates = allowedUpdates
                )
            )

            val updates = kotlin.runCatching {
                api.requestData(client, token, server).let(onEachResult)
            }.getOrElse { onError(it) }

            if (updates.isEmpty()) {
                continue
            }

            var maxUpdateId: Int = Int.MIN_VALUE
            updates.forEach {
                maxUpdateId = max(it.updateId, maxUpdateId)
                emit(it)
            }
            offset = maxUpdateId + 1
        }
    }
}


