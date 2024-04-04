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

package love.forte.simbot.component.telegram.bot

import io.ktor.client.engine.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import love.forte.simbot.annotations.InternalSimbotAPI
import love.forte.simbot.bot.SerializableBotConfiguration
import love.forte.simbot.bot.configuration.ProxyConfiguration
import love.forte.simbot.bot.configuration.ProxyValue
import love.forte.simbot.component.telegram.component.TelegramComponent
import love.forte.simbot.telegram.stdlib.bot.Bot
import love.forte.simbot.telegram.stdlib.bot.BotConfiguration
import love.forte.simbot.telegram.stdlib.bot.LongPolling


/**
 *
 * @author ForteScarlet
 */
@Serializable
@SerialName(TelegramComponent.ID_VALUE)
@InternalSimbotAPI
public data class SerializableTelegramBotConfiguration(
    public val ticket: Ticket,
    public var config: Config? = null
) : SerializableBotConfiguration() {

    @Serializable
    public data class Ticket(val token: String) {
        public fun toBotTicket(): Bot.Ticket = Bot.Ticket(token)
    }

    @Serializable
    public class Config {

        public var server: String? = null

        public var proxy: ProxyConfiguration? = null

        public var longPolling: LongPollingConfiguration? = null


    }

    @Serializable
    public data class LongPollingConfiguration(
        val limit: Int? = null,
        val timeout: Int? = BotConfiguration.DefaultLongPollingTimeout.inWholeSeconds.toInt(),
        val allowedUpdates: Collection<String>? = null,
    ) {
        public fun toBotLongPolling(): LongPolling = LongPolling(
            limit = limit,
            timeout = timeout,
            allowedUpdates = allowedUpdates,
        )
    }

    public fun toBotConfiguration(): TelegramBotConfiguration {
        return TelegramBotConfiguration().apply {
            config?.also { c ->
                botConfiguration {
                    c.server?.also { server = it }
                    c.longPolling?.also { longPolling = it.toBotLongPolling() }
                    c.proxy?.also {
                        apiClientConfigurer {
                            engine {
                                proxy = when (val pv = it.value) {
                                    is ProxyValue.Http -> ProxyBuilder.http(pv.url)
                                    is ProxyValue.Socks -> ProxyBuilder.socks(pv.host, pv.port)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/*
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
 */
