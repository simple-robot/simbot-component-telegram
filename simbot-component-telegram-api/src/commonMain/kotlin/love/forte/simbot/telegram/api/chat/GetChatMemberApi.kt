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
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import love.forte.simbot.telegram.api.SimpleBodyTelegramApi
import love.forte.simbot.telegram.api.TelegramApiResult
import love.forte.simbot.telegram.type.ChatId
import love.forte.simbot.telegram.type.ChatMember
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic


/**
 * [getChatMember](https://core.telegram.org/bots/api#getchatmember)
 *
 * Use this method to get information about a member of a chat.
 * The method is only guaranteed to work for other users if the bot is an administrator in the chat.
 * Returns a [ChatMember] object on success.
 *
 * @author ForteScarlet
 */
public class GetChatMemberApi private constructor(body: Body) : SimpleBodyTelegramApi<ChatMember>() {
    public companion object Factory {
        private const val NAME = "getChatMember"
        private val SER = TelegramApiResult.serializer(ChatMember.serializer())

        /**
         * Create an instance of [GetChatMemberApi].
         */
        @JvmSynthetic
        public fun create(chatId: ChatId, userId: Int): GetChatMemberApi =
            GetChatMemberApi(Body(chatId, userId))

        /**
         * Create an instance of [GetChatMemberApi].
         */
        @JvmStatic
        public fun create(chatId: String, userId: Int): GetChatMemberApi =
            create(ChatId(chatId), userId)

        /**
         * Create an instance of [GetChatMemberApi].
         */
        @JvmStatic
        public fun create(chatId: Long, userId: Int): GetChatMemberApi =
            create(ChatId(chatId), userId)
    }

    override val name: String
        get() = NAME

    override val body: Any = body

    override val responseDeserializer: DeserializationStrategy<ChatMember>
        get() = ChatMember.serializer()

    override val resultDeserializer: DeserializationStrategy<TelegramApiResult<ChatMember>>
        get() = SER

    @Serializable
    private data class Body(
        @SerialName("chat_id") val chatId: ChatId,
        @SerialName("user_id") val userId: Int,
    )
}
