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

import love.forte.simbot.telegram.stdlib.bot.internal.BotImpl
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic

// TODO Doc

/**
 * A [Bot] factory.
 */
public object BotFactory {

    /**
     * @param ticket [Bot.Ticket].
     */
    @JvmStatic
    @JvmOverloads
    public fun create(ticket: Bot.Ticket, configuration: BotConfiguration = BotConfiguration()): Bot =
        BotImpl(ticket, configuration)

    /**
     * @param token Bot full token, e.g. `"bot123123.aaabbbccc"`.
     * See: [Bot.Ticket]
     */
    @JvmStatic
    @JvmOverloads
    public fun create(token: String, configuration: BotConfiguration = BotConfiguration()): Bot =
        create(Bot.Ticket(token), configuration)


    @JvmSynthetic
    public inline fun create(ticket: Bot.Ticket, block: BotConfiguration.() -> Unit): Bot =
        create(ticket, BotConfiguration().also(block))

    @JvmSynthetic
    public inline fun create(token: String, block: BotConfiguration.() -> Unit): Bot =
        create(token, BotConfiguration().also(block))
}
