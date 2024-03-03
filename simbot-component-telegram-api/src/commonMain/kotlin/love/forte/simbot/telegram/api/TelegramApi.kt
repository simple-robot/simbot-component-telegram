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
import kotlinx.serialization.DeserializationStrategy


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
 * @see EmptyBodyTelegramApi
 * @see JsonBodyTelegramApi
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

    public companion object {

    }
}

public abstract class EmptyBodyTelegramApi<R : Any> : TelegramApi<R>() {
    override val body: Unit?
        get() = null
}

public abstract class JsonBodyTelegramApi<R : Any> : TelegramApi<R>()

public abstract class FormBodyTelegramApi<R : Any> : TelegramApi<R>() {
    abstract override val body: MultiPartFormDataContent
}
