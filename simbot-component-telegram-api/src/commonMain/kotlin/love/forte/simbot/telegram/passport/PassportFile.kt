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

package love.forte.simbot.telegram.passport

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * [PassportFile](https://core.telegram.org/bots/api#passportfile)
 *
 * This object represents a file uploaded to Telegram Passport.
 * Currently all Telegram Passport files are in JPEG format when decrypted and don't exceed 10MB.
 *
 * @author ForteScarlet
 */
@Serializable
public data class PassportFile(
    /**
     * Identifier for this file, which can be used to download or reuse the file
     */
    @SerialName("file_id")
    val fileId: String,
    /**
     * Unique identifier for this file, which is supposed to be the same over time and for different bots.
     * Can't be used to download or reuse the file.
     */
    @SerialName("file_unique_id")
    val fileUniqueId: String,
    /**
     * File size in bytes
     */
    @SerialName("file_size")
    val fileSize: Int,
    /**
     * Unix time when the file was uploaded
     */
    @SerialName("file_date")
    val fileDate: Int,
)
