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

package love.forte.simbot.component.telegram.message

import love.forte.simbot.common.id.IntID
import love.forte.simbot.common.id.IntID.Companion.ID
import love.forte.simbot.message.AggregatedMessageReceipt
import love.forte.simbot.message.MessageReceipt
import love.forte.simbot.message.SingleMessageReceipt
import love.forte.simbot.telegram.type.Message

public interface TelegramMessageReceipt : MessageReceipt


public abstract class TelegramSingleMessageReceipt : TelegramMessageReceipt, SingleMessageReceipt() {
    override val id: IntID
        get() = message.messageId.ID

    /**
     * Sent a message the API returned.
     */
    public abstract val message: Message


}

public abstract class TelegramAggregatedMessageReceipt : TelegramMessageReceipt, AggregatedMessageReceipt() {
    /**
     * Sent messages the API returned.
     */
    public abstract val messages: Collection<Message>

    abstract override fun get(index: Int): TelegramSingleMessageReceipt

    abstract override fun iterator(): Iterator<TelegramSingleMessageReceipt>

    override val size: Int
        get() = messages.size

}
