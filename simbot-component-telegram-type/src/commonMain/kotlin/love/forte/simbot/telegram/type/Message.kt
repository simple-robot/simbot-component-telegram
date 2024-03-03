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
 * [Message](https://core.telegram.org/bots/api#message)
 *
 * This object represents a message.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class Message(
    /**
     * Unique message identifier inside this chat
     *
     * type: `Integer`
     */
    @SerialName("message_id")
    public val messageId: Int,
    /**
     * Optional. 
     * Unique identifier of a message thread to which the message belongs; for supergroups only
     *
     * type: `Integer`
     */
    @SerialName("message_thread_id")
    public val messageThreadId: Int? = null,
    /**
     * Optional. 
     * Sender of the message; empty for messages sent to channels. 
     * For backward compatibility, the field contains a fake sender user in non-channel chats, if
     * the message was sent on behalf of a chat.
     *
     * type: `User`
     */
    public val from: love.forte.simbot.telegram.type.User? = null,
    /**
     * Optional. 
     * Sender of the message, sent on behalf of a chat. 
     * For example, the channel itself for channel posts, the supergroup itself for messages from
     * anonymous group administrators, the linked channel for messages automatically forwarded to the
     * discussion group. 
     * For backward compatibility, the field from contains a fake sender user in non-channel chats,
     * if the message was sent on behalf of a chat.
     *
     * type: `Chat`
     */
    @SerialName("sender_chat")
    public val senderChat: love.forte.simbot.telegram.type.Chat? = null,
    /**
     * Optional. 
     * If the sender of the message boosted the chat, the number of boosts added by the user
     *
     * type: `Integer`
     */
    @SerialName("sender_boost_count")
    public val senderBoostCount: Int? = null,
    /**
     * Date the message was sent in Unix time. 
     * It is always a positive number, representing a valid date.
     *
     * type: `Integer`
     */
    public val date: Int,
    /**
     * Chat the message belongs to
     *
     * type: `Chat`
     */
    public val chat: love.forte.simbot.telegram.type.Chat,
    /**
     * Optional. 
     * Information about the original message for forwarded messages
     *
     * type: `MessageOrigin`
     */
    @SerialName("forward_origin")
    public val forwardOrigin: love.forte.simbot.telegram.type.MessageOrigin? = null,
    /**
     * Optional. 
     * True, if the message is sent to a forum topic
     *
     * type: `True`
     */
    @SerialName("is_topic_message")
    public val isTopicMessage: Boolean? = true,
    /**
     * Optional. 
     * True, if the message is a channel post that was automatically forwarded to the connected
     * discussion group
     *
     * type: `True`
     */
    @SerialName("is_automatic_forward")
    public val isAutomaticForward: Boolean? = true,
    /**
     * Optional. 
     * For replies in the same chat and message thread, the original message. 
     * Note that the Message object in this field will not contain further reply_to_message fields
     * even if it itself is a reply.
     *
     * type: `Message`
     */
    @SerialName("reply_to_message")
    public val replyToMessage: love.forte.simbot.telegram.type.Message? = null,
    /**
     * Optional. 
     * Information about the message that is being replied to, which may come from another chat or
     * forum topic
     *
     * type: `ExternalReplyInfo`
     */
    @SerialName("external_reply")
    public val externalReply: love.forte.simbot.telegram.type.ExternalReplyInfo? = null,
    /**
     * Optional. 
     * For replies that quote part of the original message, the quoted part of the message
     *
     * type: `TextQuote`
     */
    public val quote: love.forte.simbot.telegram.type.TextQuote? = null,
    /**
     * Optional. 
     * For replies to a story, the original story
     *
     * type: `Story`
     */
    @SerialName("reply_to_story")
    public val replyToStory: love.forte.simbot.telegram.type.Story? = null,
    /**
     * Optional. 
     * Bot through which the message was sent
     *
     * type: `User`
     */
    @SerialName("via_bot")
    public val viaBot: love.forte.simbot.telegram.type.User? = null,
    /**
     * Optional. 
     * Date the message was last edited in Unix time
     *
     * type: `Integer`
     */
    @SerialName("edit_date")
    public val editDate: Int? = null,
    /**
     * Optional. 
     * True, if the message can't be forwarded
     *
     * type: `True`
     */
    @SerialName("has_protected_content")
    public val hasProtectedContent: Boolean? = true,
    /**
     * Optional. 
     * The unique identifier of a media message group this message belongs to
     *
     * type: `String`
     */
    @SerialName("media_group_id")
    public val mediaGroupId: String? = null,
    /**
     * Optional. 
     * Signature of the post author for messages in channels, or the custom title of an anonymous
     * group administrator
     *
     * type: `String`
     */
    @SerialName("author_signature")
    public val authorSignature: String? = null,
    /**
     * Optional. 
     * For text messages, the actual UTF-8 text of the message
     *
     * type: `String`
     */
    public val text: String? = null,
    /**
     * Optional. 
     * For text messages, special entities like usernames, URLs, bot commands, etc. 
     * that appear in the text
     *
     * type: `Array of MessageEntity`
     */
    public val entities: List<love.forte.simbot.telegram.type.MessageEntity>? = null,
    /**
     * Optional. 
     * Options used for link preview generation for the message, if it is a text message and link
     * preview options were changed
     *
     * type: `LinkPreviewOptions`
     */
    @SerialName("link_preview_options")
    public val linkPreviewOptions: love.forte.simbot.telegram.type.LinkPreviewOptions? = null,
    /**
     * Optional. 
     * Message is an animation, information about the animation. 
     * For backward compatibility, when this field is set, the document field will also be set
     *
     * type: `Animation`
     */
    public val animation: love.forte.simbot.telegram.type.Animation? = null,
    /**
     * Optional. 
     * Message is an audio file, information about the file
     *
     * type: `Audio`
     */
    public val audio: love.forte.simbot.telegram.type.Audio? = null,
    /**
     * Optional. 
     * Message is a general file, information about the file
     *
     * type: `Document`
     */
    public val document: love.forte.simbot.telegram.type.Document? = null,
    /**
     * Optional. 
     * Message is a photo, available sizes of the photo
     *
     * type: `Array of PhotoSize`
     */
    public val photo: List<love.forte.simbot.telegram.type.PhotoSize>? = null,
    /**
     * Optional. 
     * Message is a sticker, information about the sticker
     *
     * type: `Sticker`
     */
    public val sticker: love.forte.simbot.telegram.type.sticker.Sticker? = null,
    /**
     * Optional. 
     * Message is a forwarded story
     *
     * type: `Story`
     */
    public val story: love.forte.simbot.telegram.type.Story? = null,
    /**
     * Optional. 
     * Message is a video, information about the video
     *
     * type: `Video`
     */
    public val video: love.forte.simbot.telegram.type.Video? = null,
    /**
     * Optional. 
     * Message is a video note, information about the video message
     *
     * type: `VideoNote`
     */
    @SerialName("video_note")
    public val videoNote: love.forte.simbot.telegram.type.VideoNote? = null,
    /**
     * Optional. 
     * Message is a voice message, information about the file
     *
     * type: `Voice`
     */
    public val voice: love.forte.simbot.telegram.type.Voice? = null,
    /**
     * Optional. 
     * Caption for the animation, audio, document, photo, video or voice
     *
     * type: `String`
     */
    public val caption: String? = null,
    /**
     * Optional. 
     * For messages with a caption, special entities like usernames, URLs, bot commands, etc. 
     * that appear in the caption
     *
     * type: `Array of MessageEntity`
     */
    @SerialName("caption_entities")
    public val captionEntities: List<love.forte.simbot.telegram.type.MessageEntity>? = null,
    /**
     * Optional. 
     * True, if the message media is covered by a spoiler animation
     *
     * type: `True`
     */
    @SerialName("has_media_spoiler")
    public val hasMediaSpoiler: Boolean? = true,
    /**
     * Optional. 
     * Message is a shared contact, information about the contact
     *
     * type: `Contact`
     */
    public val contact: love.forte.simbot.telegram.type.Contact? = null,
    /**
     * Optional. 
     * Message is a dice with random value
     *
     * type: `Dice`
     */
    public val dice: love.forte.simbot.telegram.type.Dice? = null,
    /**
     * Optional. 
     * Message is a game, information about the game. 
     * More about games »
     *
     * type: `Game`
     */
    public val game: love.forte.simbot.telegram.type.game.Game? = null,
    /**
     * Optional. 
     * Message is a native poll, information about the poll
     *
     * type: `Poll`
     */
    public val poll: love.forte.simbot.telegram.type.Poll? = null,
    /**
     * Optional. 
     * Message is a venue, information about the venue. 
     * For backward compatibility, when this field is set, the location field will also be set
     *
     * type: `Venue`
     */
    public val venue: love.forte.simbot.telegram.type.Venue? = null,
    /**
     * Optional. 
     * Message is a shared location, information about the location
     *
     * type: `Location`
     */
    public val location: love.forte.simbot.telegram.type.Location? = null,
    /**
     * Optional. 
     * New members that were added to the group or supergroup and information about them (the bot
     * itself may be one of these members)
     *
     * type: `Array of User`
     */
    @SerialName("new_chat_members")
    public val newChatMembers: List<love.forte.simbot.telegram.type.User>? = null,
    /**
     * Optional. 
     * A member was removed from the group, information about them (this member may be the bot
     * itself)
     *
     * type: `User`
     */
    @SerialName("left_chat_member")
    public val leftChatMember: love.forte.simbot.telegram.type.User? = null,
    /**
     * Optional. 
     * A chat title was changed to this value
     *
     * type: `String`
     */
    @SerialName("new_chat_title")
    public val newChatTitle: String? = null,
    /**
     * Optional. 
     * A chat photo was change to this value
     *
     * type: `Array of PhotoSize`
     */
    @SerialName("new_chat_photo")
    public val newChatPhoto: List<love.forte.simbot.telegram.type.PhotoSize>? = null,
    /**
     * Optional. 
     * Service message: the chat photo was deleted
     *
     * type: `True`
     */
    @SerialName("delete_chat_photo")
    public val deleteChatPhoto: Boolean? = true,
    /**
     * Optional. 
     * Service message: the group has been created
     *
     * type: `True`
     */
    @SerialName("group_chat_created")
    public val groupChatCreated: Boolean? = true,
    /**
     * Optional. 
     * Service message: the supergroup has been created. 
     * This field can't be received in a message coming through updates, because bot can't be a
     * member of a supergroup when it is created. 
     * It can only be found in reply_to_message if someone replies to a very first message in a
     * directly created supergroup.
     *
     * type: `True`
     */
    @SerialName("supergroup_chat_created")
    public val supergroupChatCreated: Boolean? = true,
    /**
     * Optional. 
     * Service message: the channel has been created. 
     * This field can't be received in a message coming through updates, because bot can't be a
     * member of a channel when it is created. 
     * It can only be found in reply_to_message if someone replies to a very first message in a
     * channel.
     *
     * type: `True`
     */
    @SerialName("channel_chat_created")
    public val channelChatCreated: Boolean? = true,
    /**
     * Optional. 
     * Service message: auto-delete timer settings changed in the chat
     *
     * type: `MessageAutoDeleteTimerChanged`
     */
    @SerialName("message_auto_delete_timer_changed")
    public val messageAutoDeleteTimerChanged: love.forte.simbot.telegram.type.MessageAutoDeleteTimerChanged? = null,
    /**
     * Optional. 
     * The group has been migrated to a supergroup with the specified identifier. 
     * This number may have more than 32 significant bits and some programming languages may have
     * difficulty/silent defects in interpreting it. 
     * But it has at most 52 significant bits, so a signed 64-bit integer or double-precision float
     * type are safe for storing this identifier.
     *
     * type: `Integer`
     */
    @SerialName("migrate_to_chat_id")
    public val migrateToChatId: Long? = null,
    /**
     * Optional. 
     * The supergroup has been migrated from a group with the specified identifier. 
     * This number may have more than 32 significant bits and some programming languages may have
     * difficulty/silent defects in interpreting it. 
     * But it has at most 52 significant bits, so a signed 64-bit integer or double-precision float
     * type are safe for storing this identifier.
     *
     * type: `Integer`
     */
    @SerialName("migrate_from_chat_id")
    public val migrateFromChatId: Long? = null,
    /**
     * Optional. 
     * Specified message was pinned. 
     * Note that the Message object in this field will not contain further reply_to_message fields
     * even if it itself is a reply.
     *
     * type: `MaybeInaccessibleMessage`
     */
    @SerialName("pinned_message")
    public val pinnedMessage: love.forte.simbot.telegram.type.MaybeInaccessibleMessage? = null,
    /**
     * Optional. 
     * Message is an invoice for a payment, information about the invoice. 
     * More about payments »
     *
     * type: `Invoice`
     */
    public val invoice: love.forte.simbot.telegram.type.payment.Invoice? = null,
    /**
     * Optional. 
     * Message is a service message about a successful payment, information about the payment. 
     * More about payments »
     *
     * type: `SuccessfulPayment`
     */
    @SerialName("successful_payment")
    public val successfulPayment: love.forte.simbot.telegram.type.payment.SuccessfulPayment? = null,
    /**
     * Optional. 
     * Service message: users were shared with the bot
     *
     * type: `UsersShared`
     */
    @SerialName("users_shared")
    public val usersShared: love.forte.simbot.telegram.type.UsersShared? = null,
    /**
     * Optional. 
     * Service message: a chat was shared with the bot
     *
     * type: `ChatShared`
     */
    @SerialName("chat_shared")
    public val chatShared: love.forte.simbot.telegram.type.ChatShared? = null,
    /**
     * Optional. 
     * The domain name of the website on which the user has logged in. 
     * More about Telegram Login »
     *
     * type: `String`
     */
    @SerialName("connected_website")
    public val connectedWebsite: String? = null,
    /**
     * Optional. 
     * Service message: the user allowed the bot to write messages after adding it to the attachment
     * or side menu, launching a Web App from a link, or accepting an explicit request from a Web App
     * sent by the method requestWriteAccess
     *
     * type: `WriteAccessAllowed`
     */
    @SerialName("write_access_allowed")
    public val writeAccessAllowed: love.forte.simbot.telegram.type.WriteAccessAllowed? = null,
    /**
     * Optional. 
     * Telegram Passport data
     *
     * type: `PassportData`
     */
    @SerialName("passport_data")
    public val passportData: love.forte.simbot.telegram.type.passport.PassportData? = null,
    /**
     * Optional. 
     * Service message. 
     * A user in the chat triggered another user's proximity alert while sharing Live Location.
     *
     * type: `ProximityAlertTriggered`
     */
    @SerialName("proximity_alert_triggered")
    public val proximityAlertTriggered: love.forte.simbot.telegram.type.ProximityAlertTriggered? = null,
    /**
     * Optional. 
     * Service message: user boosted the chat
     *
     * type: `ChatBoostAdded`
     */
    @SerialName("boost_added")
    public val boostAdded: love.forte.simbot.telegram.type.ChatBoostAdded? = null,
    /**
     * Optional. 
     * Service message: forum topic created
     *
     * type: `ForumTopicCreated`
     */
    @SerialName("forum_topic_created")
    public val forumTopicCreated: love.forte.simbot.telegram.type.ForumTopicCreated? = null,
    /**
     * Optional. 
     * Service message: forum topic edited
     *
     * type: `ForumTopicEdited`
     */
    @SerialName("forum_topic_edited")
    public val forumTopicEdited: love.forte.simbot.telegram.type.ForumTopicEdited? = null,
    /**
     * Optional. 
     * Service message: forum topic closed
     *
     * type: `ForumTopicClosed`
     */
    @SerialName("forum_topic_closed")
    public val forumTopicClosed: love.forte.simbot.telegram.type.ForumTopicClosed? = null,
    /**
     * Optional. 
     * Service message: forum topic reopened
     *
     * type: `ForumTopicReopened`
     */
    @SerialName("forum_topic_reopened")
    public val forumTopicReopened: love.forte.simbot.telegram.type.ForumTopicReopened? = null,
    /**
     * Optional. 
     * Service message: the 'General' forum topic hidden
     *
     * type: `GeneralForumTopicHidden`
     */
    @SerialName("general_forum_topic_hidden")
    public val generalForumTopicHidden: love.forte.simbot.telegram.type.GeneralForumTopicHidden? = null,
    /**
     * Optional. 
     * Service message: the 'General' forum topic unhidden
     *
     * type: `GeneralForumTopicUnhidden`
     */
    @SerialName("general_forum_topic_unhidden")
    public val generalForumTopicUnhidden: love.forte.simbot.telegram.type.GeneralForumTopicUnhidden? = null,
    /**
     * Optional. 
     * Service message: a scheduled giveaway was created
     *
     * type: `GiveawayCreated`
     */
    @SerialName("giveaway_created")
    public val giveawayCreated: love.forte.simbot.telegram.type.GiveawayCreated? = null,
    /**
     * Optional. 
     * The message is a scheduled giveaway message
     *
     * type: `Giveaway`
     */
    public val giveaway: love.forte.simbot.telegram.type.Giveaway? = null,
    /**
     * Optional. 
     * A giveaway with public winners was completed
     *
     * type: `GiveawayWinners`
     */
    @SerialName("giveaway_winners")
    public val giveawayWinners: love.forte.simbot.telegram.type.GiveawayWinners? = null,
    /**
     * Optional. 
     * Service message: a giveaway without public winners was completed
     *
     * type: `GiveawayCompleted`
     */
    @SerialName("giveaway_completed")
    public val giveawayCompleted: love.forte.simbot.telegram.type.GiveawayCompleted? = null,
    /**
     * Optional. 
     * Service message: video chat scheduled
     *
     * type: `VideoChatScheduled`
     */
    @SerialName("video_chat_scheduled")
    public val videoChatScheduled: love.forte.simbot.telegram.type.VideoChatScheduled? = null,
    /**
     * Optional. 
     * Service message: video chat started
     *
     * type: `VideoChatStarted`
     */
    @SerialName("video_chat_started")
    public val videoChatStarted: love.forte.simbot.telegram.type.VideoChatStarted? = null,
    /**
     * Optional. 
     * Service message: video chat ended
     *
     * type: `VideoChatEnded`
     */
    @SerialName("video_chat_ended")
    public val videoChatEnded: love.forte.simbot.telegram.type.VideoChatEnded? = null,
    /**
     * Optional. 
     * Service message: new participants invited to a video chat
     *
     * type: `VideoChatParticipantsInvited`
     */
    @SerialName("video_chat_participants_invited")
    public val videoChatParticipantsInvited: love.forte.simbot.telegram.type.VideoChatParticipantsInvited? = null,
    /**
     * Optional. 
     * Service message: data sent by a Web App
     *
     * type: `WebAppData`
     */
    @SerialName("web_app_data")
    public val webAppData: love.forte.simbot.telegram.type.WebAppData? = null,
    /**
     * Optional. 
     * Inline keyboard attached to the message. 
     * login_url buttons are represented as ordinary url buttons.
     *
     * type: `InlineKeyboardMarkup`
     */
    @SerialName("reply_markup")
    public val replyMarkup: love.forte.simbot.telegram.type.InlineKeyboardMarkup? = null,
)

/**
 * [MessageAutoDeleteTimerChanged](https://core.telegram.org/bots/api#messageautodeletetimerchanged)
 *
 * This object represents a service message about a change in auto-delete timer settings.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class MessageAutoDeleteTimerChanged(
    /**
     * New auto-delete time for messages in the chat; in seconds
     *
     * type: `Integer`
     */
    @SerialName("message_auto_delete_time")
    public val messageAutoDeleteTime: Int,
)

