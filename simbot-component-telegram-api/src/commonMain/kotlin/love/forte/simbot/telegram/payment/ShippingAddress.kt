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

package love.forte.simbot.telegram.payment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * [ShippingAddress](https://core.telegram.org/bots/api#shippingaddress)
 *
 * This object represents a shipping address.
 *
 * @author ForteScarlet
 */
@Serializable
public data class ShippingAddress(
    /**
     * Two-letter [ISO 3166-1 alpha-2](https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2) country code
     */
    @SerialName("country_code")
    val countryCode: String,
    /**
     * State, if applicable
     */
    val state: String,
    /**
     * City
     */
    val city: String,
    /**
     * First line for the address
     */
    @SerialName("street_line1")
    val streetLine1: String,
    /**
     * Second line for the address
     */
    @SerialName("street_line2")
    val streetLine2: String,
    /**
     * Address post code
     */
    @SerialName("post_code")
    val postCode: String,
)
