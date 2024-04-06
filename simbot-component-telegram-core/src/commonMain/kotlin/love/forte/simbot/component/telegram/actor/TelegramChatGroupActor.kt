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

package love.forte.simbot.component.telegram.actor

import kotlinx.coroutines.Job
import love.forte.simbot.common.collectable.Collectable
import love.forte.simbot.common.collectable.emptyCollectable
import love.forte.simbot.common.id.ID
import love.forte.simbot.common.id.LongID
import love.forte.simbot.common.id.LongID.Companion.ID
import love.forte.simbot.component.telegram.bot.TelegramBot
import love.forte.simbot.component.telegram.message.TelegramMessageReceipt
import love.forte.simbot.definition.ChatGroup
import love.forte.simbot.definition.Member
import love.forte.simbot.definition.Role
import love.forte.simbot.message.Message
import love.forte.simbot.message.MessageContent
import love.forte.simbot.suspendrunner.ST
import love.forte.simbot.suspendrunner.STP
import love.forte.simbot.telegram.type.Chat
import love.forte.simbot.telegram.type.ChatType
import kotlin.coroutines.CoroutineContext


/**
 *
 * @author ForteScarlet
 */
public interface TelegramChatAware {
    public val source: Chat


}

/**
 * A Telegram [Chat] representing a group ([Chat.type] == [ChatType.GROUP].value)
 * or a channel ([Chat.type] == [ChatType.CHANNEL].value).
 *
 * @see TelegramChatGroup
 * @see TelegramChannel
 *
 * @author ForteScarlet
 */
public interface TelegramChatGroupActor : TelegramChatAware, ChatGroup {
    /**
     * From [TelegramBot] but without [Job].
     */
    override val coroutineContext: CoroutineContext

    /**
     * The [Chat].
     */
    override val source: Chat

    /**
     * The [Chat.title]
     */
    override val name: String
        get() = source.title ?: ""

    /**
     * The [Chat.id]
     */
    override val id: LongID
        get() = source.id.ID

    /**
     * Always `null`.
     */
    override val ownerId: ID?
        get() = null

    /**
     * Always empty: Telegram cannot get all members.
     */
    override val members: Collectable<Member>
        get() = emptyCollectable()

    @STP
    public suspend fun memberCount(): Int

    override val roles: Collectable<Role>

    @STP
    override suspend fun botAsMember(): TelegramMember

    @ST(blockingBaseName = "getMember", blockingSuffix = "", asyncBaseName = "getMember", reserveBaseName = "getMember")
    override suspend fun member(id: ID): TelegramMember?

    @ST
    override suspend fun send(text: String): TelegramMessageReceipt

    @ST
    override suspend fun send(message: Message): TelegramMessageReceipt

    @ST
    override suspend fun send(messageContent: MessageContent): TelegramMessageReceipt
}


/**
 *
 * A Telegram [Chat] representing a group ([Chat.type] == [ChatType.GROUP].value).
 *
 * @author ForteScarlet
 */
public interface TelegramChatGroup : TelegramChatGroupActor


/**
 *
 * A Telegram [Chat] representing a channel ([Chat.type] == [ChatType.CHANNEL].value).
 *
 * The **Telegram _channel_** is also a [ChatGroup].
 *
 * @author ForteScarlet
 */
public interface TelegramChannel : TelegramChatGroupActor
