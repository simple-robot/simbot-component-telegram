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
 * [ResponseParameters](https://core.telegram.org/bots/api#responseparameters)
 *
 * Describes why a request was unsuccessful.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class ResponseParameters(
    /**
     * Optional.
     * The group has been migrated to a supergroup with the specified identifier.
     * This number may have more than 32 significant bits and some programming languages may have
     * difficulty/silent defects in interpreting it.
     * But it has at most 52 significant bits, so a signed 64-bit integer or double-precision float
     * type are safe for storing this identifier.
     *
     * type: `Integer`
     */
    @SerialName("migrate_to_chat_id")
    public val migrateToChatId: Long? = null,
    /**
     * Optional.
     * In case of exceeding flood control, the number of seconds left to wait before the request can
     * be repeated
     *
     * type: `Integer`
     */
    @SerialName("retry_after")
    public val retryAfter: Int? = null,
)
