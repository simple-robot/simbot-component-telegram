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

package love.forte.simbot.telegram.api.message

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationStrategy
import love.forte.simbot.telegram.api.SimpleBodyTelegramApi
import love.forte.simbot.telegram.api.TelegramApiResult
import love.forte.simbot.telegram.api.utils.requireNotNullNamed
import love.forte.simbot.telegram.type.ChatId
import love.forte.simbot.telegram.type.Message
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic


/**
 * [forwardMessage](https://core.telegram.org/bots/api#forwardMessage)
 *
 * > Use this method to forward messages of any kind.
 * Service messages and messages with protected content can't be forwarded.
 * On success, the sent [Message] is returned.
 *
 * @author ForteScarlet
 */
public class ForwardMessageApi private constructor(body: Body) : SimpleBodyTelegramApi<Message>() {
    public companion object Factory {
        private const val NAME = "copyMessage"
        private val RES = TelegramApiResult.serializer(Message.serializer())

        /**
         * Create a [ForwardMessageApi] via [body].
         */
        @JvmStatic
        public fun create(body: Body): ForwardMessageApi = ForwardMessageApi(body)

        /**
         * Create a [ForwardMessageApi] based only on required.
         */
        @JvmStatic
        public fun create(chatId: ChatId, fromChatId: ChatId, messageId: Int): ForwardMessageApi = ForwardMessageApi(
            Body(
                chatId = chatId,
                fromChatId = fromChatId,
                messageId = messageId
            )
        )

        /**
         * Create a [Builder].
         */
        @JvmStatic
        public fun builder(): Builder = Builder()

    }

    override val name: String
        get() = NAME

    override val body: Any = body

    override val bodySerializationStrategy: SerializationStrategy<Any>?
        get() = super.bodySerializationStrategy

    override val responseDeserializer: DeserializationStrategy<Message>
        get() = Message.serializer()

    override val resultDeserializer: DeserializationStrategy<TelegramApiResult<Message>>
        get() = RES

    /**
     * Request body for [ForwardMessageApi].
     *
     * Note: Can only be used for serialization, not deserialization.
     *
     * @property chatId Required. Unique identifier for the target chat or username of the target channel (in the format `@channelusername`)
     * @property messageThreadId Optional. Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
     * @property fromChatId Required. Unique identifier for the chat where the original message was sent (or channel username in the format `@channelusername`)
     * @property messageId Required. Message identifier in the chat specified in [fromChatId]
     * @property disableNotification Optional. Sends the message [silently](https://telegram.org/blog/channels-2-0#silent-messages). Users will receive a notification with no sound.
     * @property protectContent Optional. Protects the contents of the sent message from forwarding and saving
     *
     */
    @Serializable
    public data class Body internal constructor(
        @SerialName("chat_id")
        public val chatId: ChatId, // Integer or String
        @SerialName("message_thread_id")
        public val messageThreadId: Int? = null,
        @SerialName("from_chat_id")
        public val fromChatId: ChatId,
        @SerialName("message_id")
        public val messageId: Int,
        @SerialName("disable_notification")
        public val disableNotification: Boolean? = null,
        @SerialName("protect_content")
        public val protectContent: Boolean? = null,
    )

    /**
     * Builder for [Body] or [ForwardMessageApi].
     */
    @Suppress("MemberVisibilityCanBePrivate")
    public class Builder {
        // Requires

        /**
         * @see Body.chatId
         */
        public var chatId: ChatId? = null

        /**
         * @see chatId
         */
        public fun chatId(value: Long) {
            chatId = ChatId(value)
        }

        /**
         * @see chatId
         */
        public fun chatId(value: String) {
            chatId = ChatId(value)
        }

        /**
         * @see Body.fromChatId
         */
        public var fromChatId: ChatId? = null


        /**
         * @see fromChatId
         */
        public fun fromChatId(value: Long) {
            chatId = ChatId(value)
        }

        /**
         * @see fromChatId
         */
        public fun fromChatId(value: String) {
            chatId = ChatId(value)
        }

        /**
         * @see Body.messageId
         */
        public var messageId: Int? = null

        // Optionals
        public var messageThreadId: Int? = null
        public var disableNotification: Boolean? = null
        public var protectContent: Boolean? = null

        /**
         * Build as [Body].
         */
        public fun buildBody(): Body {
            val chatId = requireNotNullNamed(chatId) { "chatId" }
            val fromChatId = requireNotNullNamed(fromChatId) { "fromChatId" }
            val messageId = requireNotNullNamed(messageId) { "messageId" }

            return Body(
                chatId = chatId,
                fromChatId = fromChatId,
                messageId = messageId,
                messageThreadId = messageThreadId,
                disableNotification = disableNotification,
                protectContent = protectContent,
            )
        }

        /**
         * Build as [ForwardMessageApi].
         */
        public fun build(): ForwardMessageApi = create(buildBody())
    }
}


/**
 * Build a [ForwardMessageApi] via [ForwardMessageApi.Builder].
 */
@JvmSynthetic
public inline fun buildForwardMessageApi(block: ForwardMessageApi.Builder.() -> Unit): ForwardMessageApi =
    ForwardMessageApi.builder().also(block).build()

/**
 * [buildForwardMessageApi] with required arguments.
 */
@JvmSynthetic
public inline fun buildForwardMessageApi(
    chatId: ChatId, fromChatId: ChatId, messageId: Int,
    block: ForwardMessageApi.Builder.() -> Unit = {}
): ForwardMessageApi = buildForwardMessageApi {
    this.chatId = chatId
    this.fromChatId = fromChatId
    this.messageId = messageId
    block()
}