/**
 * [MessageEntity](https://core.telegram.org/bots/api#messageentity)
 *
 * This object represents one special entity in a text message. For example, hashtags, usernames,
 * URLs, etc.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class MessageEntity(
    /**
     * Type of the entity. 
     * Currently, can be “mention” (@username), “hashtag” (#hashtag), “cashtag” ($USD),
     * “bot_command” (/start@jobs_bot), “url” (https://telegram.org), “email”
     * (do-not-reply@telegram.org), “phone_number” (+1-212-555-0123), “bold” (bold text), “italic”
     * (italic text), “underline” (underlined text), “strikethrough” (strikethrough text), “spoiler”
     * (spoiler message), “blockquote” (block quotation), “code” (monowidth string), “pre” (monowidth
     * block), “text_link” (for clickable text URLs), “text_mention” (for users without usernames),
     * “custom_emoji” (for inline custom emoji stickers)
     *
     * type: `String`
     */
    public val type: String,
    /**
     * Offset in UTF-16 code units to the start of the entity
     *
     * type: `Integer`
     */
    public val offset: Int,
    /**
     * Length of the entity in UTF-16 code units
     *
     * type: `Integer`
     */
    public val length: Int,
    /**
     * Optional. 
     * For “text_link” only, URL that will be opened after user taps on the text
     *
     * type: `String`
     */
    public val url: String? = null,
    /**
     * Optional. 
     * For “text_mention” only, the mentioned user
     *
     * type: `User`
     */
    public val user: love.forte.simbot.telegram.type.User? = null,
    /**
     * Optional. 
     * For “pre” only, the programming language of the entity text
     *
     * type: `String`
     */
    public val language: String? = null,
    /**
     * Optional. 
     * For “custom_emoji” only, unique identifier of the custom emoji. 
     * Use getCustomEmojiStickers to get full information about the sticker
     *
     * type: `String`
     */
    @SerialName("custom_emoji_id")
    public val customEmojiId: String? = null,
)

