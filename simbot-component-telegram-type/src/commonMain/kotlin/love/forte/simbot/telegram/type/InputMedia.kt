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
 * [InputMedia](https://core.telegram.org/bots/api#inputmedia)
 *
 * This object represents the content of a media message to be sent.
 *
 *
 * @see InputMediaAnimation
 * @see InputMediaDocument
 * @see InputMediaAudio
 * @see InputMediaPhoto
 * @see InputMediaVideo
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public sealed class InputMedia {
    public companion object {
        public const val ANIMATION_TYPE_NAME: String = "animation"
        public const val DOCUMENT_TYPE_NAME: String = "document"
        public const val AUDIO_TYPE_NAME: String = "audio"
        public const val PHOTO_TYPE_NAME: String = "photo"
        public const val VIDEO_TYPE_NAME: String = "video"

    }
}

/**
 * [InputMediaAnimation](https://core.telegram.org/bots/api#inputmediaanimation)
 *
 * Represents an animation file (GIF or H.264/MPEG-4 AVC video without sound) to be sent.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
@SerialName(InputMedia.ANIMATION_TYPE_NAME)
public data class InputMediaAnimation(
    /**
     * File to send.
     * Pass a file_id to send a file that exists on the Telegram servers (recommended), pass an HTTP
     * URL for Telegram to get a file from the Internet, or pass “attach://<file_attach_name>” to
     * upload a new one using multipart/form-data under <file_attach_name> name.
     * More information on Sending Files »
     *
     * type: `String`
     */
    public val media: String,
    /**
     * Optional.
     * Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported
     * server-side.
     * The thumbnail should be in JPEG format and less than 200 kB in size.
     * A thumbnail's width and height should not exceed 320.
     * Ignored if the file is not uploaded using multipart/form-data.
     * Thumbnails can't be reused and can be only uploaded as a new file, so you can pass
     * “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under
     * <file_attach_name>.
     * More information on Sending Files »
     *
     * type: `InputFile or String`
     */
    public val thumbnail: String? = null, // TODO InputFile or String

    /**
     * Optional.
     * Caption of the animation to be sent, 0-1024 characters after entities parsing
     *
     * type: `String`
     */
    public val caption: String? = null,
    /**
     * Optional.
     * Mode for parsing entities in the animation caption.
     * See formatting options for more details.
     *
     * type: `String`
     */
    @SerialName("parse_mode")
    public val parseMode: String? = null,
    /**
     * Optional.
     * List of special entities that appear in the caption, which can be specified instead of
     * parse_mode
     *
     * type: `Array of MessageEntity`
     */
    @SerialName("caption_entities")
    public val captionEntities: List<MessageEntity>? = null,
    /**
     * Optional.
     * Animation width
     *
     * type: `Integer`
     */
    public val width: Int? = null,
    /**
     * Optional.
     * Animation height
     *
     * type: `Integer`
     */
    public val height: Int? = null,
    /**
     * Optional.
     * Animation duration in seconds
     *
     * type: `Integer`
     */
    public val duration: Int? = null,
    /**
     * Optional.
     * Pass True if the animation needs to be covered with a spoiler animation
     *
     * type: `Boolean`
     */
    @SerialName("has_spoiler")
    public val hasSpoiler: Boolean? = null,
) : InputMedia()

