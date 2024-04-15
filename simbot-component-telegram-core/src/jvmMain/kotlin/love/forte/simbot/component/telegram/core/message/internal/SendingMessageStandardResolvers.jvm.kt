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

package love.forte.simbot.component.telegram.core.message.internal

import io.ktor.client.request.forms.*
import io.ktor.utils.io.streams.*
import love.forte.simbot.common.ktor.inputfile.InputFile
import love.forte.simbot.message.*
import love.forte.simbot.resource.FileResource
import love.forte.simbot.resource.PathResource
import love.forte.simbot.resource.Resource
import love.forte.simbot.resource.URIResource
import kotlin.io.path.toPath

internal actual fun OfflineImage.toInputFile(): InputFile? {
    return when (val image = this) {
        is OfflinePathImage -> InputFile(image.path)
        is OfflineFileImage -> InputFile(image.file)
        is OfflineURIImage -> {
            val uri = image.uri
            if (uri.scheme == "file") {
                InputFile(uri.toPath())
            } else {
                InputFile(
                    InputProvider {
                        uri.toURL().openStream().asInput()
                    }
                )
            }
        }

        is OfflineResourceImage -> when (val resource = image.resource) {
            is PathResource -> InputFile(resource.path)
            is FileResource -> InputFile(resource.file)
            is URIResource -> {
                val uri = resource.uri
                if (uri.scheme == "file") {
                    InputFile(uri.toPath())
                } else {
                    InputFile(
                        InputProvider {
                            uri.toURL().openStream().asInput()
                        }
                    )
                }
            }

            else -> null
        }

        else -> null
    }
}

internal actual fun Resource.toInputFile(): InputFile? {
    return when (val resource = this) {
        is PathResource -> InputFile(resource.path)
        is FileResource -> InputFile(resource.file)
        is URIResource -> {
            val uri = resource.uri
            if (uri.scheme == "file") {
                InputFile(uri.toPath())
            } else {
                InputFile(
                    InputProvider {
                        uri.toURL().openStream().asInput()
                    }
                )
            }
        }

        else -> null
    }
}
