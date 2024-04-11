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
import love.forte.simbot.component.telegram.core.message.SendingMessageResolver
import love.forte.simbot.component.telegram.core.message.SendingMessageResolverContext
import love.forte.simbot.component.telegram.core.message.TelegramMessageEntity
import love.forte.simbot.component.telegram.core.message.TelegramTextParseMode
import love.forte.simbot.message.Message
import love.forte.simbot.message.PlainText
import love.forte.simbot.telegram.type.MessageEntity

internal object TextSendingResolver : SendingMessageResolver {
    override suspend fun resolve(
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

