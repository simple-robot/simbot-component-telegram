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

@file:JvmName("Updates")
@file:JvmMultifileClass

package love.forte.simbot.telegram.api.update

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import love.forte.simbot.telegram.api.Telegram
import love.forte.simbot.telegram.api.update.Update.Companion.decodeFromRawJson
import love.forte.simbot.telegram.type.*
import love.forte.simbot.telegram.type.inline.ChosenInlineResult
import love.forte.simbot.telegram.type.inline.InlineQuery
import love.forte.simbot.telegram.type.payment.PreCheckoutQuery
import love.forte.simbot.telegram.type.payment.ShippingQuery
import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName
import kotlin.jvm.JvmStatic


/**
 * [Update](https://core.telegram.org/bots/api#update).
 *
 * This object represents an incoming update.
 * At most one of the optional parameters can be present in any given update.
 *
 * @see UpdateValues
 * @see decodeFromRawJson
 *
 * @author ForteScarlet
 */
@Serializable
public data class Update(
    /**
     * The update's unique identifier.
     *
     * Update identifiers start from a certain positive number and increase sequentially.
     *
     * This identifier becomes especially handy if you're using webhooks, since it allows you to ignore repeated updates or to restore the correct update sequence, should they get out of order.
     *
     * If there are no new updates for at least a week, then identifier of the next update will be chosen randomly instead of sequentially.
     */
    @SerialName("update_id")
    val updateId: Int,
    /**
     * Optional.
     *
     * New incoming message of any kind - text, photo, sticker, etc.
     */
    val message: Message? = null,
    /**
     * Optional.
     *
     * New version of a message that is known to the bot and was edited.
     *
     * This update may at times be triggered by changes to message fields that are either unavailable or not actively used by your bot.
     */
    @SerialName("edited_message")
    val editedMessage: Message? = null,
    /**
     * Optional.
     *
     * New incoming channel post of any kind - text, photo, sticker, etc.
     */
    @SerialName("channel_post")
    val channelPost: Message? = null,
    /**
     * Optional.
     *
     * New version of a channel post that is known to the bot and was edited.
     *
     * This update may at times be triggered by changes to message fields that are either unavailable or not actively used by your bot.
     */
    @SerialName("edited_channel_post")
    val editedChannelPost: Message? = null,
    /**
     * Optional.
     *
     * A reaction to a message was changed by a user.
     *
     * The bot must be an administrator in the chat and must explicitly specify "message_reaction" in the list of allowed_updates to receive these updates.
     *
     * The update isn't received for reactions set by bots.
     */
    @SerialName("message_reaction")
    val messageReaction: MessageReactionUpdated? = null,
    /**
     * Optional.
     *
     * Reactions to a message with anonymous reactions were changed.
     *
     * The bot must be an administrator in the chat and must explicitly specify "message_reaction_count" in the list of allowed_updates to receive these updates.
     *
     * The updates are grouped and can be sent with delay up to a few minutes.
     */
    @SerialName("message_reaction_count")
    val messageReactionCount: MessageReactionCountUpdated? = null,
    /**
     * Optional.
     *
     * New incoming inline query
     */
    @SerialName("inline_query")
    val inlineQuery: InlineQuery? = null,
    /**
     * Optional.
     *
     * The result of an inline query that was chosen by a user and sent to their chat partner.
     *
     * Please see our documentation on the feedback collecting for details on how to enable these updates for your bot.
     */
    @SerialName("chosen_inline_result")
    val chosenInlineResult: ChosenInlineResult? = null,
    /**
     * Optional.
     *
     * New incoming callback query
     */
    @SerialName("callback_query")
    val callbackQuery: CallbackQuery? = null,
    /**
     * Optional.
     *
     * New incoming shipping query.
     *
     * Only for invoices with flexible price
     */
    @SerialName("shipping_query")
    val shippingQuery: ShippingQuery? = null,
    /**
     * Optional.
     *
     * New incoming pre-checkout query.
     *
     * Contains full information about checkout
     */
    @SerialName("pre_checkout_query")
    val preCheckoutQuery: PreCheckoutQuery? = null,
    /**
     * Optional.
     *
     * New poll state.
     *
     * Bots receive only updates about manually stopped polls and polls, which are sent by the bot
     */
    val poll: Poll? = null,
    /**
     * Optional.
     *
     * A user changed their answer in a non-anonymous poll.
     *
     * Bots receive new votes only in polls that were sent by the bot itself.
     */
    @SerialName("poll_answer")
    val pollAnswer: PollAnswer? = null,
    /**
     * Optional.
     *
     * The bot's chat member status was updated in a chat.
     *
     * For private chats, this update is received only when the bot is blocked or unblocked by the user.
     */
    @SerialName("my_chat_member")
    val myChatMember: ChatMemberUpdated? = null,
    /**
     * Optional.
     *
     * A chat member's status was updated in a chat.
     *
     * The bot must be an administrator in the chat and must explicitly specify "chat_member" in the list of allowed_updates to receive these updates.
     */
    @SerialName("chat_member")
    val chatMember: ChatMemberUpdated? = null,
    /**
     * Optional.
     *
     * A request to join the chat has been sent.
     *
     * The bot must have the can_invite_users administrator right in the chat to receive these updates.
     */
    @SerialName("chat_join_request")
    val chatJoinRequest: ChatJoinRequest? = null,
    /**
     * Optional.
     *
     * A chat boost was added or changed.
     *
     * The bot must be an administrator in the chat to receive these updates.
     */
    @SerialName("chat_boost")
    val chatBoost: ChatBoostUpdated? = null,
    /**
     * Optional.
     *
     * A boost was removed from a chat.
     *
     * The bot must be an administrator in the chat to receive these updates.
     */
    @SerialName("removed_chat_boost")
    val removedChatBoost: ChatBoostRemoved? = null,
) {
    public companion object {
        /**
         * Decode a JSON string to [Update].
         *
         * @throws SerializationException see [Json.decodeFromString]
         * @throws IllegalArgumentException see [Json.decodeFromString]
         */
        @JvmStatic
        public fun decodeFromRawJson(content: String): Update =
            Telegram.DefaultJson.decodeFromString(serializer(), content)
    }
}


