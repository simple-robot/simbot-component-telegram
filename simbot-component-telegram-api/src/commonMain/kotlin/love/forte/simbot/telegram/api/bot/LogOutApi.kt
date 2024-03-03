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
 * [logOut](https://core.telegram.org/bots/api#logout)
 *
 * > Use this method to log out from the cloud Bot API server before launching the bot locally.
 * You must log out the bot before running it locally,
 * otherwise there is no guarantee that the bot will receive updates.
 * After a successful call, you can immediately log in on a local server,
 * but will not be able to log in back to the cloud Bot API server for 10 minutes.
 * Returns True on success. Requires no parameters.
 *
 * @author ForteScarlet
 */
public class LogOutApi private constructor() : EmptyBodyTelegramApi<Boolean>() {
    public companion object {
        private const val NAME = "logOut"
        private val R_SER = TelegramApiResult.serializer(Boolean.serializer())
        private val INSTANCE = LogOutApi()

        /**
         * Create an instance of [LogOutApi].
         */
        @JvmStatic
        public fun create(): LogOutApi = INSTANCE

    }

    override val name: String
        get() = NAME

    override val responseDeserializer: DeserializationStrategy<Boolean>
        get() = Boolean.serializer()

    override val resultDeserializer: DeserializationStrategy<TelegramApiResult<Boolean>>
        get() = R_SER
}
