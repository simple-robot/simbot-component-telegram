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
import love.forte.simbot.telegram.api.utils.LanguageCodeBody
import love.forte.simbot.telegram.type.BotName
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic


/**
 * [getMyName](https://core.telegram.org/bots/api#getmyname)
 *
 * Use this method to get the current bot name for the given user language.
 * Returns [BotName] on success.
 *
 * @author ForteScarlet
 */
public class GetMyNameApi private constructor(languageCode: String?) : SimpleBodyTelegramApi<BotName>() {
    public companion object Factory {
        private const val NAME = "getMyName"
        private val SER = TelegramApiResult.serializer(BotName.serializer())
        private val EMPTY = GetMyNameApi(null)

        /**
         * Create [GetMyNameApi].
         * @param languageCode Optional.
         * A two-letter ISO 639-1 language code or an empty string
         */
        @JvmStatic
        @JvmOverloads
        public fun create(languageCode: String? = null): GetMyNameApi {
            return languageCode?.let { GetMyNameApi(it) } ?: EMPTY
        }

    }

    override val name: String
        get() = NAME

    override val body: Any? = languageCode?.let(::LanguageCodeBody)

    override val responseDeserializer: DeserializationStrategy<BotName>
        get() = BotName.serializer()

    override val resultDeserializer: DeserializationStrategy<TelegramApiResult<BotName>>
        get() = SER
}
