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

import love.forte.simbot.common.collectable.Collectable
import love.forte.simbot.common.collectable.emptyCollectable
import love.forte.simbot.common.id.ID
import love.forte.simbot.common.id.toInt
import love.forte.simbot.component.telegram.core.actor.TelegramChatGroupActor
import love.forte.simbot.component.telegram.core.actor.TelegramMember
import love.forte.simbot.component.telegram.core.bot.internal.TelegramBotImpl
import love.forte.simbot.component.telegram.core.bot.requestDataBy
import love.forte.simbot.component.telegram.core.message.TelegramMessageReceipt
import love.forte.simbot.component.telegram.core.message.send
import love.forte.simbot.definition.Role
import love.forte.simbot.message.Message
import love.forte.simbot.message.MessageContent
import love.forte.simbot.telegram.api.chat.GetChatMemberApi
import love.forte.simbot.telegram.api.chat.GetChatMemberCountApi
import love.forte.simbot.telegram.type.*


/**
 *
 * @author ForteScarlet
 */
internal abstract class AbstractTelegramChatGroupActor : TelegramChatGroupActor {
    protected abstract val bot: TelegramBotImpl
    abstract override val source: Chat

    override val roles: Collectable<Role>
        get() = emptyCollectable() // TODO("Not yet implemented")

    override suspend fun botAsMember(): TelegramMember {
        return bot.queryUserInfo().toTelegramMember(bot)
    }

    override suspend fun member(id: ID): TelegramMember? {
        val chatMember = GetChatMemberApi.create(ChatId(source.id), id.toInt())
            .requestDataBy(bot)

        return when (chatMember) {
            is ChatMemberAdministrator -> chatMember.user.toTelegramMember(bot, chatMember)
            is ChatMemberBanned -> chatMember.user.toTelegramMember(bot, chatMember)
            is ChatMemberLeft -> chatMember.user.toTelegramMember(bot, chatMember)
            is ChatMemberOwner -> chatMember.user.toTelegramMember(bot, chatMember)
            is ChatMemberRestricted -> chatMember.user.toTelegramMember(bot, chatMember)
            is ChatMemberMember -> chatMember.user.toTelegramMember(bot, chatMember)
        }
    }

    override suspend fun memberCount(): Int {
        return GetChatMemberCountApi.create(ChatId(source.id)).requestDataBy(bot)
    }

    override suspend fun send(text: String): TelegramMessageReceipt {
        return bot.send(text, source.id)
    }

    override suspend fun send(message: Message): TelegramMessageReceipt {
        return bot.send(message, source.id)
    }

    override suspend fun send(messageContent: MessageContent): TelegramMessageReceipt {
        return bot.send(messageContent, source.id)
    }
}
