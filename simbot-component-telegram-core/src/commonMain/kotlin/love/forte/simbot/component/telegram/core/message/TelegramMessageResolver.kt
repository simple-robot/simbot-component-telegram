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

@file:JvmName("TelegramMessageResolvers")

package love.forte.simbot.component.telegram.core.message

import love.forte.simbot.component.telegram.core.bot.internal.TelegramBotImpl
import love.forte.simbot.component.telegram.core.bot.requestDataBy
import love.forte.simbot.component.telegram.core.message.internal.TelegramAggregatedMessageIdReceiptImpl
import love.forte.simbot.component.telegram.core.message.internal.TextSendingResolver
import love.forte.simbot.component.telegram.core.message.internal.toTelegramMessageReceipt
import love.forte.simbot.message.*
import love.forte.simbot.telegram.api.TelegramApi
import love.forte.simbot.telegram.api.message.*
import love.forte.simbot.telegram.type.ChatId
import love.forte.simbot.telegram.type.MessageId
import kotlin.jvm.JvmName
import love.forte.simbot.telegram.type.Message as StdlibMessage


internal inline fun StdlibMessage.toCopyApi(
    chatId: ChatId,
    block: CopyMessageApi.Builder.() -> Unit = {}
): CopyMessageApi {
    return buildCopyMessageApi(chatId, ChatId(chat.id), messageId) {
        block()
    }
}

internal inline fun StdlibMessage.toForwardApi(
    chatId: ChatId,
    block: ForwardMessageApi.Builder.() -> Unit = {}
): ForwardMessageApi {
    return buildForwardMessageApi(chatId, ChatId(chat.id), messageId) {
        block()
    }
}

internal suspend inline fun TelegramBotImpl.send(
    text: String,
    chatId: Long,
    block: SendMessageApi.Builder.() -> Unit = {}
): TelegramMessageReceipt {
    return buildSendMessageApi(ChatId(chatId), text, block)
        .requestDataBy(this)
        .toTelegramMessageReceipt(this)
}

internal suspend inline fun TelegramBotImpl.send(
    messageContent: MessageContent, chatId: Long,
    crossinline copyApiBlock: CopyMessageApi.Builder.() -> Unit = {},
    crossinline builderFactory: BuilderFactory = { SendMessageApi.builder() }
): TelegramMessageReceipt {
    if (messageContent is TelegramMessageContent) {
        return messageContent.source.toCopyApi(ChatId(chatId), copyApiBlock)
            .requestDataBy(this)
            .toTelegramMessageReceipt(this, chatId)
    }

    return send(messageContent.messages, chatId) { builderFactory() }
}

internal suspend fun TelegramBotImpl.send(
    message: Message,
    chatId: Long,
    builderFactory: BuilderFactory = DefaultBuilderFactory
): TelegramMessageReceipt {
    val funcList = message.resolve {
        builderFactory().also {
            if (it.chatId == null) {
                it.chatId = ChatId(chatId)
            }
        }
    }

    fun toReceipt(result: Any): TelegramSingleMessageReceipt {
        return when (result) {
            is StdlibMessage -> result.toTelegramMessageReceipt(this)
            is MessageId -> result.toTelegramMessageReceipt(this, chatId)
            else -> error("Unexpected result type: $result")
        }
    }

    when {
        funcList.isEmpty() -> error("Nothing to send, the message element list is empty.")
        funcList.size == 1 -> {
            val result = funcList.first()().requestDataBy(this)
            return when (result) {
                is StdlibMessage -> toReceipt(result)
                is MessageId -> toReceipt(result)
                else -> error("Unexpected result type: $result")
            }
        }

        else -> {
            // 00 -> nothing, 不可能到这儿
            // 01 -> only Message
            // 10 -> only MessageId
            // 11 -> 混杂的
            var messageTypeMark = 0
            val resultList = funcList.map {
                val result = it.invoke().requestDataBy(this)
                when (result) {
                    is StdlibMessage -> messageTypeMark = messageTypeMark or MESSAGE_MARK
                    is MessageId -> messageTypeMark = messageTypeMark or MESSAGE_ID_MARK
                }
                result
            }

            return when (messageTypeMark) {
                // only Message
                MESSAGE_MARK -> {
                    @Suppress("UNCHECKED_CAST")
                    val sourceList = resultList as List<StdlibMessage>
                    return sourceList.toTelegramMessageReceipt(this, chatId)
                }
                // only MessageId
                MESSAGE_ID_MARK -> {
                    @Suppress("UNCHECKED_CAST")
                    val idList = resultList as List<MessageId>
                    idList.toTelegramMessageReceipt(this, chatId)
                }

                MESSAGE_ALL_MARK -> {
                    val messageIds = resultList.map {
                        when (it) {
                            is StdlibMessage -> it.messageId
                            is MessageId -> it.messageId
                            else -> error("Unexpected result type: $it")
                        }
                    }

                    TelegramAggregatedMessageIdReceiptImpl(this, chatId, messageIds)
                }

                else -> error("Unexpected mark value: $messageTypeMark")
            }
        }
    }
}

private const val MESSAGE_MARK = 0b01
private const val MESSAGE_ID_MARK = 0b10
private const val MESSAGE_ALL_MARK = MESSAGE_MARK or MESSAGE_ID_MARK

internal suspend fun Message.resolve(
    builderFactory: BuilderFactory = DefaultBuilderFactory
): List<SendingMessageResolvedFunction> {
    return when (val m = this) {
        is Message.Element -> {
            val context = SendingMessageResolverContext(builderFactory)
            for (resolver in sendingResolvers) {
                resolver.resolve(0, m, this, context)
            }

            context.end()
        }

        is Messages -> {
            if (m.isEmpty()) {
                return emptyList()
            }

            val context = SendingMessageResolverContext(builderFactory)
            m.forEachIndexed { index, element ->
                for (resolver in sendingResolvers) {
                    resolver.resolve(index, element, this, context)
                }
            }

            context.end()
        }
    }
}

internal fun interface SendingMessageResolver {
    suspend fun resolve(
        index: Int,
        element: Message.Element,
        source: Message,
        context: SendingMessageResolverContext
    )
}

private val sendingResolvers = listOf(
    TextSendingResolver,
    TelegramMessageResultApiElementSendingResolver,
)

internal typealias BuilderFactory = () -> SendMessageApi.Builder

internal val DefaultBuilderFactory: BuilderFactory = { SendMessageApi.builder() }

internal typealias SendingMessageResolvedFunction = () -> TelegramApi<*> // * 只能是 Message 或 MessageId

internal class SendingMessageResolverContext(
    private val builderFactory: BuilderFactory,
) {
    private val apiStacks = mutableListOf<SendingMessageResolvedFunction>() // TelegramApi<Message>?

    /**
     * SendMessageApi 应当尽可能只有最终的一个。
     * 如果有冲突的属性，后者覆盖前者。
     * 如果有其他API，则根据 [_builder] 是否初始化为准，
     * 追加到之前或之后。
     */
    private var _builder: SendMessageApi.Builder? = null

    fun addToStackMsg(api: () -> TelegramApi<StdlibMessage>) {
        apiStacks.add(api)
    }

    fun addToStackMsgId(api: () -> TelegramApi<MessageId>) {
        apiStacks.add(api)
    }

    private val builderOrNew: SendMessageApi.Builder
        get() = _builder ?: builderFactory().also {
            _builder = it
            addToStackMsg { it.build() }
        }

    val builder: SendMessageApi.Builder
        get() = builderOrNew

    fun end(): List<SendingMessageResolvedFunction> {
        return apiStacks
    }
}
