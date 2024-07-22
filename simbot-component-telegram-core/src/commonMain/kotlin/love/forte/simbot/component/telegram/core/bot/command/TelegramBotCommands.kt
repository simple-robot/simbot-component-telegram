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

import love.forte.simbot.ability.DeleteOption
import love.forte.simbot.ability.DeleteSupport
import love.forte.simbot.component.telegram.core.bot.TelegramBot
import love.forte.simbot.telegram.type.BotCommand
import love.forte.simbot.telegram.type.BotCommandScope
import kotlin.jvm.JvmSynthetic


/**
 * A set of Telegram's [BotCommand]s
 *
 * @author ForteScarlet
 */
public interface TelegramBotCommands : DeleteSupport, Iterable<TelegramBotCommand> {
    /**
     * The list of [TelegramBotCommand]
     */
    public val values: List<TelegramBotCommand>

    /**
     * Iterator of [values].
     */
    override fun iterator(): Iterator<TelegramBotCommand> =
        values.iterator()

    /**
     * A command scope used in the query.
     */
    public val scope: BotCommandScope?

    /**
     * A two-letter ISO 639-1 language code used in the query.
     */
    public val languageCode: String?

    /**
     * Delete this set of commands with [scope] and [languageCode].
     */
    @JvmSynthetic
    override suspend fun delete(vararg options: DeleteOption)

    /**
     * Get a [TelegramBotCommandsUpdater] with [scope] and [languageCode]
     * for update commands.
     *
     * @see TelegramBotCommandsUpdater
     */
    public val updater: TelegramBotCommandsUpdater
}

/**
 * Update bot commands by [TelegramBotCommands.updater]
 *
 * @see TelegramBotCommands.updater
 */
public suspend inline fun TelegramBotCommands.update(
    block: TelegramBotCommandsUpdater.() -> Unit
): TelegramBotCommands = updater.also(block).update()

/**
 * A [BotCommand] of [TelegramBotCommands] from [TelegramBot.commands].
 *
 * @see TelegramBotCommands
 * @see BotCommand
 */
public interface TelegramBotCommand {
    /**
     * The source type [BotCommand]
     */
    public val source: BotCommand

    /**
     * Text of the command.
     *
     * @see BotCommand.command
     */
    public val command: String
        get() = source.command

    /**
     * Description of the command.
     *
     * @see BotCommand.description
     */
    public val description: String
        get() = source.description
}


