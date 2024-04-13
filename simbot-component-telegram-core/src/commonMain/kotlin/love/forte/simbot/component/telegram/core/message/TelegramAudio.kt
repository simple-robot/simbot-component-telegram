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
import love.forte.simbot.common.ktor.inputfile.InputFile
import love.forte.simbot.resource.Resource
import love.forte.simbot.telegram.type.Audio
import kotlin.jvm.JvmStatic
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds


/**
 * A Telegram Audio that received from a message event.
 *
 * @author ForteScarlet
 */
public interface TelegramAudio : TelegramMessageElement {
    /**
     * The source [Audio].
     */
    public val source: Audio

    /**
     * The file id of Audio.
     */
    public val id: ID
        get() = source.fileId.ID

    /**
     * @see Audio.duration
     */
    public val duration: Int
        get() = source.duration

    /**
     * The [Duration] value of [duration] in seconds.
     */
    public val durationValue: Duration
        get() = duration.seconds

    /**
     * @see Audio.performer
     */
    public val performer: String?
        get() = source.performer

    /**
     * @see Audio.title
     */
    public val title: String?
        get() = source.title

    /**
     * @see Audio.fileName
     */
    public val fileName: String?
        get() = source.fileName

    /**
     * @see Audio.mimeType
     */
    public val mimeType: String?
        get() = source.mimeType

    /**
     * @see Audio.fileSize
     */
    public val fileSize: Long?
        get() = source.fileSize
}

/**
 * A Telegram Audio for **sending**
 * and serialization is not supported.
 *
 * Can use [Resource], [InputFile] or a file id.
 */
@SendOnly
public sealed class SendOnlyTelegramAudio : TelegramMessageElement {
    @Serializable
    @SerialName("telegram.m.audio.send.file_id")
    internal data class FileIdAudio(val id: ID) : SendOnlyTelegramAudio()
    internal data class InputFileAudio(val inputFile: InputFile) : SendOnlyTelegramAudio()
    internal data class ResourceAudio(val resource: Resource) : SendOnlyTelegramAudio()

    public companion object {
        /**
         * Create [SendOnlyTelegramAudio] by file id.
         */
        @JvmStatic
        public fun create(fileId: ID): SendOnlyTelegramAudio =
            FileIdAudio(fileId)

        /**
         * Create [SendOnlyTelegramAudio] by [InputFile]
         */
        @JvmStatic
        public fun create(inputFile: InputFile): SendOnlyTelegramAudio =
            InputFileAudio(inputFile)

        /**
         * Create [SendOnlyTelegramAudio] by [Resource]
         */
        @JvmStatic
        public fun create(resource: Resource): SendOnlyTelegramAudio =
            ResourceAudio(resource)
    }
}

@Serializable
@SerialName("telegram.m.audio")
internal data class TelegramAudioImpl(override val source: Audio) : TelegramAudio {
    override fun toString(): String =
        "TelegramAudio(id=${source.fileId}, source=$source)"
}
