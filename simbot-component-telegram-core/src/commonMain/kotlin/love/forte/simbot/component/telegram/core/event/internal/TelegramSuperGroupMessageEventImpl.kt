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

package love.forte.simbot.component.telegram.core.event.internal

import love.forte.simbot.component.telegram.core.actor.TelegramChatGroup
import love.forte.simbot.component.telegram.core.actor.internal.toTelegramChatGroup
import love.forte.simbot.component.telegram.core.bot.internal.TelegramBotImpl
import love.forte.simbot.component.telegram.core.event.StdlibEvent
import love.forte.simbot.telegram.type.Message


/**
 *
 * @author ForteScarlet
 */
internal class TelegramSuperGroupMessageEventImpl(
    bot: TelegramBotImpl,
    override val sourceEvent: StdlibEvent,
    // 记得确保 chat.type == SUPERGROUP
    sourceContent: Message
) : AbstractTelegramGroupMessageEventImpl(
    bot,
    sourceContent
) {
    override suspend fun content(): TelegramChatGroup {
        return sourceContent.chat.toTelegramChatGroup(bot)
    }

    override fun toString(): String {
        return "TelegramSuperGroupMessageEvent(name=${sourceEvent.name})"
    }
}
