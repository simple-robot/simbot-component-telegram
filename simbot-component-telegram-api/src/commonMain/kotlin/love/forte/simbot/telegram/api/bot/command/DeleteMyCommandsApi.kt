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
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import love.forte.simbot.telegram.api.SimpleBodyTelegramApi
import love.forte.simbot.telegram.api.TelegramApiResult
import love.forte.simbot.telegram.type.BotCommandScope
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic


/**
 * [deleteMyCommands](https://core.telegram.org/bots/api#deletemycommands)
 *
 * Use this method to delete the list of the bot's commands for the given scope and user language.
 * After deletion, higher level commands will be shown to affected users. Returns True on success.
 *
 * @author ForteScarlet
 */
public class DeleteMyCommandsApi
private constructor(body: Body) : SimpleBodyTelegramApi<Boolean>() {
    public companion object Factory {
        private const val NAME = "deleteMyCommands"
        private val EMPTY = DeleteMyCommandsApi(Body())

        /**
         * Create an instance of [DeleteMyCommandsApi].
         *
         * @param scope A JSON-serialized object, describing scope of users for which the commands are relevant.
         * Defaults to BotCommandScopeDefault.
         * @param languageCode A two-letter ISO 639-1 language code.
         * If empty, commands will be applied to all users from the given scope,
         * for whose language there are no dedicated commands
         */
        @JvmStatic
        @JvmOverloads
        public fun create(
            scope: BotCommandScope? = null,
            languageCode: String? = null,
        ): DeleteMyCommandsApi {
            return if (scope == null && languageCode == null) {
                EMPTY
            } else {
                DeleteMyCommandsApi(
                    Body(
                        scope = scope,
                        languageCode = languageCode
                    )
                )
            }
        }
    }

    /**
     * Request body for [DeleteMyCommandsApi]
     * @property scope A JSON-serialized object, describing scope of users for which the commands are relevant.
     * Defaults to BotCommandScopeDefault.
     * @property languageCode A two-letter ISO 639-1 language code.
     * If empty, commands will be applied to all users from the given scope,
     * for whose language there are no dedicated commands
     */
    @Serializable
    public data class Body(
        val scope: BotCommandScope? = null,
        @SerialName("language_code")
        val languageCode: String? = null,
    )

    override val name: String
        get() = NAME

    override val body: Any = body

    override val responseDeserializer: DeserializationStrategy<Boolean>
        get() = Boolean.serializer()

    override val resultDeserializer: DeserializationStrategy<TelegramApiResult<Boolean>>
        get() = TelegramApiResult.BooleanSerializer
}

/*
Parameter	Type	Required	Description
 */