/**
 * [MessageId](https://core.telegram.org/bots/api#messageid)
 *
 * This object represents a unique message identifier.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class MessageId(
    /**
     * Unique message identifier
     *
     * type: `Integer`
     */
    @SerialName("message_id")
    public val messageId: Int,
)

/**
 * [MessageOrigin](https://core.telegram.org/bots/api#messageorigin)
 *
 * This object describes the origin of a message. It can be one of
 * MessageOriginUser MessageOriginHiddenUser MessageOriginChat MessageOriginChannel
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public class MessageOrigin {
    // TODO Empty class?

}

/**
 * [MessageOriginChannel](https://core.telegram.org/bots/api#messageoriginchannel)
 *
 * The message was originally sent to a channel chat.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class MessageOriginChannel(
    /**
     * Type of the message origin, always “channel”
     *
     * type: `String`
     */
    public val type: String,
    /**
     * Date the message was sent originally in Unix time
     *
     * type: `Integer`
     */
    public val date: Int,
    /**
     * Channel chat to which the message was originally sent
     *
     * type: `Chat`
     */
    public val chat: love.forte.simbot.telegram.type.Chat,
    /**
     * Unique message identifier inside the chat
     *
     * type: `Integer`
     */
    @SerialName("message_id")
    public val messageId: Int,
    /**
     * Optional. 
     * Signature of the original post author
     *
     * type: `String`
     */
    @SerialName("author_signature")
    public val authorSignature: String? = null,
)

