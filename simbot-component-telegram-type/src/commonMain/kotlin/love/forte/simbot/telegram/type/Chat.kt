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

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.jvm.JvmName
import kotlin.jvm.JvmStatic

/**
 * [Chat](https://core.telegram.org/bots/api#chat)
 *
 * This object represents a chat.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class Chat(
    /**
     * Unique identifier for this chat.
     * This number may have more than 32 significant bits and some programming languages may have
     * difficulty/silent defects in interpreting it.
     * But it has at most 52 significant bits, so a signed 64-bit integer or double-precision float
     * type are safe for storing this identifier.
     *
     * type: `Integer`
     */
    public val id: Long,
    /**
     * Type of chat, can be either “private”, “group”, “supergroup” or “channel”
     *
     * type: `String`
     */
    public val type: String,
    /**
     * Optional.
     * Title, for supergroups, channels and group chats
     *
     * type: `String`
     */
    public val title: String? = null,
    /**
     * Optional.
     * Username, for private chats, supergroups and channels if available
     *
     * type: `String`
     */
    public val username: String? = null,
    /**
     * Optional.
     * First name of the other party in a private chat
     *
     * type: `String`
     */
    @SerialName("first_name")
    public val firstName: String? = null,
    /**
     * Optional.
     * Last name of the other party in a private chat
     *
     * type: `String`
     */
    @SerialName("last_name")
    public val lastName: String? = null,
    /**
     * Optional.
     * True, if the supergroup chat is a forum (has topics enabled)
     *
     * type: `True`
     */
    @SerialName("is_forum")
    public val isForum: Boolean? = null,
    /**
     * Optional.
     * Chat photo.
     * Returned only in getChat.
     *
     * type: `ChatPhoto`
     */
    public val photo: ChatPhoto? = null,
    /**
     * Optional.
     * If non-empty, the list of all active chat usernames; for private chats, supergroups and
     * channels.
     * Returned only in getChat.
     *
     * type: `Array of String`
     */
    @SerialName("active_usernames")
    public val activeUsernames: List<String>? = null,
    /**
     * Optional.
     * List of available reactions allowed in the chat.
     * If omitted, then all emoji reactions are allowed.
     * Returned only in getChat.
     *
     * type: `Array of ReactionType`
     */
    @SerialName("available_reactions")
    public val availableReactions: List<ReactionType>? = null,
    /**
     * Optional.
     * Identifier of the accent color for the chat name and backgrounds of the chat photo, reply
     * header, and link preview.
     * See accent colors for more details.
     * Returned only in getChat.
     * Always returned in getChat.
     *
     * type: `Integer`
     */
    @SerialName("accent_color_id")
    public val accentColorId: Int? = null,
    /**
     * Optional.
     * Custom emoji identifier of emoji chosen by the chat for the reply header and link preview
     * background.
     * Returned only in getChat.
     *
     * type: `String`
     */
    @SerialName("background_custom_emoji_id")
    public val backgroundCustomEmojiId: String? = null,
    /**
     * Optional.
     * Identifier of the accent color for the chat's profile background.
     * See profile accent colors for more details.
     * Returned only in getChat.
     *
     * type: `Integer`
     */
    @SerialName("profile_accent_color_id")
    public val profileAccentColorId: Int? = null,
    /**
     * Optional.
     * Custom emoji identifier of the emoji chosen by the chat for its profile background.
     * Returned only in getChat.
     *
     * type: `String`
     */
    @SerialName("profile_background_custom_emoji_id")
    public val profileBackgroundCustomEmojiId: String? = null,
    /**
     * Optional.
     * Custom emoji identifier of the emoji status of the chat or the other party in a private chat.
     *
     * Returned only in getChat.
     *
     * type: `String`
     */
    @SerialName("emoji_status_custom_emoji_id")
    public val emojiStatusCustomEmojiId: String? = null,
    /**
     * Optional.
     * Expiration date of the emoji status of the chat or the other party in a private chat, in Unix
     * time, if any.
     * Returned only in getChat.
     *
     * type: `Integer`
     */
    @SerialName("emoji_status_expiration_date")
    public val emojiStatusExpirationDate: Int? = null,
    /**
     * Optional.
     * Bio of the other party in a private chat.
     * Returned only in getChat.
     *
     * type: `String`
     */
    public val bio: String? = null,
    /**
     * Optional.
     * True, if privacy settings of the other party in the private chat allows to use
     * tg://user?id=<user_id> links only in chats with the user.
     * Returned only in getChat.
     *
     * type: `True`
     */
    @SerialName("has_private_forwards")
    public val hasPrivateForwards: Boolean? = null,
    /**
     * Optional.
     * True, if the privacy settings of the other party restrict sending voice and video note
     * messages in the private chat.
     * Returned only in getChat.
     *
     * type: `True`
     */
    @SerialName("has_restricted_voice_and_video_messages")
    public val hasRestrictedVoiceAndVideoMessages: Boolean? = null,
    /**
     * Optional.
     * True, if users need to join the supergroup before they can send messages.
     * Returned only in getChat.
     *
     * type: `True`
     */
    @SerialName("join_to_send_messages")
    public val joinToSendMessages: Boolean? = null,
    /**
     * Optional.
     * True, if all users directly joining the supergroup need to be approved by supergroup
     * administrators.
     * Returned only in getChat.
     *
     * type: `True`
     */
    @SerialName("join_by_request")
    public val joinByRequest: Boolean? = null,
    /**
     * Optional.
     * Description, for groups, supergroups and channel chats.
     * Returned only in getChat.
     *
     * type: `String`
     */
    public val description: String? = null,
    /**
     * Optional.
     * Primary invite link, for groups, supergroups and channel chats.
     * Returned only in getChat.
     *
     * type: `String`
     */
    @SerialName("invite_link")
    public val inviteLink: String? = null,
    /**
     * Optional.
     * The most recent pinned message (by sending date).
     * Returned only in getChat.
     *
     * type: `Message`
     */
    @SerialName("pinned_message")
    public val pinnedMessage: Message? = null,
    /**
     * Optional.
     * Default chat member permissions, for groups and supergroups.
     * Returned only in getChat.
     *
     * type: `ChatPermissions`
     */
    public val permissions: ChatPermissions? = null,
    /**
     * Optional.
     * For supergroups, the minimum allowed delay between consecutive messages sent by each
     * unprivileged user; in seconds.
     * Returned only in getChat.
     *
     * type: `Integer`
     */
    @SerialName("slow_mode_delay")
    public val slowModeDelay: Int? = null,
    /**
     * Optional.
     * For supergroups, the minimum number of boosts that a non-administrator user needs to add in
     * order to ignore slow mode and chat permissions.
     * Returned only in getChat.
     *
     * type: `Integer`
     */
    @SerialName("unrestrict_boost_count")
    public val unrestrictBoostCount: Int? = null,
    /**
     * Optional.
     * The time after which all messages sent to the chat will be automatically deleted; in seconds.
     *
     * Returned only in getChat.
     *
     * type: `Integer`
     */
    @SerialName("message_auto_delete_time")
    public val messageAutoDeleteTime: Int? = null,
    /**
     * Optional.
     * True, if aggressive anti-spam checks are enabled in the supergroup.
     * The field is only available to chat administrators.
     * Returned only in getChat.
     *
     * type: `True`
     */
    @SerialName("has_aggressive_anti_spam_enabled")
    public val hasAggressiveAntiSpamEnabled: Boolean? = null,
    /**
     * Optional.
     * True, if non-administrators can only get the list of bots and administrators in the chat.
     * Returned only in getChat.
     *
     * type: `True`
     */
    @SerialName("has_hidden_members")
    public val hasHiddenMembers: Boolean? = null,
    /**
     * Optional.
     * True, if messages from the chat can't be forwarded to other chats.
     * Returned only in getChat.
     *
     * type: `True`
     */
    @SerialName("has_protected_content")
    public val hasProtectedContent: Boolean? = null,
    /**
     * Optional.
     * True, if new chat members will have access to old messages; available only to chat
     * administrators.
     * Returned only in getChat.
     *
     * type: `True`
     */
    @SerialName("has_visible_history")
    public val hasVisibleHistory: Boolean? = null,
    /**
     * Optional.
     * For supergroups, name of group sticker set.
     * Returned only in getChat.
     *
     * type: `String`
     */
    @SerialName("sticker_set_name")
    public val stickerSetName: String? = null,
    /**
     * Optional.
     * True, if the bot can change the group sticker set.
     * Returned only in getChat.
     *
     * type: `True`
     */
    @SerialName("can_set_sticker_set")
    public val canSetStickerSet: Boolean? = null,
    /**
     * Optional.
     * For supergroups, the name of the group's custom emoji sticker set.
     * Custom emoji from this set can be used by all users and bots in the group.
     * Returned only in getChat.
     *
     * type: `String`
     */
    @SerialName("custom_emoji_sticker_set_name")
    public val customEmojiStickerSetName: String? = null,
    /**
     * Optional.
     * Unique identifier for the linked chat, i.e.
     * the discussion group identifier for a channel and vice versa; for supergroups and channel
     * chats.
     * This identifier may be greater than 32 bits and some programming languages may have
     * difficulty/silent defects in interpreting it.
     * But it is smaller than 52 bits, so a signed 64 bit integer or double-precision float type are
     * safe for storing this identifier.
     * Returned only in getChat.
     *
     * type: `Integer`
     */
    @SerialName("linked_chat_id")
    public val linkedChatId: Int? = null,
    /**
     * Optional.
     * For supergroups, the location to which the supergroup is connected.
     * Returned only in getChat.
     *
     * type: `ChatLocation`
     */
    public val location: ChatLocation? = null,
)

