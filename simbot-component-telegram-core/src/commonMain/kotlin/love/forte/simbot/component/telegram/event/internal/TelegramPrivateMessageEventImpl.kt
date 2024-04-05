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

package love.forte.simbot.component.telegram.event.internal

import love.forte.simbot.component.telegram.actor.TelegramContact
import love.forte.simbot.component.telegram.actor.internal.toTelegramUserContact
import love.forte.simbot.component.telegram.bot.internal.TelegramBotImpl
import love.forte.simbot.component.telegram.bot.requestDataBy
import love.forte.simbot.component.telegram.event.StdlibEvent
import love.forte.simbot.component.telegram.event.TelegramPrivateMessageEvent
import love.forte.simbot.component.telegram.message.TelegramMessageReceipt
import love.forte.simbot.component.telegram.message.internal.TelegramMessageContentImpl
import love.forte.simbot.component.telegram.message.internal.toTelegramMessageReceipt
import love.forte.simbot.message.MessageContent
import love.forte.simbot.telegram.api.message.SendMessageApi
import love.forte.simbot.telegram.type.ChatId
import love.forte.simbot.telegram.type.Message


/**
 *
 * @author ForteScarlet
 */
internal class TelegramPrivateMessageEventImpl(
    override val bot: TelegramBotImpl,
    override val sourceEvent: StdlibEvent,
    override val sourceContent: Message
) : TelegramPrivateMessageEvent {
    override val messageContent: MessageContent = TelegramMessageContentImpl(sourceContent)

    override suspend fun content(): TelegramContact {
        return sourceContent.from!!.toTelegramUserContact(bot, sourceContent.chat)
    }

    override suspend fun reply(text: String): TelegramMessageReceipt {
        return SendMessageApi.create(ChatId(sourceContent.chat.id), text)
            .requestDataBy(bot)
            .toTelegramMessageReceipt(bot)
    }

    override suspend fun reply(message: love.forte.simbot.message.Message): TelegramMessageReceipt {
        TODO("Not yet implemented")
    }

    override suspend fun reply(messageContent: MessageContent): TelegramMessageReceipt {
        TODO("Not yet implemented")
    }

    override fun toString(): String {
        return "TelegramPrivateMessageEvent(name=${sourceEvent.name})"
    }
}