/**
 * [MessageOriginChat](https://core.telegram.org/bots/api#messageoriginchat)
 *
 * The message was originally sent on behalf of a chat to a group chat.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class MessageOriginChat(
    /**
     * Type of the message origin, always “chat”
     *
     * type: `String`
     */
    public val type: String,
    /**
     * Date the message was sent originally in Unix time
     *
     * type: `Integer`
     */
    public val date: Int,
    /**
     * Chat that sent the message originally
     *
     * type: `Chat`
     */
    @SerialName("sender_chat")
    public val senderChat: love.forte.simbot.telegram.type.Chat,
    /**
     * Optional. 
     * For messages originally sent by an anonymous chat administrator, original message author
     * signature
     *
     * type: `String`
     */
    @SerialName("author_signature")
    public val authorSignature: String? = null,
)

/**
 * [MessageOriginHiddenUser](https://core.telegram.org/bots/api#messageoriginhiddenuser)
 *
 * The message was originally sent by an unknown user.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class MessageOriginHiddenUser(
    /**
     * Type of the message origin, always “hidden_user”
     *
     * type: `String`
     */
    public val type: String,
    /**
     * Date the message was sent originally in Unix time
     *
     * type: `Integer`
     */
    public val date: Int,
    /**
     * Name of the user that sent the message originally
     *
     * type: `String`
     */
    @SerialName("sender_user_name")
    public val senderUserName: String,
)