/**
 * Type of chat,
 * can be either `private`, `group`, `supergroup` or `channel`
 *
 * @see Chat.type
 * @author ForteScarlet
 */
public enum class ChatType(public val value: String) {
    PRIVATE("private"),
    GROUP("group"),
    SUPERGROUP("supergroup"),
    CHANNEL("channel");

    public companion object {
        /**
         * Get from [Chat.type].
         *
         * @throws NoSuchElementException if is unknown value
         */
        @JvmStatic
        public fun ofChatTypeValue(value: String): ChatType =
            ofChatTypeValueOrNull(value) ?: throw NoSuchElementException(value)

        /**
         * Get from [Chat.type].
         *
         * @throws NoSuchElementException if is unknown value
         */
        @JvmStatic
        public fun ofChatTypeValueOrNull(value: String): ChatType? =
            when (value) {
                "private" -> PRIVATE
                "group" -> GROUP
                "supergroup" -> SUPERGROUP
                "channel" -> CHANNEL
                else -> null
            }

        /**
         * Get from [Chat].
         *
         * @throws NoSuchElementException if is unknown value
         */
        @get:JvmName("ofChat")
        @get:JvmStatic
        public val Chat.chatType: ChatType
            get() = ofChatTypeValue(type)

        /**
         * Get from [Chat] or null.
         */
        @get:JvmName("ofChatOrNull")
        @get:JvmStatic
        public val Chat.chatTypeOrNull: ChatType?
            get() = ofChatTypeValueOrNull(type)



    }
}

