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

package love.forte.simbot.telegram.inline

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import love.forte.simbot.telegram.type.Location
import love.forte.simbot.telegram.type.User

/**
 * [InlineQuery](https://core.telegram.org/bots/api#inlinequery)
 *
 * This object represents an incoming inline query.
 * When the user sends an empty query, your bot could return some default or trending results.
 *
 * @author ForteScarlet
 */
@Serializable
public data class InlineQuery(
    /**
     * Unique identifier for this query
     */
    val id: String,
    /**
     * Sender
     */
    val from: User,
    /**
     * Text of the query (up to 256 characters)
     */
    val query: String,
    /**
     * Offset of the results to be returned, can be controlled by the bot
     */
    val offset: String,
    /**
     * Optional. Type of the chat from which the inline query was sent. Can be either “sender” for a private chat with the inline query sender, “private”, “group”, “supergroup”, or “channel”. The chat type should be always known for requests sent from official clients and most third-party clients, unless the request was sent from a secret chat
     */
    @SerialName("chat_type")
    val chatType: String? = null,
    /**
     * Optional. Sender location, only for bots that request user location
     */
    val location: Location? = null,
)
