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

package love.forte.simbot.telegram.type

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * [ReplyParameters](https://core.telegram.org/bots/api#replyparameters)
 *
 * Describes reply parameters for the message that is being sent.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class ReplyParameters(
    /**
     * Identifier of the message that will be replied to in the current chat, or in the chat chat_id
     * if it is specified
     *
     * type: `Integer`
     */
    @SerialName("message_id")
    public val messageId: Int,
    /**
     * Optional.
     * If the message to be replied to is from a different chat, unique identifier for the chat or
     * username of the channel (in the format @channelusername)
     *
     * type: `Integer or String`
     */
    @SerialName("chat_id")
    public val chatId: ChatId? = null,
    /**
     * Optional.
     * Pass True if the message should be sent even if the specified message to be replied to is not
     * found; can be used only for replies in the same chat and forum topic.
     *
     * type: `Boolean`
     */
    @SerialName("allow_sending_without_reply")
    public val allowSendingWithoutReply: Boolean? = null,
    /**
     * Optional.
     * Quoted part of the message to be replied to; 0-1024 characters after entities parsing.
     * The quote must be an exact substring of the message to be replied to, including bold, italic,
     * underline, strikethrough, spoiler, and custom_emoji entities.
     * The message will fail to send if the quote isn't found in the original message.
     *
     * type: `String`
     */
    public val quote: String? = null,
    /**
     * Optional.
     * Mode for parsing entities in the quote.
     * See formatting options for more details.
     *
     * type: `String`
     */
    @SerialName("quote_parse_mode")
    public val quoteParseMode: String? = null,
    /**
     * Optional.
     * A JSON-serialized list of special entities that appear in the quote.
     * It can be specified instead of quote_parse_mode.
     *
     * type: `Array of MessageEntity`
     */
    @SerialName("quote_entities")
    public val quoteEntities: List<MessageEntity>? = null,
    /**
     * Optional.
     * Position of the quote in the original message in UTF-16 code units
     *
     * type: `Integer`
     */
    @SerialName("quote_position")
    public val quotePosition: Int? = null,
)