/**
 * [ChatAdministratorRights](https://core.telegram.org/bots/api#chatadministratorrights)
 *
 * Represents the rights of an administrator in a chat.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class ChatAdministratorRights(
    /**
     * True, if the user's presence in the chat is hidden
     *
     * type: `Boolean`
     */
    @SerialName("is_anonymous")
    public val isAnonymous: Boolean,
    /**
     * True, if the administrator can access the chat event log, get boost list, see hidden
     * supergroup and channel members, report spam messages and ignore slow mode.
     * Implied by any other administrator privilege.
     *
     * type: `Boolean`
     */
    @SerialName("can_manage_chat")
    public val canManageChat: Boolean,
    /**
     * True, if the administrator can delete messages of other users
     *
     * type: `Boolean`
     */
    @SerialName("can_delete_messages")
    public val canDeleteMessages: Boolean,
    /**
     * True, if the administrator can manage video chats
     *
     * type: `Boolean`
     */
    @SerialName("can_manage_video_chats")
    public val canManageVideoChats: Boolean,
    /**
     * True, if the administrator can restrict, ban or unban chat members, or access supergroup
     * statistics
     *
     * type: `Boolean`
     */
    @SerialName("can_restrict_members")
    public val canRestrictMembers: Boolean,
    /**
     * True, if the administrator can add new administrators with a subset of their own privileges
     * or demote administrators that they have promoted, directly or indirectly (promoted by
     * administrators that were appointed by the user)
     *
     * type: `Boolean`
     */
    @SerialName("can_promote_members")
    public val canPromoteMembers: Boolean,
    /**
     * True, if the user is allowed to change the chat title, photo and other settings
     *
     * type: `Boolean`
     */
    @SerialName("can_change_info")
    public val canChangeInfo: Boolean,
    /**
     * True, if the user is allowed to invite new users to the chat
     *
     * type: `Boolean`
     */
    @SerialName("can_invite_users")
    public val canInviteUsers: Boolean,
    /**
     * True, if the administrator can post stories to the chat
     *
     * type: `Boolean`
     */
    @SerialName("can_post_stories")
    public val canPostStories: Boolean,
    /**
     * True, if the administrator can edit stories posted by other users
     *
     * type: `Boolean`
     */
    @SerialName("can_edit_stories")
    public val canEditStories: Boolean,
    /**
     * True, if the administrator can delete stories posted by other users
     *
     * type: `Boolean`
     */
    @SerialName("can_delete_stories")
    public val canDeleteStories: Boolean,
    /**
     * Optional.
     * True, if the administrator can post messages in the channel, or access channel statistics;
     * for channels only
     *
     * type: `Boolean`
     */
    @SerialName("can_post_messages")
    public val canPostMessages: Boolean? = null,
    /**
     * Optional.
     * True, if the administrator can edit messages of other users and can pin messages; for
     * channels only
     *
     * type: `Boolean`
     */
    @SerialName("can_edit_messages")
    public val canEditMessages: Boolean? = null,
    /**
     * Optional.
     * True, if the user is allowed to pin messages; for groups and supergroups only
     *
     * type: `Boolean`
     */
    @SerialName("can_pin_messages")
    public val canPinMessages: Boolean? = null,
    /**
     * Optional.
     * True, if the user is allowed to create, rename, close, and reopen forum topics; for
     * supergroups only
     *
     * type: `Boolean`
     */
    @SerialName("can_manage_topics")
    public val canManageTopics: Boolean? = null,
)

/**
 * [ChatBoost](https://core.telegram.org/bots/api#chatboost)
 *
 * This object contains information about a chat boost.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class ChatBoost(
    /**
     * Unique identifier of the boost
     *
     * type: `String`
     */
    @SerialName("boost_id")
    public val boostId: String,
    /**
     * Point in time (Unix timestamp) when the chat was boosted
     *
     * type: `Integer`
     */
    @SerialName("add_date")
    public val addDate: Int,
    /**
     * Point in time (Unix timestamp) when the boost will automatically expire, unless the booster's
     * Telegram Premium subscription is prolonged
     *
     * type: `Integer`
     */
    @SerialName("expiration_date")
    public val expirationDate: Int,
    /**
     * Source of the added boost
     *
     * type: `ChatBoostSource`
     */
    public val source: ChatBoostSource,
)

