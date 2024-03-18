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

package love.forte.simbot.telegram.api.message

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import love.forte.simbot.telegram.api.SimpleBodyTelegramApi
import love.forte.simbot.telegram.api.TelegramApiResult
import love.forte.simbot.telegram.type.ChatId
import kotlin.jvm.JvmStatic


/**
 * [deleteMessage](https://core.telegram.org/bots/api#deletemessage)
 *
 * @author ForteScarlet
 */
public class DeleteMessageApi private constructor(body: Body) : SimpleBodyTelegramApi<Boolean>() {
    public companion object Factory {
        private const val NAME = "deleteMessage"

        /**
         * Create instance of [DeleteMessageApi].
         *
         * @param chatId Unique identifier for the target chat or username of the target channel (in the format `@channelusername`)
         * @param messageId Identifier of the message to delete
         */
        @JvmStatic
        public fun create(chatId: ChatId, messageId: Int): DeleteMessageApi =
            DeleteMessageApi(Body(chatId, messageId))
    }

    override val name: String
        get() = NAME

    override val responseDeserializer: DeserializationStrategy<Boolean>
        get() = Boolean.serializer()

    override val resultDeserializer: DeserializationStrategy<TelegramApiResult<Boolean>>
        get() = TelegramApiResult.BooleanSerializer

    override val body: Any = body

    @Serializable
    private data class Body(
        @SerialName("chat_id") val chatId: ChatId,
        @SerialName("message_id") val messageId: Int,
    )

}
