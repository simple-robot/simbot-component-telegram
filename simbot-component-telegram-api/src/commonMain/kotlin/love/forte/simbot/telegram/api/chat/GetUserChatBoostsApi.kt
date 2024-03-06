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
import love.forte.simbot.telegram.type.UserChatBoosts
import kotlin.jvm.JvmStatic


/**
 * [getUserChatBoosts](https://core.telegram.org/bots/api#getuserchatboosts)
 *
 * Use this method to get the list of boosts added to a chat by a user.
 * Requires administrator rights in the chat. Returns a [UserChatBoosts] object.
 *
 * @author ForteScarlet
 */
public class GetUserChatBoostsApi private constructor(body: Body) : SimpleBodyTelegramApi<UserChatBoosts>() {
    public companion object Factory {
        private const val NAME = "getUserChatBoosts"
        private val SER = TelegramApiResult.serializer(UserChatBoosts.serializer())

        /**
         * Create [GetUserChatBoostsApi]
         *
         * @param chatId Unique identifier for the chat or username of the channel (in the format `@channelusername`)
         * @param userId Unique identifier of the target user
         *
         */
        @JvmStatic
        public fun create(chatId: ChatId, userId: Int): GetUserChatBoostsApi =
            GetUserChatBoostsApi(Body(chatId, userId))
    }

    override val name: String
        get() = NAME

    override val body: Any = body

    override val responseDeserializer: DeserializationStrategy<UserChatBoosts>
        get() = UserChatBoosts.serializer()

    override val resultDeserializer: DeserializationStrategy<TelegramApiResult<UserChatBoosts>>
        get() = SER

    @Serializable
    private data class Body(
        @SerialName("chat_id") val chatId: ChatId,
        @SerialName("user_id") val userId: Int,
    )
}
