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

import love.forte.simbot.component.telegram.core.actor.internal.toTelegramChatGroup
import love.forte.simbot.component.telegram.core.actor.internal.toTelegramMember
import love.forte.simbot.component.telegram.core.bot.internal.TelegramBotImpl
import love.forte.simbot.component.telegram.core.bot.requestDataBy
import love.forte.simbot.component.telegram.core.event.StdlibEvent
import love.forte.simbot.component.telegram.core.event.TelegramChatGroupMessageEvent
import love.forte.simbot.component.telegram.core.message.TelegramMessageContent
import love.forte.simbot.component.telegram.core.message.TelegramMessageReceipt
import love.forte.simbot.component.telegram.core.message.internal.TelegramMessageContentImpl
import love.forte.simbot.component.telegram.core.message.internal.toTelegramMessageReceipt
import love.forte.simbot.component.telegram.core.message.send
import love.forte.simbot.message.MessageContent
import love.forte.simbot.telegram.api.message.SendMessageApi
import love.forte.simbot.telegram.api.message.buildSendMessageApi
import love.forte.simbot.telegram.type.ChatId
import love.forte.simbot.telegram.type.Message
import love.forte.simbot.telegram.type.ReplyParameters


/**
 *
 * @author ForteScarlet
 */
internal class TelegramSuperGroupMessageEventImpl(
    override val bot: TelegramBotImpl,
    override val sourceEvent: StdlibEvent,
    // 记得确保 chat.type == SUPERGROUP
    override val sourceContent: Message
) : TelegramChatGroupMessageEvent {
    override val messageContent: TelegramMessageContent = TelegramMessageContentImpl(bot, sourceContent)

    override suspend fun content(): love.forte.simbot.component.telegram.core.actor.TelegramChatGroup {
        return sourceContent.chat.toTelegramChatGroup(bot)
    }

    override suspend fun author(): love.forte.simbot.component.telegram.core.actor.TelegramMember {
        // TODO from!!? check senderChat?
        return sourceContent.from!!.toTelegramMember(bot)
    }

    override suspend fun reply(text: String): TelegramMessageReceipt {
        return buildSendMessageApi(ChatId(sourceContent.chat.id), text) {
            replyParameters = ReplyParameters(sourceContent.messageId)
        }.requestDataBy(bot).toTelegramMessageReceipt(bot)
    }

    override suspend fun reply(message: love.forte.simbot.message.Message): TelegramMessageReceipt {
        return bot.send(message, sourceContent.chat.id) {
            SendMessageApi.builder().also {
                it.replyParameters = ReplyParameters(messageId = sourceContent.messageId)
            }
        }
    }

    override suspend fun reply(messageContent: MessageContent): TelegramMessageReceipt {
        return bot.send(
            messageContent,
            sourceContent.chat.id,
            copyApiBlock = {
                replyParameters = ReplyParameters(messageId = sourceContent.messageId)
            },
            builderFactory = {
                SendMessageApi.builder().also {
                    it.replyParameters = ReplyParameters(messageId = sourceContent.messageId)
                }
            }
        )
    }

    override fun toString(): String {
        return "TelegramSuperGroupMessageEvent(name=${sourceEvent.name})"
    }
}
