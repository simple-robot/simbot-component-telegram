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
 * [CallbackQuery](https://core.telegram.org/bots/api#callbackquery)
 *
 * This object represents an incoming callback query from a callback button in an inline keyboard.
 * If the button that originated the query was attached to a message sent by the bot, the field message
 * will be present. If the button was attached to a message sent via the bot (in inline mode), the
 * field inline_message_id will be present. Exactly one of the fields data or game_short_name will be
 * present.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class CallbackQuery(
    /**
     * Unique identifier for this query
     *
     * type: `String`
     */
    public val id: String,
    /**
     * Sender
     *
     * type: `User`
     */
    public val from: User,
    /**
     * Optional. 
     * Message sent by the bot with the callback button that originated the query
     *
     * type: `MaybeInaccessibleMessage`
     */
    public val message: MaybeInaccessibleMessage? = null,
    /**
     * Optional. 
     * Identifier of the message sent via the bot in inline mode, that originated the query.
     *
     * type: `String`
     */
    @SerialName("inline_message_id")
    public val inlineMessageId: String? = null,
    /**
     * Global identifier, uniquely corresponding to the chat to which the message with the callback
     * button was sent. 
     * Useful for high scores in games.
     *
     * type: `String`
     */
    @SerialName("chat_instance")
    public val chatInstance: String,
    /**
     * Optional. 
     * Data associated with the callback button. 
     * Be aware that the message originated the query can contain no callback buttons with this
     * data.
     *
     * type: `String`
     */
    public val `data`: String? = null,
    /**
     * Optional. 
     * Short name of a Game to be returned, serves as the unique identifier for the game
     *
     * type: `String`
     */
    @SerialName("game_short_name")
    public val gameShortName: String? = null,
)
