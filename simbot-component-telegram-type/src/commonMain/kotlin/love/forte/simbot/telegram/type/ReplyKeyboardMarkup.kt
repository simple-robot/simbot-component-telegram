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
 * [ReplyKeyboardMarkup](https://core.telegram.org/bots/api#replykeyboardmarkup)
 *
 * This object represents a custom keyboard with reply options (see Introduction to bots for details
 * and examples).
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class ReplyKeyboardMarkup(
    /**
     * Array of button rows, each represented by an Array of KeyboardButton objects
     *
     * type: `Array of Array of KeyboardButton`
     */
    public val keyboard: List<List<KeyboardButton>> = emptyList(),
    /**
     * Optional.
     * Requests clients to always show the keyboard when the regular keyboard is hidden.
     * Defaults to false, in which case the custom keyboard can be hidden and opened with a keyboard
     * icon.
     *
     * type: `Boolean`
     */
    @SerialName("is_persistent")
    public val isPersistent: Boolean? = null,
    /**
     * Optional.
     * Requests clients to resize the keyboard vertically for optimal fit (e.g., make the keyboard
     * smaller if there are just two rows of buttons).
     * Defaults to false, in which case the custom keyboard is always of the same height as the
     * app's standard keyboard.
     *
     * type: `Boolean`
     */
    @SerialName("resize_keyboard")
    public val resizeKeyboard: Boolean? = null,
    /**
     * Optional.
     * Requests clients to hide the keyboard as soon as it's been used.
     * The keyboard will still be available, but clients will automatically display the usual
     * letter-keyboard in the chat - the user can press a special button in the input field to see the
     * custom keyboard again.
     * Defaults to false.
     *
     * type: `Boolean`
     */
    @SerialName("one_time_keyboard")
    public val oneTimeKeyboard: Boolean? = null,
    /**
     * Optional.
     * The placeholder to be shown in the input field when the keyboard is active; 1-64 characters
     *
     * type: `String`
     */
    @SerialName("input_field_placeholder")
    public val inputFieldPlaceholder: String? = null,
    /**
     * Optional.
     * Use this parameter if you want to show the keyboard to specific users only.
     * Targets: 1) users that are @mentioned in the text of the Message object; 2) if the bot's
     * message is a reply to a message in the same chat and forum topic, sender of the original
     * message.
     * Example: A user requests to change the bot's language, bot replies to the request with a
     * keyboard to select the new language.
     * Other users in the group don't see the keyboard.
     *
     * type: `Boolean`
     */
    public val selective: Boolean? = null,
)
