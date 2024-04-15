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

import love.forte.simbot.common.id.ID
import love.forte.simbot.common.id.LongID.Companion.ID
import love.forte.simbot.common.time.Timestamp
import love.forte.simbot.component.telegram.core.actor.*
import love.forte.simbot.component.telegram.core.message.TelegramMessageContent
import love.forte.simbot.component.telegram.core.message.TelegramMessageReceipt
import love.forte.simbot.component.telegram.core.time.unixDateTimestamp
import love.forte.simbot.event.ChatGroupMessageEvent
import love.forte.simbot.event.ContactMessageEvent
import love.forte.simbot.event.MessageEvent
import love.forte.simbot.message.MessageContent
import love.forte.simbot.suspendrunner.ST
import love.forte.simbot.suspendrunner.STP
import love.forte.simbot.telegram.type.Message
import love.forte.simbot.telegram.type.User


/**
 * An event type related to [Message].
 *
 * @author ForteScarlet
 */
public interface TelegramMessageRelatedEvent : TelegramEvent, TypeBasedTelegramMessageEvent {
    override val sourceContent: Message

    override val time: Timestamp
        get() = unixDateTimestamp(sourceContent.date)
}

/**
 * A [Message] [event][MessageEvent].
 *
 * @author ForteScarlet
 */
public interface TelegramMessageEvent : TelegramMessageRelatedEvent, BasicTelegramMessageEvent

/**
 * An event about [Message] from [TelegramChannel] or [TelegramChatGroup].
 *
 * @see TelegramChatGroupMessageEvent
 * @see TelegramSuperGroupMessageEvent
 * @see TelegramChannelMessageEvent
 *
 * @author ForteScarlet
 */
public interface TelegramGroupMessageEvent : TelegramMessageEvent, ChatGroupMessageEvent {
    override val messageContent: TelegramMessageContent

    /**
     * The [TelegramGroup].
     */
    @STP
    override suspend fun content(): TelegramGroup

    /**
     * The [sender][Message.from]'s [id][User.id]
     */
    override val authorId: ID
        get() = sourceContent.from!!.id.ID

    @STP
    override suspend fun author(): TelegramMember

    /**
     * Reply to this message with [text].
     */
    @ST
    override suspend fun reply(text: String): TelegramMessageReceipt

    /**
     * Reply to this message with [message].
     */
    @ST
    override suspend fun reply(message: love.forte.simbot.message.Message): TelegramMessageReceipt

    /**
     * Reply to this message with [messageContent].
     */
    @ST
    override suspend fun reply(messageContent: MessageContent): TelegramMessageReceipt
}

/**
 * An event about [Message] from a [TelegramChatGroup] (chat.type == `"group"`)
 *
 * @author ForteScarlet
 */
public interface TelegramChatGroupMessageEvent : TelegramGroupMessageEvent {
    /**
     * The [TelegramChatGroup].
     */
    @STP
    override suspend fun content(): TelegramChatGroup

}

/**
 * An event about [Message] from a [TelegramChatGroup] (chat.type == `"supergroup"`)
 *
 * @author ForteScarlet
 */
public interface TelegramSuperGroupMessageEvent : TelegramGroupMessageEvent {
    /**
     * The [TelegramGroup].
     */
    @STP
    override suspend fun content(): TelegramGroup
}

/**
 * An event about [Message] from a [TelegramChannel] (chat.type == `"channel"`)
 *
 * @author ForteScarlet
 */
public interface TelegramChannelMessageEvent : TelegramGroupMessageEvent {
    /**
     * The [TelegramChannel].
     */
    @STP
    override suspend fun content(): TelegramChannel
}

/**
 * An event about [Message] from a [TelegramContact] (chat.type == `"private"`)
 *
 * @author ForteScarlet
 */
public interface TelegramPrivateMessageEvent : TelegramMessageEvent, ContactMessageEvent {
    override val messageContent: TelegramMessageContent

    /**
     * The [TelegramContact].
     */
    @STP
    override suspend fun content(): TelegramContact

    /**
     * The [sender][Message.from]'s [id][User.id]
     */
    override val authorId: ID
        get() = sourceContent.from!!.id.ID


    @ST
    override suspend fun reply(text: String): TelegramMessageReceipt

    @ST
    override suspend fun reply(message: love.forte.simbot.message.Message): TelegramMessageReceipt

    @ST
    override suspend fun reply(messageContent: MessageContent): TelegramMessageReceipt
}
