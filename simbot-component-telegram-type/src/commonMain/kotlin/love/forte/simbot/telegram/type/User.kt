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
 * [User](https://core.telegram.org/bots/api#user)
 *
 * This object represents a Telegram user or bot.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class User(
    /**
     * Unique identifier for this user or bot. 
     * This number may have more than 32 significant bits and some programming languages may have
     * difficulty/silent defects in interpreting it. 
     * But it has at most 52 significant bits, so a 64-bit integer or double-precision float type
     * are safe for storing this identifier.
     *
     * type: `Integer`
     */
    public val id: Long,
    /**
     * True, if this user is a bot
     *
     * type: `Boolean`
     */
    @SerialName("is_bot")
    public val isBot: Boolean,
    /**
     * User's or bot's first name
     *
     * type: `String`
     */
    @SerialName("first_name")
    public val firstName: String,
    /**
     * Optional. 
     * User's or bot's last name
     *
     * type: `String`
     */
    @SerialName("last_name")
    public val lastName: String? = null,
    /**
     * Optional. 
     * User's or bot's username
     *
     * type: `String`
     */
    public val username: String? = null,
    /**
     * Optional. 
     * IETF language tag of the user's language
     *
     * type: `String`
     */
    @SerialName("language_code")
    public val languageCode: String? = null,
    /**
     * Optional. 
     * True, if this user is a Telegram Premium user
     *
     * type: `True`
     */
    @SerialName("is_premium")
    public val isPremium: Boolean? = null,
    /**
     * Optional. 
     * True, if this user added the bot to the attachment menu
     *
     * type: `True`
     */
    @SerialName("added_to_attachment_menu")
    public val addedToAttachmentMenu: Boolean? = null,
    /**
     * Optional. 
     * True, if the bot can be invited to groups. 
     * Returned only in getMe.
     *
     * type: `Boolean`
     */
    @SerialName("can_join_groups")
    public val canJoinGroups: Boolean? = null,
    /**
     * Optional. 
     * True, if privacy mode is disabled for the bot. 
     * Returned only in getMe.
     *
     * type: `Boolean`
     */
    @SerialName("can_read_all_group_messages")
    public val canReadAllGroupMessages: Boolean? = null,
    /**
     * Optional. 
     * True, if the bot supports inline queries. 
     * Returned only in getMe.
     *
     * type: `Boolean`
     */
    @SerialName("supports_inline_queries")
    public val supportsInlineQueries: Boolean? = null,
)

/**
 * [UserChatBoosts](https://core.telegram.org/bots/api#userchatboosts)
 *
 * This object represents a list of boosts added to a chat by a user.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class UserChatBoosts(
    /**
     * The list of boosts added to the chat by the user
     *
     * type: `Array of ChatBoost`
     */
    public val boosts: List<love.forte.simbot.telegram.type.ChatBoost> = emptyList(),
)

/**
 * [UserProfilePhotos](https://core.telegram.org/bots/api#userprofilephotos)
 *
 * This object represent a user's profile pictures.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class UserProfilePhotos(
    /**
     * Total number of profile pictures the target user has
     *
     * type: `Integer`
     */
    @SerialName("total_count")
    public val totalCount: Int,
    /**
     * Requested profile pictures (in up to 4 sizes each)
     *
     * type: `Array of Array of PhotoSize`
     */
    public val photos: List<List<love.forte.simbot.telegram.type.PhotoSize>> = emptyList(),
)

/**
 * [UsersShared](https://core.telegram.org/bots/api#usersshared)
 *
 * This object contains information about the users whose identifiers were shared with the bot using
 * a KeyboardButtonRequestUsers button.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class UsersShared(
    /**
     * Identifier of the request
     *
     * type: `Integer`
     */
    @SerialName("request_id")
    public val requestId: Int,
    /**
     * Identifiers of the shared users. 
     * These numbers may have more than 32 significant bits and some programming languages may have
     * difficulty/silent defects in interpreting them. 
     * But they have at most 52 significant bits, so 64-bit integers or double-precision float types
     * are safe for storing these identifiers. 
     * The bot may not have access to the users and could be unable to use these identifiers, unless
     * the users are already known to the bot by some other means.
     *
     * type: `Array of Integer`
     */
    @SerialName("user_ids")
    public val userIds: List<Long> = emptyList(),
)
