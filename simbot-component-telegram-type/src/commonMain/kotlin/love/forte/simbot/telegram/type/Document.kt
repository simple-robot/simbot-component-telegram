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
 * [Document](https://core.telegram.org/bots/api#document)
 *
 * This object represents a general file (as opposed to photos, voice messages and audio files).
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class Document(
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
     * Optional.
     * Document thumbnail as defined by sender
     *
     * type: `PhotoSize`
     */
    public val thumbnail: PhotoSize? = null,
    /**
     * Optional.
     * Original filename as defined by sender
     *
     * type: `String`
     */
    @SerialName("file_name")
    public val fileName: String? = null,
    /**
     * Optional.
     * MIME type of the file as defined by sender
     *
     * type: `String`
     */
    @SerialName("mime_type")
    public val mimeType: String? = null,
    /**
     * Optional.
     * File size in bytes.
     * It can be bigger than 2^31 and some programming languages may have difficulty/silent defects
     * in interpreting it.
     * But it has at most 52 significant bits, so a signed 64-bit integer or double-precision float
     * type are safe for storing this value.
     *
     * type: `Integer`
     */
    @SerialName("file_size")
    public val fileSize: Long? = null,
)
