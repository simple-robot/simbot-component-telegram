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
 * [Contact](https://core.telegram.org/bots/api#contact)
 *
 * This object represents a phone contact.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class Contact(
    /**
     * Contact's phone number
     *
     * type: `String`
     */
    @SerialName("phone_number")
    public val phoneNumber: String,
    /**
     * Contact's first name
     *
     * type: `String`
     */
    @SerialName("first_name")
    public val firstName: String,
    /**
     * Optional. 
     * Contact's last name
     *
     * type: `String`
     */
    @SerialName("last_name")
    public val lastName: String? = null,
    /**
     * Optional. 
     * Contact's user identifier in Telegram. 
     * This number may have more than 32 significant bits and some programming languages may have
     * difficulty/silent defects in interpreting it. 
     * But it has at most 52 significant bits, so a 64-bit integer or double-precision float type
     * are safe for storing this identifier.
     *
     * type: `Integer`
     */
    @SerialName("user_id")
    public val userId: Long? = null,
    /**
     * Optional. 
     * Additional data about the contact in the form of a vCard
     *
     * type: `String`
     */
    public val vcard: String? = null,
)