/**
 * [InputMediaAudio](https://core.telegram.org/bots/api#inputmediaaudio)
 *
 * Represents an audio file to be treated as music to be sent.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
@SerialName(InputMedia.AUDIO_TYPE_NAME)
public data class InputMediaAudio(
    /**
     * File to send.
     * Pass a file_id to send a file that exists on the Telegram servers (recommended), pass an HTTP
     * URL for Telegram to get a file from the Internet, or pass “attach://<file_attach_name>” to
     * upload a new one using multipart/form-data under <file_attach_name> name.
     * More information on Sending Files »
     *
     * type: `String`
     */
    public val media: String,
    /**
     * Optional.
     * Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported
     * server-side.
     * The thumbnail should be in JPEG format and less than 200 kB in size.
     * A thumbnail's width and height should not exceed 320.
     * Ignored if the file is not uploaded using multipart/form-data.
     * Thumbnails can't be reused and can be only uploaded as a new file, so you can pass
     * “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under
     * <file_attach_name>.
     * More information on Sending Files »
     *
     * type: `InputFile or String`
     */
    public val thumbnail: String? = null,
    /**
     * Optional.
     * Caption of the audio to be sent, 0-1024 characters after entities parsing
     *
     * type: `String`
     */
    public val caption: String? = null,
    /**
     * Optional.
     * Mode for parsing entities in the audio caption.
     * See formatting options for more details.
     *
     * type: `String`
     */
    @SerialName("parse_mode")
    public val parseMode: String? = null,
    /**
     * Optional.
     * List of special entities that appear in the caption, which can be specified instead of
     * parse_mode
     *
     * type: `Array of MessageEntity`
     */
    @SerialName("caption_entities")
    public val captionEntities: List<MessageEntity>? = null,
    /**
     * Optional.
     * Duration of the audio in seconds
     *
     * type: `Integer`
     */
    public val duration: Int? = null,
    /**
     * Optional.
     * Performer of the audio
     *
     * type: `String`
     */
    public val performer: String? = null,
    /**
     * Optional.
     * Title of the audio
     *
     * type: `String`
     */
    public val title: String? = null,
) : InputMedia()

/**
 * [InputMediaDocument](https://core.telegram.org/bots/api#inputmediadocument)
 *
 * Represents a general file to be sent.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
@SerialName(InputMedia.DOCUMENT_TYPE_NAME)
public data class InputMediaDocument(
    /**
     * File to send.
     * Pass a file_id to send a file that exists on the Telegram servers (recommended), pass an HTTP
     * URL for Telegram to get a file from the Internet, or pass “attach://<file_attach_name>” to
     * upload a new one using multipart/form-data under <file_attach_name> name.
     * More information on Sending Files »
     *
     * type: `String`
     */
    public val media: String,
    /**
     * Optional.
     * Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported
     * server-side.
     * The thumbnail should be in JPEG format and less than 200 kB in size.
     * A thumbnail's width and height should not exceed 320.
     * Ignored if the file is not uploaded using multipart/form-data.
     * Thumbnails can't be reused and can be only uploaded as a new file, so you can pass
     * “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under
     * <file_attach_name>.
     * More information on Sending Files »
     *
     * type: `InputFile or String`
     */
    public val thumbnail: String? = null,
    /**
     * Optional.
     * Caption of the document to be sent, 0-1024 characters after entities parsing
     *
     * type: `String`
     */
    public val caption: String? = null,
    /**
     * Optional.
     * Mode for parsing entities in the document caption.
     * See formatting options for more details.
     *
     * type: `String`
     */
    @SerialName("parse_mode")
    public val parseMode: String? = null,
    /**
     * Optional.
     * List of special entities that appear in the caption, which can be specified instead of
     * parse_mode
     *
     * type: `Array of MessageEntity`
     */
    @SerialName("caption_entities")
    public val captionEntities: List<MessageEntity>? = null,
    /**
     * Optional.
     * Disables automatic server-side content type detection for files uploaded using
     * multipart/form-data.
     * Always True, if the document is sent as part of an album.
     *
     * type: `Boolean`
     */
    @SerialName("disable_content_type_detection")
    public val disableContentTypeDetection: Boolean? = null,
) : InputMedia()

