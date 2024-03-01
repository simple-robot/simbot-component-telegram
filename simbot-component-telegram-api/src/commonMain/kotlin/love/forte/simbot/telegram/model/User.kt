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

package love.forte.simbot.telegram.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * [User](https://core.telegram.org/bots/api#user)
 *
 * This object represents a Telegram user or bot.
 *
 * @property id Unique identifier for this user or bot. This number may have more than 32 significant bits and some programming languages may have difficulty/silent defects in interpreting it. But it has at most 52 significant bits, so a 64-bit integer or double-precision float type are safe for storing this identifier.
 * @property isBot True, if this user is a bot
 * @property firstName User's or bot's first name
 * @property lastName Optional. User's or bot's last name
 * @property username Optional. User's or bot's username
 * @property languageCode Optional. IETF language tag of the user's language
 * @property isPremium Optional. True, if this user is a Telegram Premium user
 * @property addedToAttachmentMenu Optional. True, if this user added the bot to the attachment menu
 * @property canJoinGroups Optional. True, if the bot can be invited to groups. Returned only in getMe.
 * @property canReadAllGroupMessages Optional. True, if privacy mode is disabled for the bot. Returned only in getMe.
 * @property supportsInlineQueries Optional. True, if the bot supports inline queries. Returned only in getMe.
 *
 * @author ForteScarlet
 */
@Serializable
public data class User(
    val id: Long,
    @SerialName("is_bot")
    val isBot: Boolean,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String? = null,
    val username: String? = null,
    @SerialName("language_code")
    val languageCode: String? = null,
    @SerialName("is_premium")
    val isPremium: Boolean? = true,
    @SerialName("added_to_attachment_menu")
    val addedToAttachmentMenu: Boolean? = true,
    @SerialName("can_join_groups")
    val canJoinGroups: Boolean? = null,
    @SerialName("can_read_all_group_messages")
    val canReadAllGroupMessages: Boolean? = null,
    @SerialName("supports_inline_queries")
    val supportsInlineQueries: Boolean? = null,
)
