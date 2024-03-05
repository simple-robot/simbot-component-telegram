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
import kotlinx.serialization.builtins.serializer
import love.forte.simbot.telegram.api.EmptyBodyTelegramApi
import love.forte.simbot.telegram.api.TelegramApiResult
import kotlin.jvm.JvmStatic


/**
 * [close](https://core.telegram.org/bots/api#close)
 *
 * Use this method to close the bot instance before moving it from one local server to another.
 * You need to delete the webhook before calling this method to ensure that the bot isn't launched again after server restart.
 * The method will return error 429 in the first 10 minutes after the bot is launched. Returns True on success. Requires no parameters.
 *
 * @author ForteScarlet
 */
public class CloseApi private constructor() : EmptyBodyTelegramApi<Boolean>() {
    public companion object Factory {
        private const val NAME = "close"
        private val INSTANCE = CloseApi()

        /**
         * Create an instance of [CloseApi].
         */
        @JvmStatic
        public fun create(): CloseApi = INSTANCE
    }

    override val name: String
        get() = NAME

    override val responseDeserializer: DeserializationStrategy<Boolean>
        get() = Boolean.serializer()

    override val resultDeserializer: DeserializationStrategy<TelegramApiResult<Boolean>>
        get() = TelegramApiResult.BooleanSerializer
}
