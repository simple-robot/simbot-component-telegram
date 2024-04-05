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

package love.forte.simbot.component.telegram.message.internal

import love.forte.simbot.ability.DeleteFailureException
import love.forte.simbot.ability.DeleteOption
import love.forte.simbot.component.telegram.bot.internal.TelegramBotImpl
import love.forte.simbot.component.telegram.message.TelegramAggregatedMessageReceipt
import love.forte.simbot.component.telegram.message.TelegramSingleMessageReceipt
import love.forte.simbot.telegram.api.message.DeleteMessageApi
import love.forte.simbot.telegram.api.message.DeleteMessagesApi
import love.forte.simbot.telegram.stdlib.bot.requestDataBy
import love.forte.simbot.telegram.type.ChatId
import love.forte.simbot.telegram.type.Message


/**
 *
 * @author ForteScarlet
 */
internal class TelegramSingleMessageReceiptImpl(
    private val bot: TelegramBotImpl,
    override val message: Message
) : TelegramSingleMessageReceipt() {

    override suspend fun delete(vararg options: DeleteOption) {
        // TODO option ignore fail
        DeleteMessageApi.create(ChatId(message.chat.id), message.messageId).requestDataBy(bot.source)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TelegramSingleMessageReceiptImpl) return false

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
        "TelegramSingleMessageReceipt(id=${message.messageId})"
}

internal class TelegramAggregatedMessageReceiptImpl(
    private val bot: TelegramBotImpl,
    override val messages: List<Message>
) : TelegramAggregatedMessageReceipt() {
    override fun get(index: Int): TelegramSingleMessageReceipt {
        return messages[index].toTelegramMessageReceipt(bot)
    }

    override fun iterator(): Iterator<TelegramSingleMessageReceipt> =
        messages.map { it.toTelegramMessageReceipt(bot) }.iterator()

    override suspend fun deleteAll(vararg options: DeleteOption): Int {
        val chatId = messages.first().chat.id
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

internal fun Message.toTelegramMessageReceipt(bot: TelegramBotImpl): TelegramSingleMessageReceiptImpl =
    TelegramSingleMessageReceiptImpl(bot, this)

/**
 * [this] must be >= 1
 * (preferably >= 2).
 */
internal fun List<Message>.toTelegramMessageReceipt(bot: TelegramBotImpl): TelegramAggregatedMessageReceiptImpl {
    require(isNotEmpty()) { "Message list must not be empty" }
    return TelegramAggregatedMessageReceiptImpl(bot, this)
}
