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

package love.forte.simbot.telegram.api.bot.command

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import love.forte.simbot.telegram.api.SimpleBodyTelegramApi
import love.forte.simbot.telegram.api.TelegramApiResult
import love.forte.simbot.telegram.api.bot.GetMeApi
import love.forte.simbot.telegram.type.BotCommand
import love.forte.simbot.telegram.type.BotCommandScope
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic


/**
 *
 * [getMyCommands](https://core.telegram.org/bots/api#getmycommands)
 *
 * Use this method to get the current list of the bot's commands for the given scope and user language.
 * Returns an Array of [BotCommand] objects. If commands aren't set, an empty list is returned.
 *
 * @author ForteScarlet
 */
public class GetMyCommandsApi private constructor(body: Body?) : SimpleBodyTelegramApi<List<BotCommand>>() {
    public companion object Factory {
        private const val NAME = "getMyCommands"
        private val SER = ListSerializer(BotCommand.serializer())
        private val R_SER = TelegramApiResult.serializer(SER)
        private val EMPTY = GetMyCommandsApi(null)

        /**
         * Create [GetMeApi]
         *
         * @param scope Optional.
         * A JSON-serialized object, describing scope of users. Defaults to BotCommandScopeDefault.
         * see [BotCommandScope]
         * @param languageCode Optional.
         * A two-letter ISO 639-1 language code or an empty string
         */
        @JvmStatic
        @JvmOverloads
        public fun create(
            scope: BotCommandScope? = null,
            languageCode: String? = null
        ): GetMyCommandsApi {
            if (scope == null && languageCode == null) return EMPTY

            return GetMyCommandsApi(Body(scope, languageCode))
        }
    }

    override val name: String
        get() = NAME

    override val body: Any? = body

    override val responseDeserializer: DeserializationStrategy<List<BotCommand>>
        get() = SER

    override val resultDeserializer: DeserializationStrategy<TelegramApiResult<List<BotCommand>>>
        get() = R_SER

    @Serializable
    private data class Body(
        val scope: BotCommandScope? = null,
        val languageCode: String? = null
    )
}
