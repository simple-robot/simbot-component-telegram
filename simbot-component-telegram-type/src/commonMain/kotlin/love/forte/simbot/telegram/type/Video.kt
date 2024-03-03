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
 * [Video](https://core.telegram.org/bots/api#video)
 *
 * This object represents a video file.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class Video(
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
     * Video width as defined by sender
     *
     * type: `Integer`
     */
    public val width: Int,
    /**
     * Video height as defined by sender
     *
     * type: `Integer`
     */
    public val height: Int,
    /**
     * Duration of the video in seconds as defined by sender
     *
     * type: `Integer`
     */
    public val duration: Int,
    /**
     * Optional. 
     * Video thumbnail
     *
     * type: `PhotoSize`
     */
    public val thumbnail: love.forte.simbot.telegram.type.PhotoSize? = null,
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

/**
 * [VideoChatEnded](https://core.telegram.org/bots/api#videochatended)
 *
 * This object represents a service message about a video chat ended in the chat.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class VideoChatEnded(
    /**
     * Video chat duration in seconds
     *
     * type: `Integer`
     */
    public val duration: Int,
)

/**
 * [VideoChatParticipantsInvited](https://core.telegram.org/bots/api#videochatparticipantsinvited)
 *
 * This object represents a service message about new members invited to a video chat.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class VideoChatParticipantsInvited(
    /**
     * New members that were invited to the video chat
     *
     * type: `Array of User`
     */
    public val users: List<love.forte.simbot.telegram.type.User> = emptyList(),
)

/**
 * [VideoChatScheduled](https://core.telegram.org/bots/api#videochatscheduled)
 *
 * This object represents a service message about a video chat scheduled in the chat.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class VideoChatScheduled(
    /**
     * Point in time (Unix timestamp) when the video chat is supposed to be started by a chat
     * administrator
     *
     * type: `Integer`
     */
    @SerialName("start_date")
    public val startDate: Int,
)

/**
 * [VideoChatStarted](https://core.telegram.org/bots/api#videochatstarted)
 *
 * This object represents a service message about a video chat started in the chat. Currently holds
 * no information.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public class VideoChatStarted {
    // TODO Empty class?

}

/**
 * [VideoNote](https://core.telegram.org/bots/api#videonote)
 *
 * This object represents a video message (available in Telegram apps as of v.4.0).
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class VideoNote(
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
     * Video width and height (diameter of the video message) as defined by sender
     *
     * type: `Integer`
     */
    public val length: Int,
    /**
     * Duration of the video in seconds as defined by sender
     *
     * type: `Integer`
     */
    public val duration: Int,
    /**
     * Optional. 
     * Video thumbnail
     *
     * type: `PhotoSize`
     */
    public val thumbnail: love.forte.simbot.telegram.type.PhotoSize? = null,
    /**
     * Optional. 
     * File size in bytes
     *
     * type: `Integer`
     */
    @SerialName("file_size")
    public val fileSize: Int? = null,
)
