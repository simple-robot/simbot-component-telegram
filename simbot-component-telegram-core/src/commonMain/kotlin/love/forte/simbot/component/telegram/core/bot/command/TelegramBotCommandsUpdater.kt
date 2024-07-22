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

package love.forte.simbot.component.telegram.core.bot.command

import love.forte.simbot.suspendrunner.ST
import love.forte.simbot.telegram.type.BotCommand
import love.forte.simbot.telegram.type.BotCommandScope


/**
 * An updater for set bot commands.
 *
 * @author ForteScarlet
 */
public interface TelegramBotCommandsUpdater {
    /**
     * the [BotCommandScope]
     */
    public var scope: BotCommandScope?

    /**
     * the [languageCode]
     */
    public var languageCode: String?

    /**
     * Commands for update.
     */
    public var commands: MutableList<BotCommand>

    /**
     * @see scope
     */
    public fun scope(scope: BotCommandScope?): TelegramBotCommandsUpdater = apply {
        this.scope = scope
    }

    /**
     * @see languageCode
     */
    public fun languageCode(languageCode: String?): TelegramBotCommandsUpdater = apply {
        this.languageCode = languageCode
    }

    /**
     * Add a command into [commands]
     * @see commands
     */
    public fun addCommand(command: BotCommand): TelegramBotCommandsUpdater = apply {
        commands.add(command)
    }

    /**
     * Add a command into [commands]
     * @see commands
     */
    public fun addCommand(command: String, description: String): TelegramBotCommandsUpdater =
        addCommand(BotCommand(command, description))

    /**
     * Execute setting and a new [TelegramBotCommands] that
     * values **directly** based on new commands is returned.
     *
     */
    @ST
    public suspend fun update(): TelegramBotCommands
}
