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

package love.forte.simbot.telegram.api.bot

import kotlinx.serialization.DeserializationStrategy
import love.forte.simbot.telegram.api.SimpleBodyTelegramApi
import love.forte.simbot.telegram.api.TelegramApiResult
import love.forte.simbot.telegram.api.utils.SingleChatIdBody
import love.forte.simbot.telegram.type.ChatId
import love.forte.simbot.telegram.type.MenuButton
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmSynthetic


/**
 * [getChatMenuButton](https://core.telegram.org/bots/api#getchatmenubutton)
 *
 * Use this method to get the current value of the bot's menu button in a private chat, or the default menu button.
 * Returns [MenuButton] on success.
 *
 * @author ForteScarlet
 */
public class GetChatMenuButtonApi private constructor(chatId: ChatId?) : SimpleBodyTelegramApi<MenuButton>() {
    public companion object Factory {
        private const val NAME = "getChatMenuButton"
        private val SER = TelegramApiResult.serializer(MenuButton.serializer())
        private val EMPTY = GetChatMenuButtonApi(null)

        /**
         * Create [GetChatMenuButtonApi].
         * @param chatId Optional.
         * Unique identifier for the target private chat. If not specified, default bot's menu button will be returned
         */
        @JvmSynthetic
        public fun create(chatId: ChatId? = null): GetChatMenuButtonApi {
            return chatId?.let { GetChatMenuButtonApi(it) } ?: EMPTY
        }

        /**
         * Create [GetChatMenuButtonApi].
         * @param chatId Optional.
         * Unique identifier for the target private chat. If not specified, default bot's menu button will be returned
         */
        @JvmSynthetic
        @JvmOverloads
        public fun create(chatId: Long? = null): GetChatMenuButtonApi =
            create(chatId?.let(::ChatId))

        /**
         * Create [GetChatMenuButtonApi].
         * @param chatId Optional.
         * Unique identifier for the target private chat. If not specified, default bot's menu button will be returned
         */
        @JvmSynthetic
        public fun create(chatId: String?): GetChatMenuButtonApi =
            create(chatId?.let(::ChatId))

    }

    override val name: String
        get() = NAME

    override val body: Any? = chatId?.let(::SingleChatIdBody)

    override val responseDeserializer: DeserializationStrategy<MenuButton>
        get() = MenuButton.serializer()

    override val resultDeserializer: DeserializationStrategy<TelegramApiResult<MenuButton>>
        get() = SER
}
