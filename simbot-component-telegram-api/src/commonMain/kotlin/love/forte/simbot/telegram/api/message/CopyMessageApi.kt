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
import love.forte.simbot.telegram.api.utils.requireNotNullNamed
import love.forte.simbot.telegram.type.*
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic


/**
 * [copyMessage](https://core.telegram.org/bots/api#copymessage)
 *
 * > Use this method to copy messages of any kind.
 * Service messages, giveaway messages, giveaway winners messages,
 * and invoice messages can't be copied.
 * A quiz [poll](https://core.telegram.org/bots/api#poll)
 * can be copied only if the value of the field
 * `correct_option_id` is known to the bot.
 * The method is analogous to the method [forwardMessage](https://core.telegram.org/bots/api#forwardmessage),
 * but the copied message doesn't have a link to the original message.
 * Returns the [MessageId] of the sent message on success.
 *
 * @author ForteScarlet
 */
public class CopyMessageApi private constructor(body: Body) : SimpleBodyTelegramApi<MessageId>() {
    public companion object Factory {
        private const val NAME = "copyMessage"
        private val RES = TelegramApiResult.serializer(MessageId.serializer())

        /**
         * Create a [CopyMessageApi] via [body].
         */
        @JvmStatic
        public fun create(body: Body): CopyMessageApi = CopyMessageApi(body)

        /**
         * Create a [CopyMessageApi] based only on required.
         */
        @JvmStatic
        public fun create(chatId: ChatId, fromChatId: ChatId, messageId: Int): CopyMessageApi = CopyMessageApi(
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

    override val responseDeserializer: DeserializationStrategy<MessageId>
        get() = MessageId.serializer()

    override val resultDeserializer: DeserializationStrategy<TelegramApiResult<MessageId>>
        get() = RES

    /**
     * Request body for [CopyMessageApi].
     *
     * Note: Can only be used for serialization, not deserialization.
     *
     * @property chatId Required. Unique identifier for the target chat or username of the target channel (in the format `@channelusername`)
     * @property messageThreadId Optional. Unique identifier for the target message thread (topic) of the forum; for forum supergroups only
     * @property fromChatId Required. Unique identifier for the chat where the original message was sent (or channel username in the format `@channelusername`)
     * @property messageId Required. Message identifier in the chat specified in [fromChatId]
     * @property caption Optional. New caption for media, 0-1024 characters after entities parsing. If not specified, the original caption is kept
     * @property parseMode Optional. Mode for parsing entities in the new caption. See [formatting options](https://core.telegram.org/bots/api#formatting-options) for more details.
     * @property captionEntities Optional. A JSON-serialized list of special entities that appear in the new caption, which can be specified instead of parse_mode
     * @property disableNotification Optional. Sends the message [silently](https://telegram.org/blog/channels-2-0#silent-messages). Users will receive a notification with no sound.
     * @property protectContent Optional. Protects the contents of the sent message from forwarding and saving
     * @property replyParameters Optional. Description of the message to reply to
     * @property replyMarkup Optional. Additional interface options. A JSON-serialized object for an [inline keyboard](https://core.telegram.org/bots/features#inline-keyboards),
     * [custom reply keyboard](https://core.telegram.org/bots/features#keyboards),
     * instructions to remove reply keyboard or to force a reply from the user.
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
        public val caption: String? = null,
        @SerialName("parse_mode")
        public val parseMode: String? = null,
        @SerialName("caption_entities")
        public val captionEntities: Collection<MessageEntity>? = null,
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
     * Builder for [Body] or [CopyMessageApi].
     */
    @Suppress("MemberVisibilityCanBePrivate")
    public class Builder {
        //region Requires

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

        public var messageId: Int? = null
        //endregion

        // Optionals
        public var messageThreadId: Int? = null


        public var caption: String? = null
        public var parseMode: String? = null
        public var captionEntities: Collection<MessageEntity>? = null
        public var disableNotification: Boolean? = null
        public var protectContent: Boolean? = null
        public var replyParameters: ReplyParameters? = null
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
        public fun buildBody(): Body {
            val chatId = requireNotNullNamed(chatId) { "chatId" }
            val fromChatId = requireNotNullNamed(fromChatId) { "fromChatId" }
            val messageId = requireNotNullNamed(messageId) { "messageId" }

            return Body(
                chatId = chatId,
                fromChatId = fromChatId,
                messageId = messageId,
                messageThreadId = messageThreadId,
                caption = caption,
                parseMode = parseMode,
                captionEntities = captionEntities,
                disableNotification = disableNotification,
                protectContent = protectContent,
                replyParameters = replyParameters,
                replyMarkup = replyMarkup,
            )
        }

        /**
         * Build as [CopyMessageApi].
         */
        public fun build(): CopyMessageApi = create(buildBody())
    }
}


/**
 * Build a [CopyMessageApi] via [CopyMessageApi.Builder].
 */
@JvmSynthetic
public inline fun buildCopyMessageApi(block: CopyMessageApi.Builder.() -> Unit): CopyMessageApi =
    CopyMessageApi.builder().also(block).build()

/**
 * [buildForwardMessageApi] with required arguments.
 */
@JvmSynthetic
public inline fun buildCopyMessageApi(
    chatId: ChatId, fromChatId: ChatId, messageId: Int,
    block: CopyMessageApi.Builder.() -> Unit = {}
): CopyMessageApi = buildCopyMessageApi {
    this.chatId = chatId
    this.fromChatId = fromChatId
    this.messageId = messageId
    block()
}
