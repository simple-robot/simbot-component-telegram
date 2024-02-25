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

import io.ktor.http.*
import kotlinx.serialization.DeserializationStrategy
import love.forte.simbot.common.apidefinition.ApiDefinition
import love.forte.simbot.telegram.Telegram


/**
 * An API definition of [Telegram Bot API](https://core.telegram.org/bots/api).
 *
 * @author ForteScarlet
 */
public interface TelegramApi<R : Any> : ApiDefinition<R> {
    override val body: Any?

    /**
     * The method of this API.
     * The TG Bot API supports both `Get` and `Post`.
     *
     * See [Making requests](https://core.telegram.org/bots/api#making-requests).
     */
    override val method: HttpMethod

    /**
     * The result's [DeserializationStrategy] of this API.
     */
    override val resultDeserializationStrategy: DeserializationStrategy<R>

    /**
     * The `METHOD_NAME` of a Telegram Bot API query:
     * `https://api.telegram.org/bot<token>/METHOD_NAME`.
     *
     * See: [Making requests](https://core.telegram.org/bots/api#making-requests).
     *
     */
    public val apiMethod: String

    /**
     * The full query API url.
     *
     * In a Telegram Bot API query:
     * `https://api.telegram.org/bot<token>/METHOD_NAME`
     * there is a variable (`bot token`) in the path,
     * so in the result via [url], [`bot<token>`][Telegram.BOT_TOKEN_PLACEHOLDER_VALUE]
     * is replaced with `{bot.token}`,
     * e.g. `https://api.telegram.org/{bot.token}/getUser`.
     *
     * If you want to provide an [Url] with a specific `bot.token` value, use [url(token = ...)] [url].
     *
     * See: [Making requests](https://core.telegram.org/bots/api#making-requests).
     *
     * @ese url
     */
    override val url: Url

    /**
     * The full query API url,
     * with the specific [server] value and
     * the specific [Bot token][token] value.
     *
     * e.g.
     * ```kotlin
     * val url = getMeApi.url
     * // -> https://api.telegram.org/{bot.token}/getMe
     *
     * val url1 = getMeApi.url(token = "bot123456")
     * // -> https://api.telegram.org/bot123456/getMe
     *
     * val url2 = getMeApi.url(server = "http://example.com", token = "bot123456")
     * // -> http://example.com/bot123456/getMe
     * ```
     *
     *
     * @param server The query API server (e.g. `"https://example.com"`).
     * Default is [Telegram.BASE_SERVER_VALUE].
     * @param token The Bot token in the query API (should with the prefix `bot.`).
     * e.g. `"bot<token>"` of `https://api.telegram.org/bot<token>/METHOD_NAME`.
     * Default is [Telegram.BOT_TOKEN_PLACEHOLDER_VALUE].
     */
    public fun url(server: String? = null, token: String? = null): Url
}

