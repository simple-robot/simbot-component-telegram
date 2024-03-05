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
 * [KeyboardButton](https://core.telegram.org/bots/api#keyboardbutton)
 *
 * This object represents one button of the reply keyboard. For simple text buttons, String can be
 * used instead of this object to specify the button text. The optional fields web_app, request_users,
 * request_chat, request_contact, request_location, and request_poll are mutually exclusive.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class KeyboardButton(
    /**
     * Text of the button. 
     * If none of the optional fields are used, it will be sent as a message when the button is
     * pressed
     *
     * type: `String`
     */
    public val text: String,
    /**
     * Optional. 
     * If specified, pressing the button will open a list of suitable users. 
     * Identifiers of selected users will be sent to the bot in a “users_shared” service message. 
     * Available in private chats only.
     *
     * type: `KeyboardButtonRequestUsers`
     */
    @SerialName("request_users")
    public val requestUsers: KeyboardButtonRequestUsers? = null,
    /**
     * Optional. 
     * If specified, pressing the button will open a list of suitable chats. 
     * Tapping on a chat will send its identifier to the bot in a “chat_shared” service message. 
     * Available in private chats only.
     *
     * type: `KeyboardButtonRequestChat`
     */
    @SerialName("request_chat")
    public val requestChat: KeyboardButtonRequestChat? = null,
    /**
     * Optional. 
     * If True, the user's phone number will be sent as a contact when the button is pressed. 
     * Available in private chats only.
     *
     * type: `Boolean`
     */
    @SerialName("request_contact")
    public val requestContact: Boolean? = null,
    /**
     * Optional. 
     * If True, the user's current location will be sent when the button is pressed. 
     * Available in private chats only.
     *
     * type: `Boolean`
     */
    @SerialName("request_location")
    public val requestLocation: Boolean? = null,
    /**
     * Optional. 
     * If specified, the user will be asked to create a poll and send it to the bot when the button
     * is pressed. 
     * Available in private chats only.
     *
     * type: `KeyboardButtonPollType`
     */
    @SerialName("request_poll")
    public val requestPoll: KeyboardButtonPollType? = null,
    /**
     * Optional. 
     * If specified, the described Web App will be launched when the button is pressed. 
     * The Web App will be able to send a “web_app_data” service message. 
     * Available in private chats only.
     *
     * type: `WebAppInfo`
     */
    @SerialName("web_app")
    public val webApp: WebAppInfo? = null,
)

/**
 * [KeyboardButtonPollType](https://core.telegram.org/bots/api#keyboardbuttonpolltype)
 *
 * This object represents type of a poll, which is allowed to be created and sent when the
 * corresponding button is pressed.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class KeyboardButtonPollType(
    /**
     * Optional. 
     * If quiz is passed, the user will be allowed to create only polls in the quiz mode. 
     * If regular is passed, only regular polls will be allowed. 
     * Otherwise, the user will be allowed to create a poll of any type.
     *
     * type: `String`
     */
    public val type: String? = null,
)

/**
 * [KeyboardButtonRequestChat](https://core.telegram.org/bots/api#keyboardbuttonrequestchat)
 *
 * This object defines the criteria used to request a suitable chat. The identifier of the selected
 * chat will be shared with the bot when the corresponding button is pressed. More about requesting
 * chats »
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class KeyboardButtonRequestChat(
    /**
     * Signed 32-bit identifier of the request, which will be received back in the ChatShared
     * object. 
     * Must be unique within the message
     *
     * type: `Integer`
     */
    @SerialName("request_id")
    public val requestId: Int,
    /**
     * Pass True to request a channel chat, pass False to request a group or a supergroup chat.
     *
     * type: `Boolean`
     */
    @SerialName("chat_is_channel")
    public val chatIsChannel: Boolean,
    /**
     * Optional. 
     * Pass True to request a forum supergroup, pass False to request a non-forum chat. 
     * If not specified, no additional restrictions are applied.
     *
     * type: `Boolean`
     */
    @SerialName("chat_is_forum")
    public val chatIsForum: Boolean? = null,
    /**
     * Optional. 
     * Pass True to request a supergroup or a channel with a username, pass False to request a chat
     * without a username. 
     * If not specified, no additional restrictions are applied.
     *
     * type: `Boolean`
     */
    @SerialName("chat_has_username")
    public val chatHasUsername: Boolean? = null,
    /**
     * Optional. 
     * Pass True to request a chat owned by the user. 
     * Otherwise, no additional restrictions are applied.
     *
     * type: `Boolean`
     */
    @SerialName("chat_is_created")
    public val chatIsCreated: Boolean? = null,
    /**
     * Optional. 
     * A JSON-serialized object listing the required administrator rights of the user in the chat. 
     * The rights must be a superset of bot_administrator_rights. 
     * If not specified, no additional restrictions are applied.
     *
     * type: `ChatAdministratorRights`
     */
    @SerialName("user_administrator_rights")
    public val userAdministratorRights: ChatAdministratorRights? = null,
    /**
     * Optional. 
     * A JSON-serialized object listing the required administrator rights of the bot in the chat. 
     * The rights must be a subset of user_administrator_rights. 
     * If not specified, no additional restrictions are applied.
     *
     * type: `ChatAdministratorRights`
     */
    @SerialName("bot_administrator_rights")
    public val botAdministratorRights: ChatAdministratorRights? = null,
    /**
     * Optional. 
     * Pass True to request a chat with the bot as a member. 
     * Otherwise, no additional restrictions are applied.
     *
     * type: `Boolean`
     */
    @SerialName("bot_is_member")
    public val botIsMember: Boolean? = null,
)

/**
 * [KeyboardButtonRequestUsers](https://core.telegram.org/bots/api#keyboardbuttonrequestusers)
 *
 * This object defines the criteria used to request suitable users. The identifiers of the selected
 * users will be shared with the bot when the corresponding button is pressed. More about requesting
 * users »
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class KeyboardButtonRequestUsers(
    /**
     * Signed 32-bit identifier of the request that will be received back in the UsersShared object.
     * 
     * Must be unique within the message
     *
     * type: `Integer`
     */
    @SerialName("request_id")
    public val requestId: Int,
    /**
     * Optional. 
     * Pass True to request bots, pass False to request regular users. 
     * If not specified, no additional restrictions are applied.
     *
     * type: `Boolean`
     */
    @SerialName("user_is_bot")
    public val userIsBot: Boolean? = null,
    /**
     * Optional. 
     * Pass True to request premium users, pass False to request non-premium users. 
     * If not specified, no additional restrictions are applied.
     *
     * type: `Boolean`
     */
    @SerialName("user_is_premium")
    public val userIsPremium: Boolean? = null,
    /**
     * Optional. 
     * The maximum number of users to be selected; 1-10. 
     * Defaults to 1.
     *
     * type: `Integer`
     */
    @SerialName("max_quantity")
    public val maxQuantity: Int? = null,
)
