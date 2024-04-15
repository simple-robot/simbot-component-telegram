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

import love.forte.simbot.common.id.literal
import love.forte.simbot.common.ktor.inputfile.InputFile
import love.forte.simbot.component.telegram.core.message.*
import love.forte.simbot.message.*
import love.forte.simbot.resource.ByteArrayResource
import love.forte.simbot.resource.Resource
import love.forte.simbot.resource.StringResource
import love.forte.simbot.telegram.api.message.*
import love.forte.simbot.telegram.api.utils.StringValueInputFile
import love.forte.simbot.telegram.type.ChatId
import love.forte.simbot.telegram.type.MessageEntity

internal object SendingMarksResolver : SendingMessageResolver {
    override suspend fun resolve(
        chatId: ChatId,
        index: Int,
        element: Message.Element,
        source: Message,
        context: SendingMessageResolverContext
    ) {
        when (element) {
            is TelegramProtectContent -> {
                context.markProtectContent()
            }
            is TelegramDisableNotification -> {
                context.markDisableNotification()
            }
        }
    }
}

internal object TextSendingResolver : SendingMessageResolver {
    override suspend fun resolve(
        chatId: ChatId,
        index: Int,
        element: Message.Element,
        source: Message,
        context: SendingMessageResolverContext
    ) {
        with(context.builder) {
            when (element) {
                is TelegramTextParseMode -> {
                    parseMode = element.parseMode
                }

                is PlainText -> {
                    val textValue = element.text
                    if (element is TelegramMessageEntity) {
                        val entityType = element.type
                        when (element) {
                            is TelegramMessageEntity.Simple -> {
                                addEntity(
                                    MessageEntity(
                                        type = entityType,
                                        offset = textOffset,
                                        length = textValue.length
                                    )
                                )
                            }

                            is TelegramMessageEntity.TextLink -> {
                                addEntity(
                                    MessageEntity(
                                        type = entityType,
                                        offset = textOffset,
                                        length = textValue.length,
                                        url = element.url
                                    )
                                )
                            }

                            is TelegramMessageEntity.TextMention -> {
                                addEntity(
                                    MessageEntity(
                                        type = entityType,
                                        offset = textOffset,
                                        length = textValue.length,
                                        user = element.user
                                    )
                                )
                            }

                            is TelegramMessageEntity.Pre -> {
                                addEntity(
                                    MessageEntity(
                                        type = entityType,
                                        offset = textOffset,
                                        length = textValue.length,
                                        language = element.language
                                    )
                                )
                            }

                            is TelegramMessageEntity.CustomEmoji -> {
                                addEntity(
                                    MessageEntity(
                                        type = entityType,
                                        offset = textOffset,
                                        length = textValue.length,
                                        customEmojiId = element.customEmojiId?.literal
                                    )
                                )
                            }
                        }
                    }
                    appendText(textValue)
                }

            }
        }
    }
}

/**
 * 解析发送图片的内容，将它们解析为 [SendPhotoApi].
 */
internal object ImageSendingResolver : SendingMessageResolver {
    override suspend fun resolve(
        chatId: ChatId,
        index: Int,
        element: Message.Element,
        source: Message,
        context: SendingMessageResolverContext
    ) {
        fun SendPhotoApi.Body.mark(marks: SendingMarks) {
            if (marks.isProtectContent) {
                protectContent = true
            }
            if (marks.isDisableNotification) {
                disableNotification = true
            }
        }

        if (element is Image) {
            when (element) {
                // 包括 TelegramPhotoImage
                is RemoteImage -> {
                    context.addToStackMsg { m ->
                        buildSendPhotoApi {
                            this.chatId = chatId
                            this.photo = StringValueInputFile.create(element.id.literal)
                            mark(m)
                        }
                    }
                }

                is OfflineImage -> {
                    context.addToStackMsg { m ->
                        buildSendPhotoApi {
                            this.chatId = chatId
                            this.photo = element.toInputFileCommon()
                            mark(m)

                        }
                    }
                }

                is IDAwareImage -> {
                    // with a log?
                    context.addToStackMsg { m ->
                        buildSendPhotoApi {
                            this.chatId = chatId
                            this.photo = StringValueInputFile.create(element.id.literal)
                            mark(m)

                        }
                    }
                }
                // other: ignore.
            }
        }
    }
}

