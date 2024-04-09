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

@file:JvmName("TelegramMessageResolvers")

package love.forte.simbot.component.telegram.core.message

import love.forte.simbot.telegram.api.message.CopyMessageApi
import love.forte.simbot.telegram.api.message.ForwardMessageApi
import love.forte.simbot.telegram.api.message.buildCopyMessageApi
import love.forte.simbot.telegram.api.message.buildForwardMessageApi
import love.forte.simbot.telegram.type.ChatId
import kotlin.jvm.JvmName
import love.forte.simbot.telegram.type.Message as StdlibMessage


internal inline fun StdlibMessage.toCopyApi(
    chatId: ChatId,
    block: CopyMessageApi.Builder.() -> Unit = {}
): CopyMessageApi {
    return buildCopyMessageApi(chatId, ChatId(chat.id), messageId) {
        block()
    }
}

internal inline fun StdlibMessage.toForwardApi(
    chatId: ChatId,
    block: ForwardMessageApi.Builder.() -> Unit = {}
): ForwardMessageApi {
    return buildForwardMessageApi(chatId, ChatId(chat.id), messageId) {
        block()
    }
}
