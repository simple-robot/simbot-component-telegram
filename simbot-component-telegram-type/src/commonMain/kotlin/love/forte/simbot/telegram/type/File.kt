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
 * [File](https://core.telegram.org/bots/api#file)
 *
 * This object represents a file ready to be downloaded. The file can be downloaded via the link
 * https://api.telegram.org/file/bot<token>/<file_path>. It is guaranteed that the link will be valid
 * for at least 1 hour. When the link expires, a new one can be requested by calling getFile.
 * The maximum file size to download is 20 MB
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class File(
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
    /**
     * Optional.
     * File path.
     * Use https://api.telegram.org/file/bot<token>/<file_path> to get the file.
     *
     * type: `String`
     */
    @SerialName("file_path")
    public val filePath: String? = null,
)
