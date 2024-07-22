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

import love.forte.simbot.ability.DeleteFailureException
import love.forte.simbot.ability.DeleteOption
import love.forte.simbot.ability.StandardDeleteOption
import love.forte.simbot.component.telegram.core.bot.TelegramBot
import love.forte.simbot.component.telegram.core.bot.command.TelegramBotCommand
import love.forte.simbot.component.telegram.core.bot.command.TelegramBotCommands
import love.forte.simbot.component.telegram.core.bot.command.TelegramBotCommandsUpdater
import love.forte.simbot.telegram.api.bot.command.DeleteMyCommandsApi
import love.forte.simbot.telegram.stdlib.bot.requestDataBy
import love.forte.simbot.telegram.type.BotCommand
import love.forte.simbot.telegram.type.BotCommandScope

internal class TelegramBotCommandsImpl(
    private val bot: TelegramBot,
    override val values: List<TelegramBotCommand>,
    override val scope: BotCommandScope?,
    override val languageCode: String?,
) : TelegramBotCommands {
    override suspend fun delete(vararg options: DeleteOption) {
        runCatching {
            DeleteMyCommandsApi.create(
                scope = scope,
                languageCode = languageCode
            ).requestDataBy(bot.source)
        }.onFailure { err ->
            if (StandardDeleteOption.IGNORE_ON_FAILURE !in options) {
                throw DeleteFailureException(err.message, err)
            }
        }
    }

    override val updater: TelegramBotCommandsUpdater
        get() = TelegramBotCommandsUpdaterImpl(
            bot = bot,
            scope = scope,
            languageCode = languageCode
        )

    override fun toString(): String =
        "TelegramBotCommands(scope=$scope, languageCode=$languageCode, values=$values)"
}

internal fun Iterable<BotCommand>.toTGCommands(
    bot: TelegramBot,
    scope: BotCommandScope?,
    languageCode: String?,
): TelegramBotCommandsImpl =
    TelegramBotCommandsImpl(
        bot,
        this.map { it.toTGCommand() },
        scope,
        languageCode
    )

/**
 *
 * @author ForteScarlet
 */
internal class TelegramBotCommandImpl(
    override val source: BotCommand
) : TelegramBotCommand {
    override fun toString(): String {
        return "TelegramBotCommand(source=$source)"
    }
}

internal fun BotCommand.toTGCommand(): TelegramBotCommandImpl =
    TelegramBotCommandImpl(this)
