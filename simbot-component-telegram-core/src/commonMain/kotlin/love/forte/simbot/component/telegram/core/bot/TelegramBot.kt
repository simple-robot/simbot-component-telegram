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

import io.ktor.client.statement.*
import love.forte.simbot.bot.Bot
import love.forte.simbot.bot.ContactRelation
import love.forte.simbot.bot.GroupRelation
import love.forte.simbot.bot.GuildRelation
import love.forte.simbot.common.id.ID
import love.forte.simbot.common.id.LongID.Companion.ID
import love.forte.simbot.component.telegram.core.bot.command.TelegramBotCommands
import love.forte.simbot.component.telegram.core.bot.command.TelegramBotCommandsUpdater
import love.forte.simbot.component.telegram.core.component.TelegramComponent
import love.forte.simbot.suspendrunner.ST
import love.forte.simbot.telegram.api.TelegramApi
import love.forte.simbot.telegram.api.TelegramApiResult
import love.forte.simbot.telegram.api.update.Update
import love.forte.simbot.telegram.type.BotCommandScope
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

    /**
     * Fetches a [TelegramBotCommands] with [scope] and [languageCode].
     *
     * @param scope Scope of bot commands.
     * @param languageCode A two-letter ISO 639-1 language code of commands.
     */
    @ST
    public suspend fun commands(scope: BotCommandScope?, languageCode: String?): TelegramBotCommands

    /**
     * Fetches a [TelegramBotCommands].
     */
    @ST
    public suspend fun commands(): TelegramBotCommands =
        commands(scope = null, languageCode = null)

    /**
     * An updater for set commands.
     *
     * @see TelegramBotCommandsUpdater
     */
    public val commandsUpdater: TelegramBotCommandsUpdater

    /**
     * Execute [TelegramApi] by this bot.
     *
     * @throws Exception Any exception that may occur during the API request process
     *
     * @see requestDataBy
     */
    @ST
    public suspend fun <R : Any> execute(api: TelegramApi<R>): R =
        api.requestDataBy(this)

    /**
     * Execute [TelegramApi] by this bot.
     *
     * @throws Exception Any exception that may occur during the API request process
     *
     * @return The raw [HttpResponse]
     * @see requestRawBy
     */
    @ST
    public suspend fun executeRaw(api: TelegramApi<*>): HttpResponse =
        api.requestRawBy(this)

    /**
     * Execute [TelegramApi] by this bot.
     *
     * @throws Exception Any exception that may occur during the API request process
     *
     * @return The [TelegramApiResult]
     * @see requestResultBy
     */
    @ST
    public suspend fun <R : Any> executeResult(api: TelegramApi<R>): TelegramApiResult<R> =
        api.requestResultBy(this)


    override val groupRelation: GroupRelation?
        get() = null

    override val guildRelation: GuildRelation?
        get() = null

    override val contactRelation: ContactRelation?
        get() = null

}

/**
 * Update bot commands by [TelegramBot.commandsUpdater]
 *
 * @see TelegramBot.commandsUpdater
 */
public suspend inline fun TelegramBot.updateCommands(
    block: TelegramBotCommandsUpdater.() -> Unit
): TelegramBotCommands = commandsUpdater.also(block).update()
