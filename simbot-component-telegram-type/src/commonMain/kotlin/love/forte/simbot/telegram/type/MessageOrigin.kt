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
 * [MessageOrigin](https://core.telegram.org/bots/api#messageorigin)
 *
 * This object describes the origin of a message.
 *
 * @see MessageOriginUser
 * @see MessageOriginHiddenUser
 * @see MessageOriginChat
 * @see MessageOriginChannel
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public sealed class MessageOrigin {
    public companion object {
        public const val USER_TYPE_NAME: String = "user"
        public const val HIDDEN_USER_TYPE_NAME: String = "hidden_user"
        public const val CHAT_TYPE_NAME: String = "chat"
        public const val CHANNEL_TYPE_NAME: String = "channel"
    }
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
@SerialName(MessageOrigin.CHANNEL_TYPE_NAME)
public data class MessageOriginChannel(
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
    public val chat: Chat,
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
) : MessageOrigin()

/**
 * [MessageOriginChat](https://core.telegram.org/bots/api#messageoriginchat)
 *
 * The message was originally sent on behalf of a chat to a group chat.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
@SerialName(MessageOrigin.CHAT_TYPE_NAME)
public data class MessageOriginChat(
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
    public val senderChat: Chat,
    /**
     * Optional.
     * For messages originally sent by an anonymous chat administrator, original message author
     * signature
     *
     * type: `String`
     */
    @SerialName("author_signature")
    public val authorSignature: String? = null,
) : MessageOrigin()

/**
 * [MessageOriginHiddenUser](https://core.telegram.org/bots/api#messageoriginhiddenuser)
 *
 * The message was originally sent by an unknown user.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
@SerialName(MessageOrigin.HIDDEN_USER_TYPE_NAME)
public data class MessageOriginHiddenUser(
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
) : MessageOrigin()

/**
 * [MessageOriginUser](https://core.telegram.org/bots/api#messageoriginuser)
 *
 * The message was originally sent by a known user.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
@SerialName(MessageOrigin.USER_TYPE_NAME)
public data class MessageOriginUser(
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
    public val senderUser: User,
) : MessageOrigin()