/**
 * [InputMediaPhoto](https://core.telegram.org/bots/api#inputmediaphoto)
 *
 * Represents a photo to be sent.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
@SerialName(InputMedia.PHOTO_TYPE_NAME)
public data class InputMediaPhoto(
    /**
     * File to send.
     * Pass a file_id to send a file that exists on the Telegram servers (recommended), pass an HTTP
     * URL for Telegram to get a file from the Internet, or pass “attach://<file_attach_name>” to
     * upload a new one using multipart/form-data under <file_attach_name> name.
     * More information on Sending Files »
     *
     * type: `String`
     */
    public val media: String,
    /**
     * Optional.
     * Caption of the photo to be sent, 0-1024 characters after entities parsing
     *
     * type: `String`
     */
    public val caption: String? = null,
    /**
     * Optional.
     * Mode for parsing entities in the photo caption.
     * See formatting options for more details.
     *
     * type: `String`
     */
    @SerialName("parse_mode")
    public val parseMode: String? = null,
    /**
     * Optional.
     * List of special entities that appear in the caption, which can be specified instead of
     * parse_mode
     *
     * type: `Array of MessageEntity`
     */
    @SerialName("caption_entities")
    public val captionEntities: List<MessageEntity>? = null,
    /**
     * Optional.
     * Pass True if the photo needs to be covered with a spoiler animation
     *
     * type: `Boolean`
     */
    @SerialName("has_spoiler")
    public val hasSpoiler: Boolean? = null,
) : InputMedia()

/**
 * [InputMediaVideo](https://core.telegram.org/bots/api#inputmediavideo)
 *
 * Represents a video to be sent.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
@SerialName(InputMedia.VIDEO_TYPE_NAME)
public data class InputMediaVideo(
    /**
     * Type of the result, must be video
     *
     * type: `String`
     */
    public val type: String,
    /**
     * File to send.
     * Pass a file_id to send a file that exists on the Telegram servers (recommended), pass an HTTP
     * URL for Telegram to get a file from the Internet, or pass “attach://<file_attach_name>” to
     * upload a new one using multipart/form-data under <file_attach_name> name.
     * More information on Sending Files »
     *
     * type: `String`
     */
    public val media: String,
    /**
     * Optional.
     * Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported
     * server-side.
     * The thumbnail should be in JPEG format and less than 200 kB in size.
     * A thumbnail's width and height should not exceed 320.
     * Ignored if the file is not uploaded using multipart/form-data.
     * Thumbnails can't be reused and can be only uploaded as a new file, so you can pass
     * “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under
     * <file_attach_name>.
     * More information on Sending Files »
     *
     * type: `InputFile or String`
     */
    public val thumbnail: String? = null,
    /**
     * Optional.
     * Caption of the video to be sent, 0-1024 characters after entities parsing
     *
     * type: `String`
     */
    public val caption: String? = null,
    /**
     * Optional.
     * Mode for parsing entities in the video caption.
     * See formatting options for more details.
     *
     * type: `String`
     */
    @SerialName("parse_mode")
    public val parseMode: String? = null,
    /**
     * Optional.
     * List of special entities that appear in the caption, which can be specified instead of
     * parse_mode
     *
     * type: `Array of MessageEntity`
     */
    @SerialName("caption_entities")
    public val captionEntities: List<MessageEntity>? = null,
    /**
     * Optional.
     * Video width
     *
     * type: `Integer`
     */
    public val width: Int? = null,
    /**
     * Optional.
     * Video height
     *
     * type: `Integer`
     */
    public val height: Int? = null,
    /**
     * Optional.
     * Video duration in seconds
     *
     * type: `Integer`
     */
    public val duration: Int? = null,
    /**
     * Optional.
     * Pass True if the uploaded video is suitable for streaming
     *
     * type: `Boolean`
     */
    @SerialName("supports_streaming")
    public val supportsStreaming: Boolean? = null,
    /**
     * Optional.
     * Pass True if the video needs to be covered with a spoiler animation
     *
     * type: `Boolean`
     */
    @SerialName("has_spoiler")
    public val hasSpoiler: Boolean? = null,
) : InputMedia()
