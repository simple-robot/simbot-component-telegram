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

package love.forte.simbot.telegram.api

import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.http.content.*
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy


/**
 *
 * A [Telegram Method](https://core.telegram.org/bots/api#available-methods).
 *
 * > All methods in the Bot API are case-insensitive.
 * > We support GET and POST HTTP methods.
 * > Use either URL query string or `application/json`
 * > or `application/x-www-form-urlencoded`
 * > or `multipart/form-data` for passing parameters in Bot API requests.
 * > On successful call, a JSON-object containing the result will be returned.
 *
 * Use [requestRaw] to request an API with method.
 *
 * @see [Making requests](https://core.telegram.org/bots/api#making-requests)
 * @see EmptyBodyTelegramApi
 * @see SimpleBodyTelegramApi
 * @see FormBodyTelegramApi
 *
 * @author ForteScarlet
 */
public sealed class TelegramApi<R : Any> {
    /**
     * The method name.
     */
    public abstract val name: String

    /**
     * The request body (or null).
     */
    public abstract val body: Any?

    /**
     * A special deserialization for [body].
     * If it is `null`, then try to 'guess' the body serializer.
     * This is usually overridden when the body is not `null` and you need to customize its serializer.
     *
     * Default is `null`.
     */
    public open val bodySerializationStrategy: SerializationStrategy<Any>?
        get() = null


    /**
     * The result's [DeserializationStrategy] of this Method.
     */
    public abstract val responseDeserializer: DeserializationStrategy<R>

    /**
     * The [TelegramApiResult]'s [DeserializationStrategy] of this Method.
     */
    public abstract val resultDeserializer: DeserializationStrategy<TelegramApiResult<R>>

    /**
     * Headers for request.
     * Default is [Headers.Empty].
     */
    public open val headers: Headers
        get() = Headers.Empty
}

/**
 * [body] is always `null` [TelegramApi].
 * Will use a `GET` request directly.
 */
public abstract class EmptyBodyTelegramApi<R : Any> : TelegramApi<R>() {
    final override val body: Unit?
        get() = null
}

/**
 * An ordinary extensible [TelegramApi].
 * `Post` is used if [body] is not null,
 * `application/json` is used if [body] is not [MultiPartFormDataContent],
 * and `application/x-www-form-urlencoded` is used otherwise.
 *
 * If [body] is [OutgoingContent] other than [MultiPartFormDataContent],
 * remember to override [headers] to provide the necessary information as needed.
 */
public abstract class SimpleBodyTelegramApi<R : Any> : TelegramApi<R>()

/**
 * A [SimpleBodyTelegramApi] whose [body] is explicitly [MultiPartFormDataContent].
 */
public abstract class FormBodyTelegramApi<R : Any> : SimpleBodyTelegramApi<R>() {
    abstract override val body: MultiPartFormDataContent

    /**
     * [MultiPartFormDataContent] does not require a serializer. Always get `null`.
     */
    final override val bodySerializationStrategy: SerializationStrategy<Any>?
        get() = null
}
