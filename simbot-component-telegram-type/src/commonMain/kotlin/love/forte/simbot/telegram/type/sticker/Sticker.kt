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

package love.forte.simbot.telegram.type.sticker

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * [Stickers](https://core.telegram.org/bots/api#stickers)
 *
 * This object represents a sticker.
 *
 * @author ForteScarlet
 */
@Serializable
public data class Sticker(
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
     * Type of the sticker, currently one of “regular”, “mask”, “custom_emoji”.
     * The type of the sticker is independent from its format, which is determined by the fields is_animated and is_video.
     */
    val type: String,
    /**
     * Sticker width
     */
    val width: Int,
    /**
     * Sticker height
     */
    val height: Int,
    /**
     * True, if the sticker is animated
     */
    @SerialName("is_animated")
    val isAnimated: Boolean = false,
    /**
     * True, if the sticker is a video sticker
     */
    @SerialName("is_video")
    val isVideo: Boolean = false,
    /**
     * Optional.
     * Sticker thumbnail in the .WEBP or .JPG format
     */
    val thumbnail: love.forte.simbot.telegram.type.PhotoSize? = null,
    /**
     * Optional.
     * Emoji associated with the sticker
     */
    val emoji: String? = null,
    /**
     * Optional.
     * Name of the sticker set to which the sticker belongs
     */
    @SerialName("set_name")
    val setName: String? = null,
    /**
     * Optional.
     * For premium regular stickers, premium animation for the sticker
     */
    @SerialName("premium_animation")
    val premiumAnimation: love.forte.simbot.telegram.type.File? = null,
    /**
     * Optional.
     * For mask stickers, the position where the mask should be placed
     */
    @SerialName("mask_position")
    val maskPosition: love.forte.simbot.telegram.type.sticker.MaskPosition? = null,
    /**
     * Optional.
     * For custom emoji stickers, unique identifier of the custom emoji
     */
    @SerialName("custom_emoji_id")
    val customEmojiId: String? = null,
    /**
     * Optional.
     * True, if the sticker must be repainted to a text color in messages,
     * the color of the Telegram Premium badge in emoji status, white color on chat photos, or another appropriate color in other places
     */
    @SerialName("needs_repainting")
    val needsRepainting: Boolean? = null,
    /**
     * Optional.
     * File size in bytes
     */
    @SerialName("file_size")
    val fileSize: Int? = null,
)
