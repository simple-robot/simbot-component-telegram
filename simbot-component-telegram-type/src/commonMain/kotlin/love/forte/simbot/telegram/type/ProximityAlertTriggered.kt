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

import kotlinx.serialization.Serializable

/**
 * [ProximityAlertTriggered](https://core.telegram.org/bots/api#proximityalerttriggered)
 *
 * This object represents the content of a service message, sent whenever a user in the chat
 * triggers a proximity alert set by another user.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class ProximityAlertTriggered(
    /**
     * User that triggered the alert
     *
     * type: `User`
     */
    public val traveler: love.forte.simbot.telegram.type.User,
    /**
     * User that set the alert
     *
     * type: `User`
     */
    public val watcher: love.forte.simbot.telegram.type.User,
    /**
     * The distance between the users
     *
     * type: `Integer`
     */
    public val distance: Int,
)
