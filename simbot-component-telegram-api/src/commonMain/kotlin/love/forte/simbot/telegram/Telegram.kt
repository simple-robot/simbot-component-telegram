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

package love.forte.simbot.telegram

import io.ktor.http.*
import kotlinx.serialization.json.Json
import kotlin.jvm.JvmField
import kotlin.jvm.JvmStatic

public object Telegram {
    /**
     * The `"{bot.token}"`.
     */
    public const val BOT_TOKEN_PLACEHOLDER_VALUE: String = "{bot.token}"

    /**
     * The Official server of Telegram Bot API:
     * `"https://api.telegram.org"`
     *
     * See: [Telegram Bot API Doc](https://core.telegram.org/bots/api)
     */
    public const val BASE_SERVER_VALUE: String = "https://api.telegram.org"

    /**
     * The [Url] value of [BASE_SERVER_VALUE].
     */
    @JvmField
    public val BaseServerUrl: Url = Url(BASE_SERVER_VALUE)

    /**
     * If [url] == [BASE_SERVER_VALUE],
     * return [BaseServerUrl],
     * otherwise, return a new [Url].
     */
    @JvmStatic
    public fun serverUrl(url: String): Url
        = if (url == BASE_SERVER_VALUE) BaseServerUrl else Url(url)

    /**
     * A default [Json].
     */
    @JvmField
    public val DefaultJson: Json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        allowSpecialFloatingPointValues = true
        prettyPrint = false
    }
}
