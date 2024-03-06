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
import kotlinx.serialization.builtins.serializer
import love.forte.simbot.telegram.api.SimpleBodyTelegramApi
import love.forte.simbot.telegram.api.TelegramApiResult
import love.forte.simbot.telegram.api.utils.SingleChatIdBody
import love.forte.simbot.telegram.type.ChatId
import kotlin.jvm.JvmStatic


/**
 * [getChatMemberCount](https://core.telegram.org/bots/api#getchatmembercount)
 *
 * Use this method to get the number of members in a chat. Returns [Int] on success.
 *
 * @author ForteScarlet
 */
public class GetChatMemberCountApi private constructor(cid: ChatId) : SimpleBodyTelegramApi<Int>() {
    public companion object Factory {
        private const val NAME = "getChatMemberCount"
        private val SER = TelegramApiResult.serializer(Int.serializer())

        /**
         * Create an instance of [GetChatMemberCountApi].
         */
        @JvmStatic
        public fun create(chatId: ChatId): GetChatMemberCountApi =
            GetChatMemberCountApi(chatId)
    }

    override val name: String
        get() = NAME

    override val body: Any = SingleChatIdBody(cid)

    override val responseDeserializer: DeserializationStrategy<Int>
        get() = Int.serializer()

    override val resultDeserializer: DeserializationStrategy<TelegramApiResult<Int>>
        get() = SER
}
