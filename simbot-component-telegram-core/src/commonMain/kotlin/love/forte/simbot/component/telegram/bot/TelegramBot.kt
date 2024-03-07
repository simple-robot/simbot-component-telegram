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

import love.forte.simbot.bot.Bot
import love.forte.simbot.bot.ContactRelation
import love.forte.simbot.bot.GroupRelation
import love.forte.simbot.bot.GuildRelation
import love.forte.simbot.common.id.ID
import kotlin.coroutines.CoroutineContext


public typealias StdlibBot = love.forte.simbot.telegram.stdlib.bot.Bot

/**
 * A Telegram Bot of this component.
 *
 * @author ForteScarlet
 */
public interface TelegramBot : Bot {
    override val coroutineContext: CoroutineContext

    /**
     *
     * @throws IllegalStateException if bot is not started yet.
     */
    override val id: ID

    /**
     *
     * @throws IllegalStateException if bot is not started yet.
     */
    override val name: String

    public val source: StdlibBot


    override val groupRelation: GroupRelation?
        get() = null

    override val guildRelation: GuildRelation?
        get() = null

    override val contactRelation: ContactRelation?
        get() = null

}