/**
 * [MessageOriginUser](https://core.telegram.org/bots/api#messageoriginuser)
 *
 * The message was originally sent by a known user.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class MessageOriginUser(
    /**
     * Type of the message origin, always “user”
     *
     * type: `String`
     */
    public val type: String,
    /**
     * Date the message was sent originally in Unix time
     *
     * type: `Integer`
     */
    public val date: Int,
    /**
     * User that sent the message originally
     *
     * type: `User`
     */
    @SerialName("sender_user")
    public val senderUser: love.forte.simbot.telegram.type.User,
)

/**
 * [MessageReactionCountUpdated](https://core.telegram.org/bots/api#messagereactioncountupdated)
 *
 * This object represents reaction changes on a message with anonymous reactions.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class MessageReactionCountUpdated(
    /**
     * The chat containing the message
     *
     * type: `Chat`
     */
    public val chat: love.forte.simbot.telegram.type.Chat,
    /**
     * Unique message identifier inside the chat
     *
     * type: `Integer`
     */
    @SerialName("message_id")
    public val messageId: Int,
    /**
     * Date of the change in Unix time
     *
     * type: `Integer`
     */
    public val date: Int,
    /**
     * List of reactions that are present on the message
     *
     * type: `Array of ReactionCount`
     */
    public val reactions: List<love.forte.simbot.telegram.type.ReactionCount> = emptyList(),
)

