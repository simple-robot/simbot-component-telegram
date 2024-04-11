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

import love.forte.simbot.component.telegram.core.message.StdlibMessage
import love.forte.simbot.message.Messages
import love.forte.simbot.message.MessagesBuilder

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

    text?.also { builder.add(it) }

    // TODO entities ..?

    // TODO linkPreviewOptions: LinkPreviewOptions
    // TODO animation: Animation
    // TODO audio: Audio
    // TODO document: Document
    // TODO photo: List<PhotoSize>
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
