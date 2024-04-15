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

import io.ktor.client.request.forms.*
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import love.forte.simbot.common.ktor.inputfile.InputFile
import love.forte.simbot.telegram.api.FormBodyTelegramApi
import love.forte.simbot.telegram.api.TelegramApiResult
import love.forte.simbot.telegram.api.internal.FormApiSerializer
import love.forte.simbot.telegram.api.utils.StringValueInputFile
import love.forte.simbot.telegram.type.ChatId
import love.forte.simbot.telegram.type.Message
import love.forte.simbot.telegram.type.MessageEntity
import love.forte.simbot.telegram.type.ReplyParameters
import kotlin.jvm.JvmStatic


/**
 * [sendDocument](https://core.telegram.org/bots/api#senddocument)
 *
 * @author ForteScarlet
 */
public class SendDocumentApi private constructor(body: Body) : FormBodyTelegramApi<Message>() {
    public companion object Factory {
        private const val NAME = "sendDocument"
        private val RES = TelegramApiResult.serializer(Message.serializer())
        private val formSerializer = FormApiSerializer(
            Body.serializer()
        )

        /**
         * Create a [SendDocumentApi].
         */
        @JvmStatic
        public fun create(body: Body): SendDocumentApi = SendDocumentApi(body)


        /**
         * Create [SendDocumentApi] with only required params.
         *
         * @param chatId The chat id.
         * @param document The Document file you want to send.
         */
        @JvmStatic
        public fun create(chatId: ChatId, document: InputFile): SendDocumentApi =
            SendDocumentApi(
                Body().also {
                    it.chatId = chatId
                    it.document = document
                }
            )
    }

    override val name: String
        get() = NAME
    override val body: MultiPartFormDataContent =
        MultiPartFormDataContent(
            formData(
                values = formSerializer.serialize(body).toTypedArray(),
            )
        )
    override val responseDeserializer: DeserializationStrategy<Message>
        get() = Message.serializer()

    override val resultDeserializer: DeserializationStrategy<TelegramApiResult<Message>>
        get() = RES

    /**
     * Request body for [SendDocumentApi].
     */
    @Serializable
    public class Body {
        /**
         * Optional.
         * Unique identifier of the business connection on behalf of which the message will be sent
         */
        @SerialName("business_connection_id")
        public var businessConnectionId: String? = null

        /**
         * Required unique identifier for the target chat or username
         * of the target channel (in the format `@channelusername`)
         */
        @SerialName("chat_id")
        @Required
        public lateinit var chatId: ChatId

        /**
         * Optional.
         * Unique identifier for the target message thread (topic) of the forum;
         * for forum supergroups only
         */
        @SerialName("message_thread_id")
        public var messageThreadId: Int? = null

        /**
         * Required.
         * File to send.
         * (See [the documentation](https://core.telegram.org/bots/api#senddocument)).
         *
         * See also [StringValueInputFile] for send a photo with type of [String].
         *
         * @see StringValueInputFile
         */
        @Required
        public lateinit var document: InputFile

        /**
         * Optional.
         * Thumbnail of the file sent;
         *
         * (See [the documentation](https://core.telegram.org/bots/api#senddocument)).
         *
         * See also [StringValueInputFile] for send a photo with type of [String].
         *
         * @see StringValueInputFile
         */
        public var thumbnail: InputFile? = null

        /**
         * Optional.
         * Photo caption (may also be used when resending photos by file_id),
         * 0-1024 characters after entities parsing
         */
        public var caption: String? = null

        /**
         * Optional.
         * Mode for parsing entities in the photo caption.
         * See [formatting options](https://core.telegram.org/bots/api#formatting-options)
         * for more details.
         */
        @SerialName("parse_mode")
        public var parseMode: String? = null

        /**
         * Optional.
         * A JSON-serialized list of special entities that appear in the caption,
         * which can be specified instead of parse_mode
         */
        @SerialName("caption_entities")
        public var captionEntities: MutableCollection<MessageEntity>? = null

        /**
         * Optional.
         * Disables automatic server-side content type detection
         * for files uploaded using multipart/form-data
         */
        @SerialName("disable_content_type_detection")
        public var disableContentTypeDetection: Boolean? = null

        /**
         * Optional.
         * Sends the message silently. Users will receive a notification with no sound.
         */
        @SerialName("disable_notification")
        public var disableNotification: Boolean? = null

        /**
         * Optional.
         * Protects the contents of the sent message from forwarding and saving
         */
        @SerialName("protect_content")
        public var protectContent: Boolean? = null

        /**
         * Optional.
         * Description of the message to reply to
         */
        @SerialName("reply_parameters")
        public var replyParameters: ReplyParameters? = null

        /**
         * Additional interface options.
         * A JSON-serialized object for an inline keyboard,
         * custom reply keyboard,
         * instructions to remove a reply keyboard or to force a reply from the user.
         * Not supported for messages sent on behalf of a business account
         */
        @SerialName("reply_markup")
        public var replyMarkup: ReplyMarkupWrapper? = null
    }
}

/**
 * Create [SendDocumentApi].
 */
public inline fun buildSendDocumentApi(block: SendDocumentApi.Body.() -> Unit): SendDocumentApi =
    SendDocumentApi.create(SendDocumentApi.Body().apply(block))
