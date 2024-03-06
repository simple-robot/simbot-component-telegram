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
import love.forte.simbot.telegram.type.User

/**
 * [PreCheckoutQuery](https://core.telegram.org/bots/api#precheckoutquery)
 *
 * This object contains information about an incoming pre-checkout query.
 *
 * @author ForteScarlet
 */
@Serializable
public data class PreCheckoutQuery(
    /**
     * Unique query identifier
     */
    val id: String,
    /**
     * User who sent the query
     */
    val from: User,
    /**
     * Three-letter ISO 4217 currency code
     */
    val currency: String,
    /**
     * Total price in the smallest units of the currency (integer, not float/double).
     * For example, for a price of US$ 1.45 pass amount = 145. See the exp parameter in currencies.json, it shows the number of digits past the decimal point for each currency (2 for the majority of currencies).
     */
    @SerialName("total_amount")
    val totalAmount: Int,
    /**
     * Bot specified invoice payload
     */
    @SerialName("invoice_payload")
    val invoicePayload: String,
    /**
     * Optional.
     * Identifier of the shipping option chosen by the user
     */
    @SerialName("shipping_option_id")
    val shippingOptionId: String? = null,
    /**
     * Optional.
     * Order information provided by the user
     */
    @SerialName("order_info")
    val orderInfo: OrderInfo? = null,
)
