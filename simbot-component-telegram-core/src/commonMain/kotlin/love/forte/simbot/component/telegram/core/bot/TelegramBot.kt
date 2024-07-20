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

package love.forte.simbot.component.telegram.core.bot

import love.forte.simbot.bot.Bot
import love.forte.simbot.bot.ContactRelation
import love.forte.simbot.bot.GroupRelation
import love.forte.simbot.bot.GuildRelation
import love.forte.simbot.common.id.ID
import love.forte.simbot.common.id.LongID.Companion.ID
import love.forte.simbot.component.telegram.core.component.TelegramComponent
import love.forte.simbot.suspendrunner.ST
import love.forte.simbot.telegram.api.update.Update
import love.forte.simbot.telegram.type.User
import kotlin.coroutines.CoroutineContext


public typealias StdlibBot = love.forte.simbot.telegram.stdlib.bot.Bot

/**
 * A Telegram Bot of this component.
 *
 * @author ForteScarlet
 */
public interface TelegramBot : Bot {
    override val coroutineContext: CoroutineContext
    override val component: TelegramComponent

    /**
     * The source [bot][StdlibBot].
     */
    public val source: StdlibBot

    /**
     * [User] info of current bot.
     * The value is updated each time [queryUserInfo] is used.
     *
     * @throws IllegalStateException The bot has not been started, or has never been used [queryUserInfo].
     */
    public val userInfo: User

    /**
     * The name ([User.username] or [User.firstName] if it is null) of current bot.
     *
     * @throws IllegalStateException The bot has not been started, or has never been used [queryUserInfo].
     * @see userInfo
     */
    override val name: String
        get() = userInfo.username ?: userInfo.firstName

    /**
     * The id of [userInfo].
     *
     * @throws IllegalStateException The bot has not be started,
     * or has never been used [queryUserInfo]
     */
    public val userId: ID
        get() = userInfo.id.ID

    /**
     * The bot token configured.
     *
     * If you want to get the `user.id` of this bot,
     * use [userId].
     */
    override val id: ID

    /**
     * Query user info of the current bot.
     * And update the value of [userInfo].
     *
     * It is used to initialize [userInfo] at [start].
     */
    @ST
    public suspend fun queryUserInfo(): User

    /**
     * Same to [source.pushUpdate][love.forte.simbot.telegram.stdlib.bot.Bot.pushUpdate]
     * @see love.forte.simbot.telegram.stdlib.bot.Bot.pushUpdate
     */
    @ST
    public suspend fun pushUpdate(update: Update, raw: String? = null) {
        source.pushUpdate(update, raw)
    }


    override val groupRelation: GroupRelation?
        get() = null // TODO?

    override val guildRelation: GuildRelation?
        get() = null // TODO?

    override val contactRelation: ContactRelation?
        get() = null // TODO?

}
