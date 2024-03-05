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
import io.ktor.http.content.*
import love.forte.simbot.common.serialization.guessSerializer
import love.forte.simbot.telegram.Telegram
import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName
import kotlin.jvm.JvmSynthetic

/**
 * Request the [TelegramApi] by [client] with [token].
 *
 * @param client The [HttpClient]
 * @param token The bot token in the query API (should with the prefix `bot`).
 * e.g. `"bot<token>"` of `https://api.telegram.org/bot<token>/METHOD_NAME`.
 * @param server The query API server (e.g. `"https://example.com"`).
 * Default is [Telegram.BASE_SERVER_VALUE].
 */
@JvmSynthetic
public suspend fun TelegramApi<*>.requestRaw(
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
        val headers = headers
        if (!headers.isEmpty()) {
            appendAll(headers)
        }
    }

    val body = body
    if (body == null) {
        builder.method = HttpMethod.Get
    } else {
        builder.method = HttpMethod.Post
        when (body) {
            is OutgoingContent -> {

            }
            else -> {
                // JSON body.
                if (HttpHeaders.ContentType !in builder.headers) {
                    builder.headers.append(HttpHeaders.ContentType, ContentType.Application.Json)
                }


            }
        }
    }

    when (val api = this) {
        is EmptyBodyTelegramApi -> {
            // Do get
            builder.method = HttpMethod.Get
        }

        is SimpleBodyTelegramApi -> {
            when (val b = api.body) {
                null -> {
                    builder.method = HttpMethod.Get
                }

                is OutgoingContent -> {
                    builder.method = HttpMethod.Post
                    if (b is MultiPartFormDataContent && HttpHeaders.ContentType !in builder.headers) {
                        builder.headers.append(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded)
                    }

                    builder.setBody(b)
                }

                else -> {
                    builder.method = HttpMethod.Post
                    if (HttpHeaders.ContentType !in builder.headers) {
                        builder.headers.append(HttpHeaders.ContentType, ContentType.Application.Json)
                    }
                    val serializer = bodySerializationStrategy ?: guessSerializer(b, Telegram.DefaultJson.serializersModule)
                    builder.setBody(Telegram.DefaultJson.encodeToString(serializer, b))
                }
            }
        }
    }

    return client.request(builder)
}

/**
 * Request the [TelegramApi] by [client] with [token].
 *
 * @param client see `client` in [requestRaw]
 * @param token see `token` in [requestRaw]
 * @param server see `server` in [requestRaw]
 * @throws TelegramApiResultNotOkException
 */
@JvmSynthetic
public suspend fun <R : Any> TelegramApi<R>.requestResult(
    client: HttpClient,
    token: String,
    server: String? = null,
): TelegramApiResult<R> {
    val rawResponse = requestRaw(client, token, server)
    val raw = rawResponse.bodyAsText()
    // decode as result
    return Telegram.DefaultJson.decodeFromString(resultDeserializer, raw)
}

/**
 * Request the [TelegramApi] by [client] with [token].
 *
 * @param client see `client` in [requestRaw]
 * @param token see `token` in [requestRaw]
 * @param server see `server` in [requestRaw]
 * @throws TelegramApiResultNotOkException
 */
@JvmSynthetic
public suspend fun <R : Any> TelegramApi<R>.requestData(
    client: HttpClient,
    token: String,
    server: String? = null,
): R {
    return requestResult(client, token, server).resultOrThrow()
}