/**
 * [ChatBoostAdded](https://core.telegram.org/bots/api#chatboostadded)
 *
 * This object represents a service message about a user boosting a chat.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class ChatBoostAdded(
    /**
     * Number of boosts added by the user
     *
     * type: `Integer`
     */
    @SerialName("boost_count")
    public val boostCount: Int,
)

/**
 * [ChatBoostRemoved](https://core.telegram.org/bots/api#chatboostremoved)
 *
 * This object represents a boost removed from a chat.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class ChatBoostRemoved(
    /**
     * Chat which was boosted
     *
     * type: `Chat`
     */
    public val chat: Chat,
    /**
     * Unique identifier of the boost
     *
     * type: `String`
     */
    @SerialName("boost_id")
    public val boostId: String,
    /**
     * Point in time (Unix timestamp) when the boost was removed
     *
     * type: `Integer`
     */
    @SerialName("remove_date")
    public val removeDate: Int,
    /**
     * Source of the removed boost
     *
     * type: `ChatBoostSource`
     */
    public val source: ChatBoostSource,
)

/**
 * [ChatBoostSource](https://core.telegram.org/bots/api#chatboostsource)
 *
 * This object describes the source of a chat boost.
 *
 * Note: Because the property name of the class discriminator is not `type`,
 * [JsonClassDiscriminator] is specified,
 * and therefore only using [Json][kotlinx.serialization.json.Json] serialization is better.
 *
 * @see ChatBoostSourcePremium
 * @see ChatBoostSourceGiftCode
 * @see ChatBoostSourceGiveaway
 *
 * @author ForteScarlet
 */
@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator(ChatBoostSource.CLASS_DISCRIMINATOR)
public sealed class ChatBoostSource {
    public companion object {
        public const val CLASS_DISCRIMINATOR: String = "source"

        public const val PREMIUM_SOURCE_NAME: String = "premium"
        public const val GIFT_CODE_SOURCE_NAME: String = "gift_code"
        public const val GIVEAWAY_SOURCE_NAME: String = "giveaway"
    }
}

/**
 * [ChatBoostSourceGiftCode](https://core.telegram.org/bots/api#chatboostsourcegiftcode)
 *
 * The boost was obtained by the creation of Telegram Premium gift codes to boost a chat. Each such
 * code boosts the chat 4 times for the duration of the corresponding Telegram Premium subscription.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
@SerialName(ChatBoostSource.GIFT_CODE_SOURCE_NAME)
public data class ChatBoostSourceGiftCode(
    /**
     * User for which the gift code was created
     *
     * type: `User`
     */
    public val user: User,
) : ChatBoostSource()

/**
 * [ChatBoostSourceGiveaway](https://core.telegram.org/bots/api#chatboostsourcegiveaway)
 *
 * The boost was obtained by the creation of a Telegram Premium giveaway. This boosts the chat 4
 * times for the duration of the corresponding Telegram Premium subscription.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
@SerialName(ChatBoostSource.GIVEAWAY_SOURCE_NAME)
public data class ChatBoostSourceGiveaway(
    /**
     * Identifier of a message in the chat with the giveaway; the message could have been deleted
     * already.
     * May be 0 if the message isn't sent yet.
     *
     * type: `Integer`
     */
    @SerialName("giveaway_message_id")
    public val giveawayMessageId: Int,
    /**
     * Optional.
     * User that won the prize in the giveaway if any
     *
     * type: `User`
     */
    public val user: User? = null,
    /**
     * Optional.
     * True, if the giveaway was completed, but there was no user to win the prize
     *
     * type: `True`
     */
    @SerialName("is_unclaimed")
    public val isUnclaimed: Boolean? = null,
) : ChatBoostSource()

/**
 * [ChatBoostSourcePremium](https://core.telegram.org/bots/api#chatboostsourcepremium)
 *
 * The boost was obtained by subscribing to Telegram Premium or by gifting a Telegram Premium
 * subscription to another user.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
@SerialName(ChatBoostSource.PREMIUM_SOURCE_NAME)
public data class ChatBoostSourcePremium(
    /**
     * User that boosted the chat
     *
     * type: `User`
     */
    public val user: User,
) : ChatBoostSource()

/**
 * [ChatBoostUpdated](https://core.telegram.org/bots/api#chatboostupdated)
 *
 * This object represents a boost added to a chat or changed.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class ChatBoostUpdated(
    /**
     * Chat which was boosted
     *
     * type: `Chat`
     */
    public val chat: Chat,
    /**
     * Information about the chat boost
     *
     * type: `ChatBoost`
     */
    public val boost: ChatBoost,
)

