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

package love.forte.simbot.telegram.api.chat

import kotlinx.serialization.DeserializationStrategy
import love.forte.simbot.telegram.api.SimpleBodyTelegramApi
import love.forte.simbot.telegram.api.TelegramApiResult
import love.forte.simbot.telegram.api.utils.SingleChatIdBody
import love.forte.simbot.telegram.type.Chat
import love.forte.simbot.telegram.type.ChatId
import kotlin.jvm.JvmStatic


/**
 * [getChat](https://core.telegram.org/bots/api#getchat)
 *
 * Use this method to get up to date information about the chat. Returns a Chat object on success.
 *
 * @author ForteScarlet
 */
public class GetChatApi private constructor(cid: ChatId) : SimpleBodyTelegramApi<Chat>() {
    public companion object Factory {
        private const val NAME = "getChat"
        private val SER = TelegramApiResult.serializer(Chat.serializer())

        /**
         * Create an instance of [GetChatApi].
         *
         * @param chatId Unique identifier for the target chat or username of the target supergroup or channel (in the format `@channelusername`)
         */
        @JvmStatic
        public fun create(chatId: ChatId): GetChatApi = GetChatApi(chatId)
    }

    override val name: String
        get() = NAME

    override val body: Any = SingleChatIdBody(cid)

    override val responseDeserializer: DeserializationStrategy<Chat>
        get() = Chat.serializer()

    override val resultDeserializer: DeserializationStrategy<TelegramApiResult<Chat>>
        get() = SER
}
