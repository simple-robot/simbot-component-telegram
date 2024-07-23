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

package love.forte.simbot.component.telegram.core.event

import love.forte.simbot.component.telegram.core.bot.TelegramBot
import love.forte.simbot.event.BotRegisteredEvent


/**
 * An event that indicating a [TelegramBot] has been registered.
 *
 * This is an event used as a notification
 * that the bot may not have been started yet.
 */
public interface TelegramBotRegisteredEvent : BotRegisteredEvent, TelegramBotEvent {
    override val bot: TelegramBot
}

/**
 * An event that indicating a [TelegramBot] has been started at the first time.
 *
 * This is an event used as a notification.
 * At this point, [bot.isStarted][TelegramBot.isStarted] is already `true`.
 */
public interface TelegramBotStartedEvent : BotRegisteredEvent, TelegramBotEvent {
    override val bot: TelegramBot
}