/**
 * [ChatInviteLink](https://core.telegram.org/bots/api#chatinvitelink)
 *
 * Represents an invite link for a chat.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class ChatInviteLink(
    /**
     * The invite link.
     * If the link was created by another chat administrator, then the second part of the link will
     * be replaced with “…”.
     *
     * type: `String`
     */
    @SerialName("invite_link")
    public val inviteLink: String,
    /**
     * Creator of the link
     *
     * type: `User`
     */
    public val creator: User,
    /**
     * True, if users joining the chat via the link need to be approved by chat administrators
     *
     * type: `Boolean`
     */
    @SerialName("creates_join_request")
    public val createsJoinRequest: Boolean,
    /**
     * True, if the link is primary
     *
     * type: `Boolean`
     */
    @SerialName("is_primary")
    public val isPrimary: Boolean,
    /**
     * True, if the link is revoked
     *
     * type: `Boolean`
     */
    @SerialName("is_revoked")
    public val isRevoked: Boolean,
    /**
     * Optional.
     * Invite link name
     *
     * type: `String`
     */
    public val name: String? = null,
    /**
     * Optional.
     * Point in time (Unix timestamp) when the link will expire or has been expired
     *
     * type: `Integer`
     */
    @SerialName("expire_date")
    public val expireDate: Int? = null,
    /**
     * Optional.
     * The maximum number of users that can be members of the chat simultaneously after joining the
     * chat via this invite link; 1-99999
     *
     * type: `Integer`
     */
    @SerialName("member_limit")
    public val memberLimit: Int? = null,
    /**
     * Optional.
     * Number of pending join requests created using this link
     *
     * type: `Integer`
     */
    @SerialName("pending_join_request_count")
    public val pendingJoinRequestCount: Int? = null,
)

/**
 * [ChatJoinRequest](https://core.telegram.org/bots/api#chatjoinrequest)
 *
 * Represents a join request sent to a chat.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class ChatJoinRequest(
    /**
     * Chat to which the request was sent
     *
     * type: `Chat`
     */
    public val chat: Chat,
    /**
     * User that sent the join request
     *
     * type: `User`
     */
    public val from: User,
    /**
     * Identifier of a private chat with the user who sent the join request.
     * This number may have more than 32 significant bits and some programming languages may have
     * difficulty/silent defects in interpreting it.
     * But it has at most 52 significant bits, so a 64-bit integer or double-precision float type
     * are safe for storing this identifier.
     * The bot can use this identifier for 5 minutes to send messages until the join request is
     * processed, assuming no other administrator contacted the user.
     *
     * type: `Integer`
     */
    @SerialName("user_chat_id")
    public val userChatId: Long,
    /**
     * Date the request was sent in Unix time
     *
     * type: `Integer`
     */
    public val date: Int,
    /**
     * Optional.
     * Bio of the user.
     *
     * type: `String`
     */
    public val bio: String? = null,
    /**
     * Optional.
     * Chat invite link that was used by the user to send the join request
     *
     * type: `ChatInviteLink`
     */
    @SerialName("invite_link")
    public val inviteLink: ChatInviteLink? = null,
)

/**
 * [ChatLocation](https://core.telegram.org/bots/api#chatlocation)
 *
 * Represents a location to which a chat is connected.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class ChatLocation(
    /**
     * The location to which the supergroup is connected.
     * Can't be a live location.
     *
     * type: `Location`
     */
    public val location: Location,
    /**
     * Location address; 1-64 characters, as defined by the chat owner
     *
     * type: `String`
     */
    public val address: String,
)

