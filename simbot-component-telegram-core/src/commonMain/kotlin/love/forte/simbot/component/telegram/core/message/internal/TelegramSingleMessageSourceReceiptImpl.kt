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

package love.forte.simbot.component.telegram.core.message.internal

import love.forte.simbot.ability.DeleteFailureException
import love.forte.simbot.ability.DeleteOption
import love.forte.simbot.common.id.IntID
import love.forte.simbot.common.id.IntID.Companion.ID
import love.forte.simbot.common.id.LongID
import love.forte.simbot.common.id.LongID.Companion.ID
import love.forte.simbot.component.telegram.core.bot.internal.TelegramBotImpl
import love.forte.simbot.component.telegram.core.message.*
import love.forte.simbot.telegram.api.message.DeleteMessageApi
import love.forte.simbot.telegram.api.message.DeleteMessagesApi
import love.forte.simbot.telegram.stdlib.bot.requestDataBy
import love.forte.simbot.telegram.type.ChatId
import love.forte.simbot.telegram.type.Message
import love.forte.simbot.telegram.type.MessageId


/**
 *
 * @author ForteScarlet
 */
internal class TelegramSingleMessageSourceReceiptImpl(
    private val bot: TelegramBotImpl,
    override val message: Message
) : TelegramSingleMessageSourceReceipt() {
    override val chatId: LongID
        get() = message.chat.id.ID

    override suspend fun delete(vararg options: DeleteOption) {
        // TODO option ignore fail
        DeleteMessageApi.create(ChatId(message.chat.id), message.messageId).requestDataBy(bot.source)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TelegramSingleMessageSourceReceiptImpl) return false

        if (bot != other.bot) return false
        if (message != other.message) return false

        return true
    }

    override fun hashCode(): Int {
        var result = bot.hashCode()
        result = 31 * result + message.hashCode()
        return result
    }

    override fun toString(): String =
        "TelegramSingleMessageSourceReceipt(id=${message.messageId})"
}

/**
 *
 * @author ForteScarlet
 */
internal class TelegramSingleMessageIdReceiptImpl(
    private val bot: TelegramBotImpl,
    private val chatIdValue: Long,
    private val messageId: Int
) : TelegramSingleMessageIdReceipt() {
    override val id: IntID
        get() = messageId.ID

    override val chatId: LongID
        get() = chatIdValue.ID

    override suspend fun delete(vararg options: DeleteOption) {
        // TODO option ignore fail
        DeleteMessageApi.create(ChatId(chatIdValue), messageId).requestDataBy(bot.source)
    }


    override fun toString(): String =
        "TelegramSingleMessageIdReceipt(id=$messageId)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TelegramSingleMessageIdReceiptImpl) return false

        if (bot != other.bot) return false
        if (chatId != other.chatId) return false
        if (messageId != other.messageId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = bot.hashCode()
        result = 31 * result + chatId.hashCode()
        result = 31 * result + messageId
        return result
    }
}

// internal class SimpleTelegramAggregatedMessageReceiptImpl(
//     private val bot: TelegramBotImpl,
//     private val chatId: Long,
//     private val receipts: List<TelegramSingleMessageReceipt>,
// ) : TelegramAggregatedMessageReceipt() {
//     override val ids: Collection<IntID>
//         get() = receipts.map { it.id }
//
//     override fun get(index: Int): TelegramSingleMessageReceipt = receipts[index]
//
//     override fun iterator(): Iterator<TelegramSingleMessageReceipt> = receipts.iterator()
// }

internal class TelegramAggregatedMessageIdReceiptImpl(
    private val bot: TelegramBotImpl,
    private val chatId: Long,
    private val messageIds: List<Int>,
) : TelegramAggregatedMessageIdReceipt() {
    override val ids: Collection<IntID>
        get() = messageIds.map { it.ID }

    override fun get(index: Int): TelegramSingleMessageReceipt {
        return TelegramSingleMessageIdReceiptImpl(bot, chatId, messageIds[index])
    }

    override fun iterator(): Iterator<TelegramSingleMessageReceipt> {
        return iterator {
            for (messageId in messageIds) {
                TelegramSingleMessageIdReceiptImpl(bot, chatId, messageId)
            }
        }
    }

    override suspend fun deleteAll(vararg options: DeleteOption): Int {
        val deleted = DeleteMessagesApi.create(ChatId(chatId), messageIds).requestDataBy(bot.source)
        if (deleted) {
            return messageIds.size
        }

        // TODO options
        throw DeleteFailureException("DeleteMessagesApi returns false")
    }

    override fun toString(): String =
        "TelegramAggregatedMessageReceipt(size=${ids.size})"
}

internal class TelegramAggregatedMessageSourceReceiptImpl(
    private val bot: TelegramBotImpl,
    private val chatId: Long,
    override val messages: List<Message>
) : TelegramAggregatedMessageSourceReceipt() {
    override fun get(index: Int): TelegramSingleMessageReceipt {
        return messages[index].toTelegramMessageReceipt(bot)
    }

    override fun iterator(): Iterator<TelegramSingleMessageReceipt> =
        iterator {
            for (message in messages) {
                yield(message.toTelegramMessageReceipt(bot))
            }
        }

    override suspend fun deleteAll(vararg options: DeleteOption): Int {
        val deleted = DeleteMessagesApi.create(ChatId(chatId), messages.map { it.messageId }).requestDataBy(bot.source)
        if (deleted) {
            return messages.size
        }

        // TODO options
        throw DeleteFailureException("DeleteMessagesApi returns false")
    }

    override fun toString(): String =
        "TelegramAggregatedMessageReceipt(size=${messages.size})"
}

internal fun Message.toTelegramMessageReceipt(bot: TelegramBotImpl): TelegramSingleMessageSourceReceiptImpl =
    TelegramSingleMessageSourceReceiptImpl(bot, this)

internal fun MessageId.toTelegramMessageReceipt(
    bot: TelegramBotImpl,
    chatId: Long
): TelegramSingleMessageIdReceiptImpl =
    TelegramSingleMessageIdReceiptImpl(bot, chatId, messageId)


/**
 * [this] must be >= 1
 * (preferably >= 2).
 */
internal fun List<Message>.toTelegramMessageReceipt(
    bot: TelegramBotImpl,
    chatId: Long = first().chat.id
): TelegramAggregatedMessageSourceReceiptImpl {
    require(isNotEmpty()) { "Message list must not be empty" }
    return TelegramAggregatedMessageSourceReceiptImpl(bot, chatId, this)
}

/**
 * [this] must be >= 1
 * (preferably >= 2).
 */
internal fun List<MessageId>.toTelegramMessageReceipt(
    bot: TelegramBotImpl,
    chatId: Long
): TelegramAggregatedMessageIdReceiptImpl {
    require(isNotEmpty()) { "Message list must not be empty" }
    return TelegramAggregatedMessageIdReceiptImpl(bot, chatId, this.map { it.messageId })
}
