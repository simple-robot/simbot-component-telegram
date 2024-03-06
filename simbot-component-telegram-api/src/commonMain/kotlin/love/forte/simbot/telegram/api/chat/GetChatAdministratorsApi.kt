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
import kotlinx.serialization.builtins.ListSerializer
import love.forte.simbot.telegram.api.SimpleBodyTelegramApi
import love.forte.simbot.telegram.api.TelegramApiResult
import love.forte.simbot.telegram.api.utils.SingleChatIdBody
import love.forte.simbot.telegram.type.ChatId
import love.forte.simbot.telegram.type.ChatMember
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic


/**
 * [getChatAdministrators](https://core.telegram.org/bots/api#getchatadministrators)
 *
 * Use this method to get a list of administrators in a chat, which aren't bots.
 * Returns an Array of [ChatMember] objects.
 *
 * @author ForteScarlet
 */
public class GetChatAdministratorsApi private constructor(cid: ChatId) : SimpleBodyTelegramApi<List<ChatMember>>() {
    public companion object Factory {
        private const val NAME = "getChatAdministrators"
        private val SER = ListSerializer(ChatMember.serializer())
        private val R_SER = TelegramApiResult.serializer(SER)

        /**
         * Create an instance of [GetChatAdministratorsApi].
         */
        @JvmSynthetic
        public fun create(chatId: ChatId): GetChatAdministratorsApi =
            GetChatAdministratorsApi(chatId)

        /**
         * Create an instance of [GetChatAdministratorsApi].
         */
        @JvmStatic
        public fun create(chatId: String): GetChatAdministratorsApi =
            create(ChatId(chatId))

        /**
         * Create an instance of [GetChatAdministratorsApi].
         */
        @JvmStatic
        public fun create(chatId: Long): GetChatAdministratorsApi =
            create(ChatId(chatId))
    }

    override val name: String
        get() = NAME

    override val body: Any = SingleChatIdBody(cid)

    override val responseDeserializer: DeserializationStrategy<List<ChatMember>>
        get() = SER

    override val resultDeserializer: DeserializationStrategy<TelegramApiResult<List<ChatMember>>>
        get() = R_SER
}