/**
 * [ChatMember](https://core.telegram.org/bots/api#chatmember)
 *
 * This object contains information about one member of a chat.
 *
 * Note: Because the property name of the class discriminator is not `type`,
 * [JsonClassDiscriminator] is specified,
 * and therefore only using [Json][kotlinx.serialization.json.Json] serialization is better.
 *
 * @see ChatMemberOwner
 * @see ChatMemberAdministrator
 * @see ChatMemberMember
 * @see ChatMemberRestricted
 * @see ChatMemberLeft
 * @see ChatMemberBanned
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator(ChatMember.CLASS_DISCRIMINATOR)
public sealed class ChatMember {
    public companion object {
        public const val CLASS_DISCRIMINATOR: String = "status"

        public const val OWNER_STATUS_NAME: String = "creator"
        public const val ADMINISTRATOR_STATUS_NAME: String = "administrator"
        public const val MEMBER_STATUS_NAME: String = "member"
        public const val RESTRICTED_STATUS_NAME: String = "restricted"
        public const val LEFT_STATUS_NAME: String = "left"
        public const val BANNED_STATUS_NAME: String = "kicked"
    }
}

/**
 * [ChatMemberAdministrator](https://core.telegram.org/bots/api#chatmemberadministrator)
 *
 * Represents a chat member that has some additional privileges.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
@SerialName(ChatMember.ADMINISTRATOR_STATUS_NAME)
public data class ChatMemberAdministrator(
    /**
     * Information about the user
     *
     * type: `User`
     */
    public val user: User,
    /**
     * True, if the bot is allowed to edit administrator privileges of that user
     *
     * type: `Boolean`
     */
    @SerialName("can_be_edited")
    public val canBeEdited: Boolean,
    /**
     * True, if the user's presence in the chat is hidden
     *
     * type: `Boolean`
     */
    @SerialName("is_anonymous")
    public val isAnonymous: Boolean,
    /**
     * True, if the administrator can access the chat event log, get boost list, see hidden
     * supergroup and channel members, report spam messages and ignore slow mode.
     * Implied by any other administrator privilege.
     *
     * type: `Boolean`
     */
    @SerialName("can_manage_chat")
    public val canManageChat: Boolean,
    /**
     * True, if the administrator can delete messages of other users
     *
     * type: `Boolean`
     */
    @SerialName("can_delete_messages")
    public val canDeleteMessages: Boolean,
    /**
     * True, if the administrator can manage video chats
     *
     * type: `Boolean`
     */
    @SerialName("can_manage_video_chats")
    public val canManageVideoChats: Boolean,
    /**
     * True, if the administrator can restrict, ban or unban chat members, or access supergroup
     * statistics
     *
     * type: `Boolean`
     */
    @SerialName("can_restrict_members")
    public val canRestrictMembers: Boolean,
    /**
     * True, if the administrator can add new administrators with a subset of their own privileges
     * or demote administrators that they have promoted, directly or indirectly (promoted by
     * administrators that were appointed by the user)
     *
     * type: `Boolean`
     */
    @SerialName("can_promote_members")
    public val canPromoteMembers: Boolean,
    /**
     * True, if the user is allowed to change the chat title, photo and other settings
     *
     * type: `Boolean`
     */
    @SerialName("can_change_info")
    public val canChangeInfo: Boolean,
    /**
     * True, if the user is allowed to invite new users to the chat
     *
     * type: `Boolean`
     */
    @SerialName("can_invite_users")
    public val canInviteUsers: Boolean,
    /**
     * True, if the administrator can post stories to the chat
     *
     * type: `Boolean`
     */
    @SerialName("can_post_stories")
    public val canPostStories: Boolean,
    /**
     * True, if the administrator can edit stories posted by other users
     *
     * type: `Boolean`
     */
    @SerialName("can_edit_stories")
    public val canEditStories: Boolean,
    /**
     * True, if the administrator can delete stories posted by other users
     *
     * type: `Boolean`
     */
    @SerialName("can_delete_stories")
    public val canDeleteStories: Boolean,
    /**
     * Optional.
     * True, if the administrator can post messages in the channel, or access channel statistics;
     * for channels only
     *
     * type: `Boolean`
     */
    @SerialName("can_post_messages")
    public val canPostMessages: Boolean? = null,
    /**
     * Optional.
     * True, if the administrator can edit messages of other users and can pin messages; for
     * channels only
     *
     * type: `Boolean`
     */
    @SerialName("can_edit_messages")
    public val canEditMessages: Boolean? = null,
    /**
     * Optional.
     * True, if the user is allowed to pin messages; for groups and supergroups only
     *
     * type: `Boolean`
     */
    @SerialName("can_pin_messages")
    public val canPinMessages: Boolean? = null,
    /**
     * Optional.
     * True, if the user is allowed to create, rename, close, and reopen forum topics; for
     * supergroups only
     *
     * type: `Boolean`
     */
    @SerialName("can_manage_topics")
    public val canManageTopics: Boolean? = null,
    /**
     * Optional.
     * Custom title for this user
     *
     * type: `String`
     */
    @SerialName("custom_title")
    public val customTitle: String? = null,
) : ChatMember()

/**
 * [ChatMemberBanned](https://core.telegram.org/bots/api#chatmemberbanned)
 *
 * Represents a chat member that was banned in the chat and can't return to the chat or view chat
 * messages.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
@SerialName(ChatMember.BANNED_STATUS_NAME)
public data class ChatMemberBanned(
    /**
     * Information about the user
     *
     * type: `User`
     */
    public val user: User,
    /**
     * Date when restrictions will be lifted for this user; Unix time.
     * If 0, then the user is banned forever
     *
     * type: `Integer`
     */
    @SerialName("until_date")
    public val untilDate: Int,
) : ChatMember()

/**
 * [ChatMemberLeft](https://core.telegram.org/bots/api#chatmemberleft)
 *
 * Represents a chat member that isn't currently a member of the chat, but may join it themselves.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
@SerialName(ChatMember.LEFT_STATUS_NAME)
public data class ChatMemberLeft(
    /**
     * Information about the user
     *
     * type: `User`
     */
    public val user: User,
) : ChatMember()

/**
 * [ChatMemberMember](https://core.telegram.org/bots/api#chatmembermember)
 *
 * Represents a chat member that has no additional privileges or restrictions.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
@SerialName(ChatMember.MEMBER_STATUS_NAME)
public data class ChatMemberMember(
    /**
     * Information about the user
     *
     * type: `User`
     */
    public val user: User,
)

/**
 * [ChatMemberOwner](https://core.telegram.org/bots/api#chatmemberowner)
 *
 * Represents a chat member that owns the chat and has all administrator privileges.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
@SerialName(ChatMember.OWNER_STATUS_NAME)
public data class ChatMemberOwner(
    /**
     * Information about the user
     *
     * type: `User`
     */
    public val user: User,
    /**
     * True, if the user's presence in the chat is hidden
     *
     * type: `Boolean`
     */
    @SerialName("is_anonymous")
    public val isAnonymous: Boolean,
    /**
     * Optional.
     * Custom title for this user
     *
     * type: `String`
     */
    @SerialName("custom_title")
    public val customTitle: String? = null,
) : ChatMember()

