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
 * [Venue](https://core.telegram.org/bots/api#venue)
 *
 * This object represents a venue.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class Venue(
    /**
     * Venue location.
     * Can't be a live location
     *
     * type: `Location`
     */
    public val location: Location,
    /**
     * Name of the venue
     *
     * type: `String`
     */
    public val title: String,
    /**
     * Address of the venue
     *
     * type: `String`
     */
    public val address: String,
    /**
     * Optional.
     * Foursquare identifier of the venue
     *
     * type: `String`
     */
    @SerialName("foursquare_id")
    public val foursquareId: String? = null,
    /**
     * Optional.
     * Foursquare type of the venue.
     * (For example, “arts_entertainment/default”, “arts_entertainment/aquarium” or
     * “food/icecream”.)
     *
     * type: `String`
     */
    @SerialName("foursquare_type")
    public val foursquareType: String? = null,
    /**
     * Optional.
     * Google Places identifier of the venue
     *
     * type: `String`
     */
    @SerialName("google_place_id")
    public val googlePlaceId: String? = null,
    /**
     * Optional.
     * Google Places type of the venue.
     * (See supported types.)
     *
     * type: `String`
     */
    @SerialName("google_place_type")
    public val googlePlaceType: String? = null,
)
