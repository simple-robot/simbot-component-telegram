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

package love.forte.simbot.telegram.type.update

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * [WebhookInfo](https://core.telegram.org/bots/api#webhookinfo)
 *
 * Describes the current status of a webhook.
 *
 * @author ForteScarlet
 *
 */
@Serializable
public data class WebhookInfo(
    /**
     * Webhook URL, may be empty if webhook is not set up
     */
    val url: String,
    /**
     * True, if a custom certificate was provided for webhook certificate checks
     */
    @SerialName("has_custom_certificate")
    val hasCustomCertificate: Boolean = false,
    /**
     * Number of updates awaiting delivery
     */
    @SerialName("pending_update_count")
    val pendingUpdateCount: Int,
    /**
     * Optional.
     * Currently used webhook IP address
     */
    @SerialName("ip_address")
    val ipAddress: String? = null,
    /**
     * Optional.
     * Unix time for the most recent error that happened when trying to deliver an update via webhook
     */
    @SerialName("last_error_date")
    val lastErrorDate: Int? = null,
    /**
     * Optional.
     * Error message in human-readable format for the most recent error that happened when trying to deliver an update via webhook
     */
    @SerialName("last_error_message")
    val lastErrorMessage: String? = null,
    /**
     * Optional.
     * Unix time of the most recent error that happened when trying to synchronize available updates with Telegram datacenters
     */
    @SerialName("last_synchronization_error_date")
    val lastSynchronizationErrorDate: Int? = null,
    /**
     * Optional.
     * The maximum allowed number of simultaneous HTTPS connections to the webhook for update delivery
     */
    @SerialName("max_connections")
    val maxConnections: Int? = null,
    /**
     * Optional.
     * A list of update types the bot is subscribed to. Defaults to all update types except chat_member
     */
    @SerialName("allowed_updates")
    val allowedUpdates: List<String>? = null,
)
