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
import love.forte.simbot.telegram.type.BotCommand
import love.forte.simbot.telegram.type.BotCommandScope
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic


/**
 * [setMyCommands](https://core.telegram.org/bots/api#setmycommands)
 * Use this method to change the list of the bot's commands.
 * See this manual for more details about bot commands. Returns True on success.
 *
 * @author ForteScarlet
 */
public class SetMyCommandsApi
private constructor(body: Body) : SimpleBodyTelegramApi<Boolean>() {
    public companion object Factory {
        private const val NAME = "setMyCommands"

        /**
         * Create an instance of [SetMyCommandsApi].
         *
         * @param commands A JSON-serialized list of bot commands to be set as the list of the bot's commands.
         * At most 100 commands can be specified.
         * @param scope A JSON-serialized object, describing scope of users for which the commands are relevant.
         * Defaults to BotCommandScopeDefault.
         * @param languageCode A two-letter ISO 639-1 language code.
         * If empty, commands will be applied to all users from the given scope,
         * for whose language there are no dedicated commands
         */
        @JvmStatic
        @JvmOverloads
        public fun create(
            commands: List<BotCommand>,
            scope: BotCommandScope? = null,
            languageCode: String? = null,
        ): SetMyCommandsApi = SetMyCommandsApi(
            Body(
                commands = commands,
                scope = scope,
                languageCode = languageCode
            )
        )
    }

    /**
     * Request body for [SetMyCommandsApi]
     * @property commands A JSON-serialized list of bot commands to be set as the list of the bot's commands.
     * At most 100 commands can be specified.
     * @property scope A JSON-serialized object, describing scope of users for which the commands are relevant.
     * Defaults to BotCommandScopeDefault.
     * @property languageCode A two-letter ISO 639-1 language code.
     * If empty, commands will be applied to all users from the given scope,
     * for whose language there are no dedicated commands
     */
    @Serializable
    public data class Body(
        val commands: List<BotCommand> = emptyList(),
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
