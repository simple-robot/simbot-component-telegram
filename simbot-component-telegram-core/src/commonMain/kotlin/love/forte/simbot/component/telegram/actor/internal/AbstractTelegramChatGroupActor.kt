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

package love.forte.simbot.component.telegram.actor.internal

import love.forte.simbot.common.collectable.Collectable
import love.forte.simbot.common.id.ID
import love.forte.simbot.component.telegram.actor.TelegramChatGroupActor
import love.forte.simbot.component.telegram.actor.TelegramMember
import love.forte.simbot.component.telegram.bot.internal.TelegramBotImpl
import love.forte.simbot.component.telegram.message.TelegramMessageReceipt
import love.forte.simbot.definition.Role
import love.forte.simbot.message.Message
import love.forte.simbot.message.MessageContent
import love.forte.simbot.telegram.type.Chat



/**
 *
 * @author ForteScarlet
 */
internal abstract class AbstractTelegramChatGroupActor : TelegramChatGroupActor {
    protected abstract val bot: TelegramBotImpl
    abstract override val source: Chat

    override val roles: Collectable<Role>
        get() = TODO("Not yet implemented")

    override suspend fun botAsMember(): TelegramMember {
        TODO("Not yet implemented")
    }

    override suspend fun member(id: ID): TelegramMember? {
        TODO("Not yet implemented")
    }

    override suspend fun memberCount(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun send(text: String): TelegramMessageReceipt {
        TODO("Not yet implemented")
    }

    override suspend fun send(message: Message): TelegramMessageReceipt {
        TODO("Not yet implemented")
    }

    override suspend fun send(messageContent: MessageContent): TelegramMessageReceipt {
        TODO("Not yet implemented")
    }
}
