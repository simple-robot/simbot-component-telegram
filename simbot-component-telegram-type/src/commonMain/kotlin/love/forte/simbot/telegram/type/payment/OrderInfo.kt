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

package love.forte.simbot.telegram.type.payment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * [OrderInfo](https://core.telegram.org/bots/api#orderinfo)
 *
 * This object represents information about an order.
 *
 * @author ForteScarlet
 */
@Serializable
public data class OrderInfo(
    /**
     * Optional.
     * User name
     */
    val name: String? = null,
    /**
     * Optional.
     * User's phone number
     */
    @SerialName("phone_number")
    val phoneNumber: String? = null,
    /**
     * Optional.
     * User email
     */
    val email: String? = null,
    /**
     * Optional.
     * User shipping address
     */
    @SerialName("shipping_address")
    val shippingAddress: love.forte.simbot.telegram.type.payment.ShippingAddress? = null,
)