/**
 * [ChatMemberRestricted](https://core.telegram.org/bots/api#chatmemberrestricted)
 *
 * Represents a chat member that is under certain restrictions in the chat. Supergroups only.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
@SerialName(ChatMember.RESTRICTED_STATUS_NAME)
public data class ChatMemberRestricted(
    /**
     * Information about the user
     *
     * type: `User`
     */
    public val user: User,
    /**
     * True, if the user is a member of the chat at the moment of the request
     *
     * type: `Boolean`
     */
    @SerialName("is_member")
    public val isMember: Boolean,
    /**
     * True, if the user is allowed to send text messages, contacts, giveaways, giveaway winners,
     * invoices, locations and venues
     *
     * type: `Boolean`
     */
    @SerialName("can_send_messages")
    public val canSendMessages: Boolean,
    /**
     * True, if the user is allowed to send audios
     *
     * type: `Boolean`
     */
    @SerialName("can_send_audios")
    public val canSendAudios: Boolean,
    /**
     * True, if the user is allowed to send documents
     *
     * type: `Boolean`
     */
    @SerialName("can_send_documents")
    public val canSendDocuments: Boolean,
    /**
     * True, if the user is allowed to send photos
     *
     * type: `Boolean`
     */
    @SerialName("can_send_photos")
    public val canSendPhotos: Boolean,
    /**
     * True, if the user is allowed to send videos
     *
     * type: `Boolean`
     */
    @SerialName("can_send_videos")
    public val canSendVideos: Boolean,
    /**
     * True, if the user is allowed to send video notes
     *
     * type: `Boolean`
     */
    @SerialName("can_send_video_notes")
    public val canSendVideoNotes: Boolean,
    /**
     * True, if the user is allowed to send voice notes
     *
     * type: `Boolean`
     */
    @SerialName("can_send_voice_notes")
    public val canSendVoiceNotes: Boolean,
    /**
     * True, if the user is allowed to send polls
     *
     * type: `Boolean`
     */
    @SerialName("can_send_polls")
    public val canSendPolls: Boolean,
    /**
     * True, if the user is allowed to send animations, games, stickers and use inline bots
     *
     * type: `Boolean`
     */
    @SerialName("can_send_other_messages")
    public val canSendOtherMessages: Boolean,
    /**
     * True, if the user is allowed to add web page previews to their messages
     *
     * type: `Boolean`
     */
    @SerialName("can_add_web_page_previews")
    public val canAddWebPagePreviews: Boolean,
    /**
     * True, if the user is allowed to change the chat title, photo and other settings
     *
     * type: `Boolean`
     */
    @SerialName("can_change_info")
    public val canChangeInfo: Boolean,
    /**
     * True, if the user is allowed to invite new users to the chat
     *
     * type: `Boolean`
     */
    @SerialName("can_invite_users")
    public val canInviteUsers: Boolean,
    /**
     * True, if the user is allowed to pin messages
     *
     * type: `Boolean`
     */
    @SerialName("can_pin_messages")
    public val canPinMessages: Boolean,
    /**
     * True, if the user is allowed to create forum topics
     *
     * type: `Boolean`
     */
    @SerialName("can_manage_topics")
    public val canManageTopics: Boolean,
    /**
     * Date when restrictions will be lifted for this user; Unix time.
     * If 0, then the user is restricted forever
     *
     * type: `Integer`
     */
    @SerialName("until_date")
    public val untilDate: Int,
) : ChatMember()

/**
 * [ChatMemberUpdated](https://core.telegram.org/bots/api#chatmemberupdated)
 *
 * This object represents changes in the status of a chat member.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class ChatMemberUpdated(
    /**
     * Chat the user belongs to
     *
     * type: `Chat`
     */
    public val chat: Chat,
    /**
     * Performer of the action, which resulted in the change
     *
     * type: `User`
     */
    public val from: User,
    /**
     * Date the change was done in Unix time
     *
     * type: `Integer`
     */
    public val date: Int,
    /**
     * Previous information about the chat member
     *
     * type: `ChatMember`
     */
    @SerialName("old_chat_member")
    public val oldChatMember: ChatMember,
    /**
     * New information about the chat member
     *
     * type: `ChatMember`
     */
    @SerialName("new_chat_member")
    public val newChatMember: ChatMember,
    /**
     * Optional.
     * Chat invite link, which was used by the user to join the chat; for joining by invite link
     * events only.
     *
     * type: `ChatInviteLink`
     */
    @SerialName("invite_link")
    public val inviteLink: ChatInviteLink? = null,
    /**
     * Optional.
     * True, if the user joined the chat via a chat folder invite link
     *
     * type: `Boolean`
     */
    @SerialName("via_chat_folder_invite_link")
    public val viaChatFolderInviteLink: Boolean? = null,
)

