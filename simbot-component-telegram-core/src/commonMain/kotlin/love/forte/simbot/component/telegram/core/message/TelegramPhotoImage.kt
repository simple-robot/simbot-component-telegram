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

package love.forte.simbot.component.telegram.core.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import love.forte.simbot.common.id.ID
import love.forte.simbot.common.id.StringID.Companion.ID
import love.forte.simbot.component.telegram.core.message.TelegramPhotoImage.Companion.create
import love.forte.simbot.message.Image
import love.forte.simbot.message.OfflineImage
import love.forte.simbot.message.RemoteImage
import love.forte.simbot.telegram.type.PhotoSize
import kotlin.jvm.JvmStatic


/**
 * An implementation for [Image] based on Telegram Photo.
 *
 * ## 发送
 * 当需要发送图片时，你可以使用 [OfflineImage] 来发送一个本地文件，
 * 或者在 `fileId` 已知的情况下构建 [TelegramPhotoImage] 来直接通过ID发送图片。
 *
 * @see TelegramPhotoSizesImage
 * @see create
 *
 * @author ForteScarlet
 */
public interface TelegramPhotoImage : TelegramMessageElement, Image, RemoteImage {
    // 有 ID 的情况下，本质上它们都是一个 “远程图片”

    /**
     * The file id of this photo.
     * See `PhotoSize.file_id` in [the documentation](https://core.telegram.org/bots/api#photosize)
     *
     */
    override val id: ID

    public companion object {
        /**
         * Create [TelegramPhotoImage] with only [fileId].
         * Can be used to send images if [fileId] is known.
         */
        @JvmStatic
        public fun create(fileId: ID): TelegramPhotoImage =
            TelegramFileIdPhotoImage(fileId)
    }
}

/**
 * An [TelegramPhotoImage] implementation containing a list of [PhotoSize]
 * is a type parsed by message events.
 *
 * The [id] value defaults to the [PhotoSize.fileId]
 * of the first element in [sources].
 */
@Serializable
@SerialName("telegram.m.photo_image.photo_sizes")
public class TelegramPhotoSizesImage internal constructor(
    public val sources: List<PhotoSize>,
    override val id: ID = sources.first().fileId.ID
) : TelegramPhotoImage {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TelegramPhotoSizesImage) return false

        if (sources != other.sources) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = sources.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }

    override fun toString(): String {
        return "TelegramPhotoSizesImage(sizes=$sources, fileId=$id)"
    }
}


@Serializable
@SerialName("telegram.m.photo_image.file_id")
internal data class TelegramFileIdPhotoImage(override val id: ID) : TelegramPhotoImage
