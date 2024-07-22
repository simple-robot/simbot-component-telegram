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

package love.forte.simbot.component.telegram.core.bot.internal.command

import love.forte.simbot.component.telegram.core.bot.TelegramBot
import love.forte.simbot.component.telegram.core.bot.command.TelegramBotCommands
import love.forte.simbot.component.telegram.core.bot.command.TelegramBotCommandsUpdater
import love.forte.simbot.telegram.api.bot.command.SetMyCommandsApi
import love.forte.simbot.telegram.stdlib.bot.requestDataBy
import love.forte.simbot.telegram.type.BotCommand
import love.forte.simbot.telegram.type.BotCommandScope


/**
 *
 * @author ForteScarlet
 */
internal class TelegramBotCommandsUpdaterImpl(
    private val bot: TelegramBot,
    override var scope: BotCommandScope?,
    override var languageCode: String?,
    override var commands: MutableList<BotCommand> = mutableListOf(),
) : TelegramBotCommandsUpdater {
    override suspend fun update(): TelegramBotCommands {
        val commands = commands.toList()
        val scope = scope
        val languageCode = languageCode

        val updated = SetMyCommandsApi.create(
            commands,
            scope,
            languageCode
        ).requestDataBy(bot.source)

        check(updated) {
            "The SetMyCommandsApi request returned `false`"
        }

        return commands.toTGCommands(
            bot = bot,
            scope = scope,
            languageCode = languageCode
        )
    }
}
