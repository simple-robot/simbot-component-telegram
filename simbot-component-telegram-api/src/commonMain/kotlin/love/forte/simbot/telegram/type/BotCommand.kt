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

package love.forte.simbot.telegram.type

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * [BotCommand](https://core.telegram.org/bots/api#botcommand)
 *
 * This object represents a bot command.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class BotCommand(
    /**
     * Text of the command; 1-32 characters. 
     * Can contain only lowercase English letters, digits and underscores.
     *
     * type: `String`
     */
    public val command: String,
    /**
     * Description of the command; 1-256 characters.
     *
     * type: `String`
     */
    public val description: String,
)

/**
 * [BotCommandScope](https://core.telegram.org/bots/api#botcommandscope)
 *
 * This object represents the scope to which bot commands are applied. Currently, the following 7
 * scopes are supported:
 * BotCommandScopeDefault BotCommandScopeAllPrivateChats BotCommandScopeAllGroupChats
 * BotCommandScopeAllChatAdministrators BotCommandScopeChat BotCommandScopeChatAdministrators
 * BotCommandScopeChatMember
 * Determining list of commands
 * The following algorithm is used to determine the list of commands for a particular user viewing
 * the bot menu. The first list of commands which is set is returned:
 * Commands in the chat with the bot
 * botCommandScopeChat + language_code botCommandScopeChat botCommandScopeAllPrivateChats +
 * language_code botCommandScopeAllPrivateChats botCommandScopeDefault + language_code
 * botCommandScopeDefault
 * Commands in group and supergroup chats
 * botCommandScopeChatMember + language_code botCommandScopeChatMember
 * botCommandScopeChatAdministrators + language_code (administrators only)
 * botCommandScopeChatAdministrators (administrators only) botCommandScopeChat + language_code
 * botCommandScopeChat botCommandScopeAllChatAdministrators + language_code (administrators only)
 * botCommandScopeAllChatAdministrators (administrators only) botCommandScopeAllGroupChats +
 * language_code botCommandScopeAllGroupChats botCommandScopeDefault + language_code
 * botCommandScopeDefault
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public class BotCommandScope {
    init {
        TODO("Empty class?")
    }
}

/**
 * [BotCommandScopeAllChatAdministrators](https://core.telegram.org/bots/api#botcommandscopeallchatadministrators)
 *
 * Represents the scope of bot commands, covering all group and supergroup chat administrators.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class BotCommandScopeAllChatAdministrators(
    /**
     * Scope type, must be all_chat_administrators
     *
     * type: `String`
     */
    public val type: String,
)

/**
 * [BotCommandScopeAllGroupChats](https://core.telegram.org/bots/api#botcommandscopeallgroupchats)
 *
 * Represents the scope of bot commands, covering all group and supergroup chats.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class BotCommandScopeAllGroupChats(
    /**
     * Scope type, must be all_group_chats
     *
     * type: `String`
     */
    public val type: String,
)

/**
 * [BotCommandScopeAllPrivateChats](https://core.telegram.org/bots/api#botcommandscopeallprivatechats)
 *
 * Represents the scope of bot commands, covering all private chats.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class BotCommandScopeAllPrivateChats(
    /**
     * Scope type, must be all_private_chats
     *
     * type: `String`
     */
    public val type: String,
)

/**
 * [BotCommandScopeChat](https://core.telegram.org/bots/api#botcommandscopechat)
 *
 * Represents the scope of bot commands, covering a specific chat.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class BotCommandScopeChat(
    /**
     * Scope type, must be chat
     *
     * type: `String`
     */
    public val type: String,
    /**
     * Unique identifier for the target chat or username of the target supergroup (in the format
     * `@supergroupusername`)
     *
     * type: `Integer or String`
     */
    @SerialName("chat_id")
    public val chatId: String,
)

/**
 * [BotCommandScopeChatAdministrators](https://core.telegram.org/bots/api#botcommandscopechatadministrators)
 *
 * Represents the scope of bot commands, covering all administrators of a specific group or
 * supergroup chat.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class BotCommandScopeChatAdministrators(
    /**
     * Scope type, must be chat_administrators
     *
     * type: `String`
     */
    public val type: String,
    /**
     * Unique identifier for the target chat or username of the target supergroup (in the format
     * `@supergroupusername`)
     *
     * type: `Integer or String`
     */
    @SerialName("chat_id")
    public val chatId: String,
)

/**
 * [BotCommandScopeChatMember](https://core.telegram.org/bots/api#botcommandscopechatmember)
 *
 * Represents the scope of bot commands, covering a specific member of a group or supergroup chat.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class BotCommandScopeChatMember(
    /**
     * Scope type, must be chat_member
     *
     * type: `String`
     */
    public val type: String,
    /**
     * Unique identifier for the target chat or username of the target supergroup (in the format
     * `@supergroupusername`)
     *
     * type: `Integer or String`
     */
    @SerialName("chat_id")
    public val chatId: String,
    /**
     * Unique identifier of the target user
     *
     * type: `Integer`
     */
    @SerialName("user_id")
    public val userId: Int,
)

/**
 * [BotCommandScopeDefault](https://core.telegram.org/bots/api#botcommandscopedefault)
 *
 * Represents the default scope of bot commands. Default commands are used if no commands with a
 * narrower scope are specified for the user.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class BotCommandScopeDefault(
    /**
     * Scope type, must be default
     *
     * type: `String`
     */
    public val type: String,
)
