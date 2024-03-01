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

@file:JvmName("ApiRequests")
@file:JvmMultifileClass

package love.forte.simbot.telegram.api

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import love.forte.simbot.telegram.Telegram
import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName
import kotlin.jvm.JvmSynthetic

/**
 * Request the [Telegram Method][method] by [client] with [token].
 *
 * @param client The [HttpClient]
 * @param token The bot token in the query API (should with the prefix `bot`).
 * e.g. `"bot<token>"` of `https://api.telegram.org/bot<token>/METHOD_NAME`.
 * @param server The query API server (e.g. `"https://example.com"`).
 * Default is [Telegram.BASE_SERVER_VALUE].
 */
@JvmSynthetic
public suspend inline fun <reified T, R : Any> TelegramApi<T, R>.requestRaw(
    client: HttpClient,
    token: String,
    server: String? = null,
): HttpResponse {
    val builder = HttpRequestBuilder()
    builder.url {
        if (server == null) takeFrom(Telegram.BaseServerUrl)
        else takeFrom(server)

        appendEncodedPathSegments(token, this@requestRaw.name)
    }
    // Header Accept
    builder.headers {
        append(HttpHeaders.Accept, ContentType.Application.Json)
    }
    when (val method = this) {
        is EmptyBodyTelegramApi -> {
            // Just do get
            builder.method = HttpMethod.Get
        }

        is JsonBodyTelegramApi -> {
            builder.method = HttpMethod.Post
            builder.headers.append(HttpHeaders.ContentType, ContentType.Application.Json)
            builder.setBody(method.body)
        }

        is FormBodyTelegramApi -> {
            builder.method = HttpMethod.Post
            builder.headers.append(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded)
            builder.setBody(method.body)
        }
    }

    return client.request(builder)
}

