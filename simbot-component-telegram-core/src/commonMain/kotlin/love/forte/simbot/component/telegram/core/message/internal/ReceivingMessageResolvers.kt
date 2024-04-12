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

import love.forte.simbot.common.id.StringID.Companion.ID
import love.forte.simbot.component.telegram.core.message.StdlibMessage
import love.forte.simbot.component.telegram.core.message.TelegramMessageEntity
import love.forte.simbot.component.telegram.core.message.TelegramPhotoSizesImage
import love.forte.simbot.message.Messages
import love.forte.simbot.message.MessagesBuilder
import love.forte.simbot.telegram.type.MessageEntity
import love.forte.simbot.telegram.type.MessageEntityType

/**
 *
 */
internal fun StdlibMessage.toMessages(): Messages {
    val builder = MessagesBuilder.create()
    // TODO forwardOrigin: MessageOrigin?
    // TODO replyToMessage: Message ..?
    // TODO externalReply: ExternalReplyInfo ..?
    // TODO quote: TextQuote
    // TODO replyToStory: Story ..?
    // TODO hasProtectedContent: Boolean ..?

    text?.also { text ->
        if (entities.isNullOrEmpty()) {
            builder.add(text)
            return@also
        }

        var lastOffset = 0

        entities?.forEach { entity ->
            if (lastOffset != entity.offset) {
                // 上一次与当前中间夹着字符串
                builder.add(text.substring(lastOffset, entity.offset))
            }
            lastOffset = entity.offset + entity.length
            val messageEntity = when (entity.type.uppercase()) {
                MessageEntityType.TEXT_LINK.name -> {
                    TelegramMessageEntity.TextLink(
                        text = text.substring(entity),
                        url = entity.url,
                        entity
                    )
                }

                MessageEntityType.TEXT_MENTION.name -> {
                    TelegramMessageEntity.TextMention(
                        text = text.substring(entity),
                        user = entity.user,
                        entity
                    )
                }

                MessageEntityType.PRE.name -> {
                    TelegramMessageEntity.Pre(
                        text = text.substring(entity),
                        language = entity.language,
                        entity
                    )
                }

                MessageEntityType.CUSTOM_EMOJI.name -> {
                    TelegramMessageEntity.CustomEmoji(
                        text = text.substring(entity),
                        customEmojiId = entity.customEmojiId?.ID,
                        entity
                    )
                }

                // simple
                else -> {
                    TelegramMessageEntity.Simple(
                        text = text.substring(entity),
                        type = entity.type,
                        entity
                    )
                }
            }
            builder.add(messageEntity)
        }

        if (lastOffset != text.length) {
            // 字符串有残留
            builder.add(text.substring(lastOffset, text.length))
        }


    }

    // TODO linkPreviewOptions: LinkPreviewOptions
    // TODO animation: Animation
    // TODO audio: Audio
    // TODO document: Document
    // photo: List<PhotoSize>
    builder.resolveImage(this)

    // TODO sticker: Sticker
    // TODO story: Story
    // TODO video: Video
    // TODO videoNote: VideoNote
    // TODO voice: Voice
    // TODO voice: Voice
    // TODO - caption: String? = null,
    //      - captionEntities: List<MessageEntity>? = null,

    // TODO contact: Contact
    // TODO dice: Dice
    // TODO game: Game
    // TODO poll: Poll
    // TODO venue: Venue
    // TODO location: Location

    return builder.build()
}

/**
 * 解析 photo 到 [TelegramPhotoSizesImage].
 */
private fun MessagesBuilder.resolveImage(source: StdlibMessage) {
    source.photo?.also { photo ->
        add(TelegramPhotoSizesImage(photo))
    }
}


private fun String.substring(entity: MessageEntity): String =
    substring(entity.offset, entity.offset + entity.length)
