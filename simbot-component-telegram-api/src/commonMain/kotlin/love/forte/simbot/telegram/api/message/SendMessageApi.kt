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
import love.forte.simbot.telegram.api.message.ReplyMarkupWrapper.Companion.wrapper
import love.forte.simbot.telegram.api.message.SendMessageApi.Builder
import love.forte.simbot.telegram.api.utils.requireNotNullNamed
import love.forte.simbot.telegram.type.*
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic


/**
 * [sendMessage](https://core.telegram.org/bots/api#sendmessage)
 *
 * > Use this method to send text messages. On success, the sent [Message] is returned.
 *
 * @see Builder
 * @see buildSendMessageApi
 *
 * @author ForteScarlet
 */
public class SendMessageApi private constructor(body: Body) : SimpleBodyTelegramApi<Message>() {
    public companion object Factory {
        private const val NAME = "sendMessage"

        private val RES = TelegramApiResult.serializer(Message.serializer())

        /**
         * Create a [SendMessageApi] based only on required arguments.
         *
         * @param chatId see [Body]
         * @param text see [Body]
         */
        @JvmStatic
        public fun create(chatId: ChatId, text: String): SendMessageApi =
            create(Body(chatId = chatId, text = text))

        /**
         * Create a [SendMessageApi] via [body].
         *
         * @param body a [Body] instance.
         */
        @JvmStatic
        public fun create(body: Body): SendMessageApi =
            SendMessageApi(body)

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
     * Request body for [SendMessageApi].
     *
     * Note: Can only be used for serialization, not deserialization.
     *
     * @property chatId [Long] or [String] (See also [ChatId]).
     * Unique identifier for the target chat or username of the target channel (in the format `@channelusername`)
     * @property messageThreadId Optional.
     * Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
     * @property text Text of the message to be sent, 1-4096 characters after entities parsing
     * @property parseMode Optional.
     * Mode for parsing entities in the message text.
     * See [formatting options](https://core.telegram.org/bots/api#formatting-options) for more details.
     * (See also [FormattingOption])
     * @property entities Optional.
     * A JSON-serialized list of special entities that appear in message text,
     * which can be specified instead of [`parse_mode`][parseMode]
     * @property linkPreviewOptions Optional.
     * Link preview generation options for the message
     * @property disableNotification Optional.
     * Sends the message silently. Users will receive a notification with no sound.
     * @property protectContent Optional.
     * Protects the contents of the sent message from forwarding and saving
     * @property replyParameters Optional.
     * Description of the message to reply to
     * @property replyMarkup
     * [InlineKeyboardMarkup] or [ReplyKeyboardMarkup] or [ReplyKeyboardRemove] or [ForceReply].
     * Optional.
     * Additional interface options.
     * A JSON-serialized object for an inline keyboard, custom reply keyboard,
     * instructions to remove reply keyboard or to force a reply from the user.
     *
     * @see Builder
     */
    @Serializable
    public data class Body internal constructor(
        @SerialName("chat_id")
        public val chatId: ChatId, // Integer or String
        public val text: String,

        @SerialName("message_thread_id")
        public val messageThreadId: Int? = null,
        @SerialName("parse_mode")
        public val parseMode: String? = null,
        public val entities: Collection<MessageEntity>? = null,
        @SerialName("link_preview_options")
        public val linkPreviewOptions: LinkPreviewOptions? = null,
        @SerialName("disable_notification")
        public val disableNotification: Boolean? = null,
        @SerialName("protect_content")
        public val protectContent: Boolean? = null,
        @SerialName("reply_parameters")
        public val replyParameters: ReplyParameters? = null,

        @SerialName("reply_markup")
        public val replyMarkup: ReplyMarkupWrapper? = null,
    )

    /**
     * Builder for [Body].
     */
    @Suppress("MemberVisibilityCanBePrivate")
    public class Builder {
        // Require

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
         * @see Body.text
         */
        public var text: String? = null

        // Optional

        /**
         * @see Body.messageThreadId
         */
        public var messageThreadId: Int? = null

        /**
         * @see Body.parseMode
         */
        public var parseMode: String? = null

        /**
         * @see Body.parseMode
         */
        public fun parseMode(formattingOption: FormattingOption?) {
            parseMode = formattingOption?.value
        }

        /**
         * @see Body.entities
         */
        public var entities: Collection<MessageEntity>? = null

        /**
         * @see Body.linkPreviewOptions
         */
        public var linkPreviewOptions: LinkPreviewOptions? = null

        /**
         * @see Body.disableNotification
         */
        public var disableNotification: Boolean? = null

        /**
         * @see Body.protectContent
         */
        public var protectContent: Boolean? = null

        /**
         * @see Body.replyParameters
         */
        public var replyParameters: ReplyParameters? = null

        /**
         * [InlineKeyboardMarkup] or [ReplyKeyboardMarkup] or [ReplyKeyboardRemove] or [ForceReply].
         *
         * @see Body.replyMarkup
         */
        public var replyMarkup: ReplyMarkupWrapper? = null

        /**
         * @see Body.replyMarkup
         */
        public fun replyMarkup(value: InlineKeyboardMarkup) {
            this.replyMarkup = value.wrapper()
        }

        /**
         * @see Body.replyMarkup
         */
        public fun replyMarkup(value: ReplyKeyboardMarkup) {
            this.replyMarkup = value.wrapper()
        }

        /**
         * @see Body.replyMarkup
         */
        public fun replyMarkup(value: ReplyKeyboardRemove) {
            this.replyMarkup = value.wrapper()
        }

        /**
         * @see Body.replyMarkup
         */
        public fun replyMarkup(value: ForceReply) {
            this.replyMarkup = value.wrapper()
        }

        /**
         * Build as [Body].
         */
        @Suppress("MemberVisibilityCanBePrivate")
        public fun buildBody(): Body {
            val chatId = requireNotNullNamed(chatId) { "chatId" }
            val text = requireNotNullNamed(text) { "text" }

            return Body(
                chatId = chatId,
                text = text,
                messageThreadId = messageThreadId,
                parseMode = parseMode,
                entities = entities,
                linkPreviewOptions = linkPreviewOptions,
                disableNotification = disableNotification,
                protectContent = protectContent,
                replyParameters = replyParameters,
                replyMarkup = replyMarkup,
            )
        }

        /**
         * Build as [SendMessageApi].
         */
        public fun build(): SendMessageApi = create(buildBody())
    }

}


/**
 * Build a [SendMessageApi] via [Builder].
 */
@JvmSynthetic
public inline fun buildSendMessageApi(block: Builder.() -> Unit): SendMessageApi =
    SendMessageApi.builder().also(block).build()

/**
 * [buildSendMessageApi] with required arguments.
 */
@JvmSynthetic
public inline fun buildSendMessageApi(
    chatId: ChatId, text: String,
    block: Builder.() -> Unit = {}
): SendMessageApi = buildSendMessageApi {
    this.chatId = chatId
    this.text = text
    block()
}
