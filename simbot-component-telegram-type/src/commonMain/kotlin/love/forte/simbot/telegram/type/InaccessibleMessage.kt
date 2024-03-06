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
 * [InaccessibleMessage](https://core.telegram.org/bots/api#inaccessiblemessage)
 *
 * This object describes a message that was deleted or is otherwise inaccessible to the bot.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class InaccessibleMessage(
    /**
     * Chat the message belonged to
     *
     * type: `Chat`
     */
    public val chat: Chat,
    /**
     * Unique message identifier inside the chat
     *
     * type: `Integer`
     */
    @SerialName("message_id")
    public val messageId: Int,
    /**
     * Always 0.
     * The field can be used to differentiate regular and inaccessible messages.
     *
     * type: `Integer`
     */
    public val date: Int,
)
