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

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import love.forte.simbot.common.id.ID
import love.forte.simbot.message.MentionMessage
import love.forte.simbot.message.PlainText
import love.forte.simbot.telegram.type.Message
import love.forte.simbot.telegram.type.MessageEntity
import love.forte.simbot.telegram.type.MessageEntityType
import love.forte.simbot.telegram.type.User
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

/**
 * A [TelegramMessageElement] for [MessageEntity].
 *
 * ## 发送
 * 当发送一个或多个 [TelegramMessageEntity] 时，
 * 它们会自动聚合为 `text` 和 `entities`。
 *
 * 例如:
 *
 * ```Kotlin
 * send(
 *     createTextLink("GitHub", "https://github.com/") +
 *     ",Hello,".toText() +
 *     create("1=1", MessageEntityType.CODE)
 * )
 * ```
 * 发送时会产生大概这样的 [Message]:
 * ```json
 * {
 *    ...
 *    "text": "GitHub,Hello,1=1",
 *    "entities": [
 *      {
 *        "type": "text_link",
 *        "offset": 0,
 *        "length": 6
 *      },
 *      {
 *        "type": "code",
 *        "offset": 13,
 *        "length": 3
 *      }
 *    ]
 * }
 * ```
 *
 */
@Serializable
@SerialName("telegram.m.message_entity")
public sealed class TelegramMessageEntity : TelegramMessageElement, PlainText {
    abstract override val text: String
    public abstract val type: String

    /**
     * The [MessageEntity] (from received message event).
     * `null` if not from event.
     */
    public abstract val sourceEntity: MessageEntity?

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TelegramMessageEntity) return false

        if (text != other.text) return false
        if (type != other.type) return false
        if (sourceEntity != other.sourceEntity) return false

        return true
    }

    override fun hashCode(): Int {
        var result = text.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + (sourceEntity?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "TelegramMessageEntity(" +
            "text='$text', " +
            "type='$type', " +
            "sourceEntity=$sourceEntity)"
    }

    public companion object {
        /**
         * Create [Simple].
         */
        @JvmStatic
        public fun create(text: String, type: String): TelegramMessageEntity =
            Simple(text, type, null)

        /**
         * Create [Simple].
         */
        @JvmStatic
        public fun create(text: String, type: MessageEntityType): TelegramMessageEntity =
            create(text, type.typeValue)

        /**
         * Create [TextLink].
         */
        @JvmStatic
        @JvmOverloads
        public fun createTextLink(text: String, url: String? = null): TelegramMessageEntity =
            TextLink(text, url, null)

        /**
         * Create [TextMention].
         */
        @JvmStatic
        @JvmOverloads
        public fun createTextMention(text: String, user: User? = null): TelegramMessageEntity =
            TextMention(text, user, null)

        /**
         * Create [Pre].
         */
        @JvmStatic
        @JvmOverloads
        public fun createPre(text: String, language: String? = null): TelegramMessageEntity =
            Pre(text, language, null)

        /**
         * Create [CustomEmoji].
         */
        @JvmStatic
        @JvmOverloads
        public fun createCustomEmoji(text: String, customEmojiId: ID? = null): TelegramMessageEntity =
            CustomEmoji(text, customEmojiId, null)
    }

    /**
     * A simple implementation for [TelegramMessageEntity].
     */
    @Serializable
    @SerialName("telegram.m.message_entity.simple")
    public class Simple internal constructor(
        override val text: String,
        override val type: String,
        override val sourceEntity: MessageEntity?
    ) : TelegramMessageEntity()

    /**
     * An implementation with type [MessageEntityType.TEXT_LINK]
     * for [TelegramMessageEntity].
     */
    @Serializable
    @SerialName("telegram.m.message_entity.text_link")
    public class TextLink internal constructor(
        override val text: String,
        public val url: String?,
        override val sourceEntity: MessageEntity?
    ) : TelegramMessageEntity() {
        override val type: String
            get() = MessageEntityType.TEXT_LINK.typeValue
    }

    /**
     * An implementation with type [MessageEntityType.TEXT_MENTION]
     * for [TelegramMessageEntity].
     */
    @Serializable
    @SerialName("telegram.m.message_entity.text_mention")
    public class TextMention internal constructor(
        override val text: String,
        public val user: User?,
        override val sourceEntity: MessageEntity?
    ) : TelegramMessageEntity(), MentionMessage {
        override val type: String
            get() = MessageEntityType.TEXT_MENTION.typeValue
    }

    /**
     * An implementation with type [MessageEntityType.PRE]
     * for [TelegramMessageEntity].
     */
    @Serializable
    @SerialName("telegram.m.message_entity.pre")
    public class Pre internal constructor(
        override val text: String,
        public val language: String?,
        override val sourceEntity: MessageEntity?
    ) : TelegramMessageEntity() {
        override val type: String
            get() = MessageEntityType.PRE.typeValue
    }

    /**
     * An implementation with type [MessageEntityType.PRE]
     * for [TelegramMessageEntity].
     */
    @Serializable
    @SerialName("telegram.m.message_entity.custom_emoji")
    public class CustomEmoji internal constructor(
        override val text: String,
        public val customEmojiId: ID?,
        override val sourceEntity: MessageEntity?
    ) : TelegramMessageEntity() {
        override val type: String
            get() = MessageEntityType.CUSTOM_EMOJI.typeValue
    }


}
