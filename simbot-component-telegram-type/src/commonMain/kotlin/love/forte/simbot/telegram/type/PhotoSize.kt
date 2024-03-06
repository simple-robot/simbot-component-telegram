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
 * [PhotoSize](https://core.telegram.org/bots/api#photosize)
 *
 * This object represents one size of a photo or a file / sticker thumbnail.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class PhotoSize(
    /**
     * Identifier for this file, which can be used to download or reuse the file
     *
     * type: `String`
     */
    @SerialName("file_id")
    public val fileId: String,
    /**
     * Unique identifier for this file, which is supposed to be the same over time and for different
     * bots.
     * Can't be used to download or reuse the file.
     *
     * type: `String`
     */
    @SerialName("file_unique_id")
    public val fileUniqueId: String,
    /**
     * Photo width
     *
     * type: `Integer`
     */
    public val width: Int,
    /**
     * Photo height
     *
     * type: `Integer`
     */
    public val height: Int,
    /**
     * Optional.
     * File size in bytes
     *
     * type: `Integer`
     */
    @SerialName("file_size")
    public val fileSize: Int? = null,
)
