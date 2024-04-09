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

package love.forte.simbot.telegram.type.inline

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import love.forte.simbot.telegram.type.User

/**
 * [ChosenInlineResult](https://core.telegram.org/bots/api#choseninlineresult)
 *
 * Represents a result of an inline query that was chosen by the user and sent to their chat partner.
 *
 * @author ForteScarlet
 */
@Serializable
public data class ChosenInlineResult(
    /**
     * The unique identifier for the result that was chosen
     */
    @SerialName("result_id")
    val resultId: String,
    /**
     * The user that chose the result
     */
    val from: User,
    /**
     * Optional.
     * Sender location, only for bots that require user location
     */
    val location: love.forte.simbot.telegram.type.Location? = null,
    /**
     * Optional.
     * Identifier of the sent inline message. Available only if there is an inline keyboard attached to the message. Will be also received in callback queries and can be used to edit the message.
     */
    @SerialName("inline_message_id")
    val inlineMessageId: String? = null,
    /**
     * The query that was used to obtain the result
     */
    val query: String,
)
