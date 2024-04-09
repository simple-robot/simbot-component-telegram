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

package love.forte.simbot.component.telegram.core.actor.internal

import love.forte.simbot.component.telegram.core.bot.internal.TelegramBotImpl
import love.forte.simbot.component.telegram.core.message.TelegramMessageReceipt
import love.forte.simbot.component.telegram.core.message.internal.toTelegramMessageReceipt
import love.forte.simbot.message.Message
import love.forte.simbot.message.MessageContent
import love.forte.simbot.telegram.api.message.SendMessageApi
import love.forte.simbot.telegram.stdlib.bot.requestDataBy
import love.forte.simbot.telegram.type.Chat
import love.forte.simbot.telegram.type.ChatId
import love.forte.simbot.telegram.type.User
import kotlin.coroutines.CoroutineContext


/**
 *
 * @author ForteScarlet
 */
internal class TelegramUserContactImpl(
    private val bot: TelegramBotImpl,
    override val source: User,
    private val sourceChat: Chat,
) : love.forte.simbot.component.telegram.core.actor.TelegramContact {
    override val coroutineContext: CoroutineContext = bot.subContext

    override suspend fun send(messageContent: MessageContent): TelegramMessageReceipt {
        TODO("Not yet implemented")
    }

    override suspend fun send(message: Message): TelegramMessageReceipt {
        TODO("Not yet implemented")
    }

    override suspend fun send(text: String): TelegramMessageReceipt {
        val sent = SendMessageApi.create(ChatId(sourceChat.id), text).requestDataBy(bot.source)
        return sent.toTelegramMessageReceipt(bot)
    }

    override fun toString(): String =
        "TelegramUserContact(" +
            "id=${source.id}, " +
            "firstName=${source.firstName} " +
            "username=${source.username}, " +
            "chat=Chat(id=${sourceChat.id}, " +
            "type=${sourceChat.type} " +
            "title=${sourceChat.title}))"
}

internal fun User.toTelegramUserContact(bot: TelegramBotImpl, chat: Chat): TelegramUserContactImpl =
    TelegramUserContactImpl(bot, this, chat)