/**
 * [MessageReactionUpdated](https://core.telegram.org/bots/api#messagereactionupdated)
 *
 * This object represents a change of a reaction on a message performed by a user.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class MessageReactionUpdated(
    /**
     * The chat containing the message the user reacted to
     *
     * type: `Chat`
     */
    public val chat: love.forte.simbot.telegram.type.Chat,
    /**
     * Unique identifier of the message inside the chat
     *
     * type: `Integer`
     */
    @SerialName("message_id")
    public val messageId: Int,
    /**
     * Optional. 
     * The user that changed the reaction, if the user isn't anonymous
     *
     * type: `User`
     */
    public val user: love.forte.simbot.telegram.type.User? = null,
    /**
     * Optional. 
     * The chat on behalf of which the reaction was changed, if the user is anonymous
     *
     * type: `Chat`
     */
    @SerialName("actor_chat")
    public val actorChat: love.forte.simbot.telegram.type.Chat? = null,
    /**
     * Date of the change in Unix time
     *
     * type: `Integer`
     */
    public val date: Int,
    /**
     * Previous list of reaction types that were set by the user
     *
     * type: `Array of ReactionType`
     */
    @SerialName("old_reaction")
    public val oldReaction: List<love.forte.simbot.telegram.type.ReactionType> = emptyList(),
    /**
     * New list of reaction types that have been set by the user
     *
     * type: `Array of ReactionType`
     */
    @SerialName("new_reaction")
    public val newReaction: List<love.forte.simbot.telegram.type.ReactionType> = emptyList(),
)
