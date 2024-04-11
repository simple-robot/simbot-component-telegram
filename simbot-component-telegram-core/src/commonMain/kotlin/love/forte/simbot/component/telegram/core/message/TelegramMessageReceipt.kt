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

package love.forte.simbot.component.telegram.core.message

import love.forte.simbot.common.id.IntID
import love.forte.simbot.common.id.IntID.Companion.ID
import love.forte.simbot.common.id.LongID
import love.forte.simbot.message.AggregatedMessageReceipt
import love.forte.simbot.message.MessageReceipt
import love.forte.simbot.message.SingleMessageReceipt
import love.forte.simbot.telegram.type.Message

public interface TelegramMessageReceipt : MessageReceipt


public interface TelegramMessageSourceAware {
    public val message: Message
}


public abstract class TelegramSingleMessageReceipt : TelegramMessageReceipt, SingleMessageReceipt() {
    abstract override val id: IntID
    public abstract val chatId: LongID
}


public abstract class TelegramSingleMessageIdReceipt : TelegramSingleMessageReceipt()
public abstract class TelegramSingleMessageSourceReceipt : TelegramSingleMessageReceipt() {
    public abstract val message: Message
    override val id: IntID
        get() = message.messageId.ID
}

public abstract class TelegramAggregatedMessageReceipt : TelegramMessageReceipt, AggregatedMessageReceipt() {
    /**
     * Sent message ids the API returned.
     */
    public abstract val ids: Collection<IntID>

    abstract override fun get(index: Int): TelegramSingleMessageReceipt

    abstract override fun iterator(): Iterator<TelegramSingleMessageReceipt>

    public override val size: Int
        get() = ids.size
}

public abstract class TelegramAggregatedMessageIdReceipt : TelegramAggregatedMessageReceipt()
public abstract class TelegramAggregatedMessageSourceReceipt : TelegramAggregatedMessageReceipt() {
    public abstract val messages: Collection<Message>
    override val ids: Collection<IntID>
        get() = messages.map { it.messageId.ID }
}
