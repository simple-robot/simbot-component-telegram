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
import love.forte.simbot.telegram.type.BotShortDescription
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic


/**
 * [getMyShortDescription](https://core.telegram.org/bots/api#https://core.telegram.org/bots/api#getmyshortdescription)
 *
 * Use this method to get the current bot short description for the given user language.
 * Returns [BotShortDescription] on success.
 *
 * @author ForteScarlet
 */
public class GetMyShortDescriptionApi private constructor(languageCode: String?) : SimpleBodyTelegramApi<BotShortDescription>() {
    public companion object Factory {
        private const val NAME = "getMyShortDescription"
        private val SER = TelegramApiResult.serializer(BotShortDescription.serializer())
        private val EMPTY = GetMyShortDescriptionApi(null)

        /**
         * Create [GetMyShortDescriptionApi].
         * @param languageCode Optional.
         * A two-letter ISO 639-1 language code or an empty string
         */
        @JvmStatic
        @JvmOverloads
        public fun create(languageCode: String? = null): GetMyShortDescriptionApi {
            return languageCode?.let { GetMyShortDescriptionApi(it) } ?: EMPTY
        }

    }

    override val name: String
        get() = NAME

    override val body: Any? = languageCode?.let(::LanguageCodeBody)

    override val responseDeserializer: DeserializationStrategy<BotShortDescription>
        get() = BotShortDescription.serializer()

    override val resultDeserializer: DeserializationStrategy<TelegramApiResult<BotShortDescription>>
        get() = SER
}

