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
import love.forte.simbot.component.telegram.core.message.SendingMessageResolver
import love.forte.simbot.component.telegram.core.message.SendingMessageResolverContext
import love.forte.simbot.component.telegram.core.message.TelegramMessageEntity
import love.forte.simbot.component.telegram.core.message.TelegramTextParseMode
import love.forte.simbot.message.*
import love.forte.simbot.resource.ByteArrayResource
import love.forte.simbot.resource.StringResource
import love.forte.simbot.telegram.api.message.SendPhotoApi
import love.forte.simbot.telegram.api.message.buildSendPhotoApi
import love.forte.simbot.telegram.api.utils.StringValueInputFile
import love.forte.simbot.telegram.type.ChatId
import love.forte.simbot.telegram.type.MessageEntity

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
        if (element is Image) {
            when (element) {
                // 包括 TelegramPhotoImage
                is RemoteImage -> {
                    context.addToStackMsg {
                        buildSendPhotoApi {
                            this.chatId = chatId
                            this.photo = StringValueInputFile.create(element.id.literal)
                            // TODO 可共享的状态元素?
                        }
                    }
                }

                is OfflineImage -> {
                    context.addToStackMsg {
                        buildSendPhotoApi {
                            this.chatId = chatId
                            element.toInputFileCommon()?.also { inputFile ->
                                this.photo = inputFile
                            }
                            // TODO null warning?
                            // TODO 可共享的状态元素?
                        }
                    }
                }

                is IDAwareImage -> {
                    // with a log?
                    context.addToStackMsg {
                        buildSendPhotoApi {
                            this.chatId = chatId
                            this.photo = StringValueInputFile.create(element.id.literal)
                            // TODO 可共享的状态元素?
                        }
                    }
                }
                // other: ignore.
            }
        }
    }
}


internal expect fun OfflineImage.toInputFile(): InputFile?


internal fun OfflineImage.toInputFileCommon(): InputFile? {
    return when (val img = this) {
        is OfflineByteArrayImage -> InputFile(img.data())
        is OfflineResourceImage -> when (val resource = img.resource) {
            is ByteArrayResource, is StringResource -> InputFile(resource.data())
            else -> toInputFile()
        }

        else -> toInputFile()
    }
}