/**
 * [ChatPermissions](https://core.telegram.org/bots/api#chatpermissions)
 *
 * Describes actions that a non-administrator user is allowed to take in a chat.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class ChatPermissions(
    /**
     * Optional.
     * True, if the user is allowed to send text messages, contacts, giveaways, giveaway winners,
     * invoices, locations and venues
     *
     * type: `Boolean`
     */
    @SerialName("can_send_messages")
    public val canSendMessages: Boolean? = null,
    /**
     * Optional.
     * True, if the user is allowed to send audios
     *
     * type: `Boolean`
     */
    @SerialName("can_send_audios")
    public val canSendAudios: Boolean? = null,
    /**
     * Optional.
     * True, if the user is allowed to send documents
     *
     * type: `Boolean`
     */
    @SerialName("can_send_documents")
    public val canSendDocuments: Boolean? = null,
    /**
     * Optional.
     * True, if the user is allowed to send photos
     *
     * type: `Boolean`
     */
    @SerialName("can_send_photos")
    public val canSendPhotos: Boolean? = null,
    /**
     * Optional.
     * True, if the user is allowed to send videos
     *
     * type: `Boolean`
     */
    @SerialName("can_send_videos")
    public val canSendVideos: Boolean? = null,
    /**
     * Optional.
     * True, if the user is allowed to send video notes
     *
     * type: `Boolean`
     */
    @SerialName("can_send_video_notes")
    public val canSendVideoNotes: Boolean? = null,
    /**
     * Optional.
     * True, if the user is allowed to send voice notes
     *
     * type: `Boolean`
     */
    @SerialName("can_send_voice_notes")
    public val canSendVoiceNotes: Boolean? = null,
    /**
     * Optional.
     * True, if the user is allowed to send polls
     *
     * type: `Boolean`
     */
    @SerialName("can_send_polls")
    public val canSendPolls: Boolean? = null,
    /**
     * Optional.
     * True, if the user is allowed to send animations, games, stickers and use inline bots
     *
     * type: `Boolean`
     */
    @SerialName("can_send_other_messages")
    public val canSendOtherMessages: Boolean? = null,
    /**
     * Optional.
     * True, if the user is allowed to add web page previews to their messages
     *
     * type: `Boolean`
     */
    @SerialName("can_add_web_page_previews")
    public val canAddWebPagePreviews: Boolean? = null,
    /**
     * Optional.
     * True, if the user is allowed to change the chat title, photo and other settings.
     * Ignored in public supergroups
     *
     * type: `Boolean`
     */
    @SerialName("can_change_info")
    public val canChangeInfo: Boolean? = null,
    /**
     * Optional.
     * True, if the user is allowed to invite new users to the chat
     *
     * type: `Boolean`
     */
    @SerialName("can_invite_users")
    public val canInviteUsers: Boolean? = null,
    /**
     * Optional.
     * True, if the user is allowed to pin messages.
     * Ignored in public supergroups
     *
     * type: `Boolean`
     */
    @SerialName("can_pin_messages")
    public val canPinMessages: Boolean? = null,
    /**
     * Optional.
     * True, if the user is allowed to create forum topics.
     * If omitted defaults to the value of can_pin_messages
     *
     * type: `Boolean`
     */
    @SerialName("can_manage_topics")
    public val canManageTopics: Boolean? = null,
)

/**
 * [ChatPhoto](https://core.telegram.org/bots/api#chatphoto)
 *
 * This object represents a chat photo.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class ChatPhoto(
    /**
     * File identifier of small (160x160) chat photo.
     * This file_id can be used only for photo download and only for as long as the photo is not
     * changed.
     *
     * type: `String`
     */
    @SerialName("small_file_id")
    public val smallFileId: String,
    /**
     * Unique file identifier of small (160x160) chat photo, which is supposed to be the same over
     * time and for different bots.
     * Can't be used to download or reuse the file.
     *
     * type: `String`
     */
    @SerialName("small_file_unique_id")
    public val smallFileUniqueId: String,
    /**
     * File identifier of big (640x640) chat photo.
     * This file_id can be used only for photo download and only for as long as the photo is not
     * changed.
     *
     * type: `String`
     */
    @SerialName("big_file_id")
    public val bigFileId: String,
    /**
     * Unique file identifier of big (640x640) chat photo, which is supposed to be the same over
     * time and for different bots.
     * Can't be used to download or reuse the file.
     *
     * type: `String`
     */
    @SerialName("big_file_unique_id")
    public val bigFileUniqueId: String,
)

/**
 * [ChatShared](https://core.telegram.org/bots/api#chatshared)
 *
 * This object contains information about the chat whose identifier was shared with the bot using a
 * KeyboardButtonRequestChat button.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class ChatShared(
    /**
     * Identifier of the request
     *
     * type: `Integer`
     */
    @SerialName("request_id")
    public val requestId: Int,
    /**
     * Identifier of the shared chat.
     * This number may have more than 32 significant bits and some programming languages may have
     * difficulty/silent defects in interpreting it.
     * But it has at most 52 significant bits, so a 64-bit integer or double-precision float type
     * are safe for storing this identifier.
     * The bot may not have access to the chat and could be unable to use this identifier, unless
     * the chat is already known to the bot by some other means.
     *
     * type: `Integer`
     */
    @SerialName("chat_id")
    public val chatId: Long,
)
