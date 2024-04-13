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
import love.forte.simbot.telegram.type.Document
import kotlin.jvm.JvmStatic


/**
 * A Telegram Document file that received from a message event.
 *
 * @author ForteScarlet
 */
public interface TelegramDocument : TelegramMessageElement {
    /**
     * The source [Document].
     */
    public val source: Document

    /**
     * The file id of Audio.
     */
    public val id: ID
        get() = source.fileId.ID

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
 * A Telegram Document for **sending**
 * and serialization is not supported.
 *
 * Can use [Resource], [InputFile] or a file id.
 */
@SendOnly
public sealed class SendOnlyTelegramDocument : TelegramMessageElement {
    @Serializable
    @SerialName("telegram.m.document.send.file_id")
    internal data class FileIdDocument(val id: ID) : SendOnlyTelegramDocument()
    internal data class InputFileDocument(val inputFile: InputFile) : SendOnlyTelegramDocument()
    internal data class ResourceDocument(val resource: Resource) : SendOnlyTelegramDocument()

    public companion object {
        /**
         * Create [SendOnlyTelegramDocument] by file id.
         */
        @JvmStatic
        public fun create(fileId: ID): SendOnlyTelegramDocument =
            FileIdDocument(fileId)

        /**
         * Create [SendOnlyTelegramDocument] by [InputFile]
         */
        @JvmStatic
        public fun create(inputFile: InputFile): SendOnlyTelegramDocument =
            InputFileDocument(inputFile)

        /**
         * Create [SendOnlyTelegramDocument] by [Resource]
         */
        @JvmStatic
        public fun create(resource: Resource): SendOnlyTelegramDocument =
            ResourceDocument(resource)
    }
}

@Serializable
@SerialName("telegram.m.document")
internal data class TelegramDocumentImpl(override val source: Document) : TelegramDocument {
    override fun toString(): String =
        "TelegramDocument(id=${source.fileId}, source=$source)"
}
