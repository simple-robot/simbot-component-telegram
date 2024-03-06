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
 * [Location](https://core.telegram.org/bots/api#location)
 *
 * This object represents a point on the map.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class Location(
    /**
     * Latitude as defined by sender
     *
     * type: `Float`
     */
    public val latitude: Float,
    /**
     * Longitude as defined by sender
     *
     * type: `Float`
     */
    public val longitude: Float,
    /**
     * Optional.
     * The radius of uncertainty for the location, measured in meters; 0-1500
     *
     * type: `Float`
     */
    @SerialName("horizontal_accuracy")
    public val horizontalAccuracy: Float? = null,
    /**
     * Optional.
     * Time relative to the message sending date, during which the location can be updated; in
     * seconds.
     * For active live locations only.
     *
     * type: `Integer`
     */
    @SerialName("live_period")
    public val livePeriod: Int? = null,
    /**
     * Optional.
     * The direction in which user is moving, in degrees; 1-360.
     * For active live locations only.
     *
     * type: `Integer`
     */
    public val heading: Int? = null,
    /**
     * Optional.
     * The maximum distance for proximity alerts about approaching another chat member, in meters.
     * For sent live locations only.
     *
     * type: `Integer`
     */
    @SerialName("proximity_alert_radius")
    public val proximityAlertRadius: Int? = null,
)
