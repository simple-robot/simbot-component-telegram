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
 * [TextQuote](https://core.telegram.org/bots/api#textquote)
 *
 * This object contains information about the quoted part of a message that is replied to by the
 * given message.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class TextQuote(
    /**
     * Text of the quoted part of a message that is replied to by the given message
     *
     * type: `String`
     */
    public val text: String,
    /**
     * Optional.
     * Special entities that appear in the quote.
     * Currently, only bold, italic, underline, strikethrough, spoiler, and custom_emoji entities
     * are kept in quotes.
     *
     * type: `Array of MessageEntity`
     */
    public val entities: List<MessageEntity>? = null,
    /**
     * Approximate quote position in the original message in UTF-16 code units as specified by the
     * sender
     *
     * type: `Integer`
     */
    public val position: Int,
    /**
     * Optional.
     * True, if the quote was chosen manually by the message sender.
     * Otherwise, the quote was added automatically by the server.
     *
     * type: `True`
     */
    @SerialName("is_manual")
    public val isManual: Boolean? = null,
)
