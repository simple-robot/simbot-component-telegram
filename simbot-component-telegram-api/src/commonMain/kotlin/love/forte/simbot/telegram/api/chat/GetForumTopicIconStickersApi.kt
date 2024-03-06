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
import love.forte.simbot.telegram.api.EmptyBodyTelegramApi
import love.forte.simbot.telegram.api.TelegramApiResult
import love.forte.simbot.telegram.type.sticker.Sticker


/**
 * [getForumTopicIconStickers](https://core.telegram.org/bots/api#getforumtopiciconstickers)
 *
 * Use this method to get custom emoji stickers, which can be used as a forum topic icon by any user.
 * Requires no parameters. Returns an Array of [Sticker] objects.
 *
 * @author ForteScarlet
 */
public class GetForumTopicIconStickersApi private constructor() : EmptyBodyTelegramApi<List<Sticker>>() {
    public companion object Factory {
        private const val NAME = "getForumTopicIconStickers"
        private val SER = ListSerializer(Sticker.serializer())
        private val R_SER = TelegramApiResult.serializer(SER)
        private val INSTANCE = GetForumTopicIconStickersApi()

        /**
         * Create an instance of [GetChatMemberCountApi].
         */
        public fun create(): GetForumTopicIconStickersApi = INSTANCE
    }

    override val name: String
        get() = NAME

    override val responseDeserializer: DeserializationStrategy<List<Sticker>>
        get() = SER

    override val resultDeserializer: DeserializationStrategy<TelegramApiResult<List<Sticker>>>
        get() = R_SER
}
