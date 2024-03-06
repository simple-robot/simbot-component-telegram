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
 * This object represents the scope to which bot commands are applied.
 *
 * @see BotCommandScopeDefault
 * @see BotCommandScopeAllPrivateChats
 * @see BotCommandScopeAllGroupChats
 * @see BotCommandScopeAllChatAdministrators
 * @see BotCommandScopeChat
 * @see BotCommandScopeChatAdministrators
 * @see BotCommandScopeChatMember
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public sealed class BotCommandScope {
    public companion object {
        public const val DEFAULT_SCOPE_NAME: String = "default"
        public const val ALL_PRIVATE_CHATS_SCOPE_NAME: String = "all_private_chats"
        public const val ALL_GROUP_CHATS_SCOPE_NAME: String = "all_group_chats"
        public const val ALL_CHAT_ADMINISTRATORS_SCOPE_NAME: String = "all_chat_administrators"
        public const val CHAT_SCOPE_NAME: String = "chat"
        public const val CHAT_ADMINISTRATORS_SCOPE_NAME: String = "chat_administrators"
        public const val CHAT_MEMBER_SCOPE_NAME: String = "chat_member"
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
@SerialName(BotCommandScope.ALL_CHAT_ADMINISTRATORS_SCOPE_NAME)
public data object BotCommandScopeAllChatAdministrators : BotCommandScope()

/**
 * [BotCommandScopeAllGroupChats](https://core.telegram.org/bots/api#botcommandscopeallgroupchats)
 *
 * Represents the scope of bot commands, covering all group and supergroup chats.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
@SerialName(BotCommandScope.ALL_GROUP_CHATS_SCOPE_NAME)
public data object BotCommandScopeAllGroupChats : BotCommandScope()

/**
 * [BotCommandScopeAllPrivateChats](https://core.telegram.org/bots/api#botcommandscopeallprivatechats)
 *
 * Represents the scope of bot commands, covering all private chats.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
@SerialName(BotCommandScope.ALL_PRIVATE_CHATS_SCOPE_NAME)
public data object BotCommandScopeAllPrivateChats : BotCommandScope()

/**
 * [BotCommandScopeChat](https://core.telegram.org/bots/api#botcommandscopechat)
 *
 * Represents the scope of bot commands, covering a specific chat.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
@SerialName(BotCommandScope.CHAT_SCOPE_NAME)
public data class BotCommandScopeChat(
    /**
     * Unique identifier for the target chat or username of the target supergroup (in the format
     * `@supergroupusername`)
     *
     * type: `Integer or String`
     */
    @SerialName("chat_id")
    public val chatId: ChatId,
) : BotCommandScope()

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
@SerialName(BotCommandScope.CHAT_ADMINISTRATORS_SCOPE_NAME)
public data class BotCommandScopeChatAdministrators(
    /**
     * Unique identifier for the target chat or username of the target supergroup (in the format
     * `@supergroupusername`)
     *
     * type: `Integer or String`
     */
    @SerialName("chat_id")
    public val chatId: ChatId,
) : BotCommandScope()

/**
 * [BotCommandScopeChatMember](https://core.telegram.org/bots/api#botcommandscopechatmember)
 *
 * Represents the scope of bot commands, covering a specific member of a group or supergroup chat.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
@SerialName(BotCommandScope.CHAT_MEMBER_SCOPE_NAME)
public data class BotCommandScopeChatMember(
    /**
     * Unique identifier for the target chat or username of the target supergroup (in the format
     * `@supergroupusername`)
     *
     * type: `Integer or String`
     */
    @SerialName("chat_id")
    public val chatId: ChatId,
    /**
     * Unique identifier of the target user
     *
     * type: `Integer`
     */
    @SerialName("user_id")
    public val userId: Int,
) : BotCommandScope()

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
@SerialName(BotCommandScope.DEFAULT_SCOPE_NAME)
public data object BotCommandScopeDefault : BotCommandScope()