internal object TelegramLinkPreviewOptionsResolver : SendingMessageResolver {
    override suspend fun resolve(
        chatId: ChatId,
        index: Int,
        element: Message.Element,
        source: Message,
        context: SendingMessageResolverContext
    ) {
        if (element is TelegramLinkPreviewOptions) {
            context.linkPreviewOptions(element.source)
        }
    }
}

internal object TelegramAudioSendingResolver : SendingMessageResolver {
    override suspend fun resolve(
        chatId: ChatId,
        index: Int,
        element: Message.Element,
        source: Message,
        context: SendingMessageResolverContext
    ) {
        fun SendAudioApi.Body.mark(marks: SendingMarks) {
            if (marks.isProtectContent) {
                protectContent = true
            }
            if (marks.isDisableNotification) {
                disableNotification = true
            }
        }

        when (element) {
            is TelegramAudio -> {
                context.addToStackMsg { m ->
                    buildSendAudioApi {
                        this.chatId = chatId
                        this.audio = StringValueInputFile.create(element.source.fileId)
                        mark(m)
                    }
                }
            }

            is SendOnlyTelegramAudio -> when (element) {
                is SendOnlyTelegramAudio.FileIdAudio -> {
                    context.addToStackMsg { m ->
                        buildSendAudioApi {
                            this.chatId = chatId
                            this.audio = StringValueInputFile.create(element.id.literal)
                            mark(m)
                        }
                    }
                }

                is SendOnlyTelegramAudio.InputFileAudio -> {
                    context.addToStackMsg { m ->
                        buildSendAudioApi {
                            this.chatId = chatId
                            this.audio = element.inputFile
                            mark(m)
                        }
                    }
                }

                is SendOnlyTelegramAudio.ResourceAudio -> {
                    context.addToStackMsg { m ->
                        buildSendAudioApi {
                            this.chatId = chatId
                            this.audio = element.resource.toInputFileCommon()
                            mark(m)
                        }
                    }
                }

            }
        }
    }
}

internal object TelegramDocumentSendingResolver : SendingMessageResolver {
    override suspend fun resolve(
        chatId: ChatId,
        index: Int,
        element: Message.Element,
        source: Message,
        context: SendingMessageResolverContext
    ) {
        fun SendDocumentApi.Body.mark(marks: SendingMarks) {
            if (marks.isProtectContent) {
                protectContent = true
            }
            if (marks.isDisableNotification) {
                disableNotification = true
            }
        }

        when (element) {
            is TelegramDocument -> {
                context.addToStackMsg { m ->
                    buildSendDocumentApi {
                        this.chatId = chatId
                        this.document = StringValueInputFile.create(element.source.fileId)
                        mark(m)
                    }
                }
            }

            is SendOnlyTelegramDocument -> when (element) {
                is SendOnlyTelegramDocument.FileIdDocument -> {
                    context.addToStackMsg { m ->
                        buildSendDocumentApi {
                            this.chatId = chatId
                            this.document = StringValueInputFile.create(element.id.literal)
                            mark(m)
                        }
                    }
                }

                is SendOnlyTelegramDocument.InputFileDocument -> {
                    context.addToStackMsg { m ->
                        buildSendDocumentApi {
                            this.chatId = chatId
                            this.document = element.inputFile
                            mark(m)
                        }
                    }
                }

                is SendOnlyTelegramDocument.ResourceDocument -> {
                    context.addToStackMsg { m ->
                        buildSendDocumentApi {
                            this.chatId = chatId
                            this.document = element.resource.toInputFileCommon()
                            mark(m)
                        }
                    }
                }
            }
        }
    }
}


internal expect fun OfflineImage.toInputFile(): InputFile?

internal fun OfflineImage.toInputFileCommon(): InputFile {
    return when (val img = this) {
        is OfflineByteArrayImage -> InputFile(img.data())
        is OfflineResourceImage -> when (val resource = img.resource) {
            is ByteArrayResource, is StringResource -> InputFile(resource.data())
            else -> toInputFile() ?: InputFile(img.data())
        }

        else -> toInputFile() ?: InputFile(img.data())
    }
}

internal expect fun Resource.toInputFile(): InputFile?

internal fun Resource.toInputFileCommon(): InputFile {
    return when (val res = this) {
        is ByteArrayResource, is StringResource -> InputFile(res.data())
        else -> toInputFile() ?: InputFile(res.data())
    }
}
