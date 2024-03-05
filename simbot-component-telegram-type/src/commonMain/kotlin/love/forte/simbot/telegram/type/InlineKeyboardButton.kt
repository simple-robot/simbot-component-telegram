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
 * [InlineKeyboardButton](https://core.telegram.org/bots/api#inlinekeyboardbutton)
 *
 * This object represents one button of an inline keyboard. You must use exactly one of the optional
 * fields.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class InlineKeyboardButton(
    /**
     * Label text on the button
     *
     * type: `String`
     */
    public val text: String,
    /**
     * Optional. 
     * HTTP or tg:// URL to be opened when the button is pressed. 
     * Links tg://user?id=<user_id> can be used to mention a user by their identifier without using
     * a username, if this is allowed by their privacy settings.
     *
     * type: `String`
     */
    public val url: String? = null,
    /**
     * Optional. 
     * Data to be sent in a callback query to the bot when button is pressed, 1-64 bytes
     *
     * type: `String`
     */
    @SerialName("callback_data")
    public val callbackData: String? = null,
    /**
     * Optional. 
     * Description of the Web App that will be launched when the user presses the button. 
     * The Web App will be able to send an arbitrary message on behalf of the user using the method
     * answerWebAppQuery. 
     * Available only in private chats between a user and the bot.
     *
     * type: `WebAppInfo`
     */
    @SerialName("web_app")
    public val webApp: WebAppInfo? = null,
    /**
     * Optional. 
     * An HTTPS URL used to automatically authorize the user. 
     * Can be used as a replacement for the Telegram Login Widget.
     *
     * type: `LoginUrl`
     */
    @SerialName("login_url")
    public val loginUrl: LoginUrl? = null,
    /**
     * Optional. 
     * If set, pressing the button will prompt the user to select one of their chats, open that chat
     * and insert the bot's username and the specified inline query in the input field. 
     * May be empty, in which case just the bot's username will be inserted.
     *
     * type: `String`
     */
    @SerialName("switch_inline_query")
    public val switchInlineQuery: String? = null,
    /**
     * Optional. 
     * If set, pressing the button will insert the bot's username and the specified inline query in
     * the current chat's input field. 
     * May be empty, in which case only the bot's username will be inserted. 
     * This offers a quick way for the user to open your bot in inline mode in the same chat - good
     * for selecting something from multiple options.
     *
     * type: `String`
     */
    @SerialName("switch_inline_query_current_chat")
    public val switchInlineQueryCurrentChat: String? = null,
    /**
     * Optional. 
     * If set, pressing the button will prompt the user to select one of their chats of the
     * specified type, open that chat and insert the bot's username and the specified inline query in
     * the input field
     *
     * type: `SwitchInlineQueryChosenChat`
     */
    @SerialName("switch_inline_query_chosen_chat")
    public val switchInlineQueryChosenChat: SwitchInlineQueryChosenChat? = null,
    /**
     * Optional. 
     * Description of the game that will be launched when the user presses the button. 
     * NOTE: This type of button must always be the first button in the first row.
     *
     * type: `CallbackGame`
     */
    @SerialName("callback_game")
    public val callbackGame: love.forte.simbot.telegram.type.game.CallbackGame? = null,
    /**
     * Optional. 
     * Specify True, to send a Pay button. 
     * NOTE: This type of button must always be the first button in the first row and can only be
     * used in invoice messages.
     *
     * type: `Boolean`
     */
    public val pay: Boolean? = null,
)
