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
import love.forte.simbot.telegram.api.EmptyBodyTelegramApi
import love.forte.simbot.telegram.api.TelegramApiResult
import love.forte.simbot.telegram.model.User
import kotlin.jvm.JvmStatic


/**
 * [getMe](https://core.telegram.org/bots/api#getme).
 *
 * > A simple method for testing your bot's authentication token.
 * Requires no parameters. Returns basic information about the bot in form of a User object.
 *
 * @author ForteScarlet
 */
public class GetMeApi private constructor() : EmptyBodyTelegramApi<User>() {
    public companion object Factory {
        private const val NAME = "getMe"
        private val INSTANCE = GetMeApi()

        private val SER = TelegramApiResult.serializer(User.serializer())

        /**
         * Create [GetMeApi]
         */
        @JvmStatic
        public fun create(): GetMeApi = INSTANCE
    }

    override val name: String
        get() = NAME

    override val responseDeserializer: DeserializationStrategy<User>
        get() = User.serializer()

    override val resultDeserializer: DeserializationStrategy<TelegramApiResult<User>>
        get() = SER
}
