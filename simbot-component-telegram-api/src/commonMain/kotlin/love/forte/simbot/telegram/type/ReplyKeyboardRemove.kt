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
 * [ReplyKeyboardRemove](https://core.telegram.org/bots/api#replykeyboardremove)
 *
 * Upon receiving a message with this object, Telegram clients will remove the current custom
 * keyboard and display the default letter-keyboard. By default, custom keyboards are displayed until a
 * new keyboard is sent by a bot. An exception is made for one-time keyboards that are hidden
 * immediately after the user presses a button (see ReplyKeyboardMarkup).
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class ReplyKeyboardRemove(
    /**
     * Requests clients to remove the custom keyboard (user will not be able to summon this
     * keyboard; if you want to hide the keyboard from sight but keep it accessible, use
     * one_time_keyboard in ReplyKeyboardMarkup)
     *
     * type: `True`
     */
    @SerialName("remove_keyboard")
    public val removeKeyboard: Boolean = false,
    /**
     * Optional. 
     * Use this parameter if you want to remove the keyboard for specific users only. 
     * Targets: 1) users that are @mentioned in the text of the Message object; 2) if the bot's
     * message is a reply to a message in the same chat and forum topic, sender of the original
     * message. 
     * Example: A user votes in a poll, bot returns confirmation message in reply to the vote and
     * removes the keyboard for that user, while still showing the keyboard with poll options to users
     * who haven't voted yet.
     *
     * type: `Boolean`
     */
    public val selective: Boolean? = null,
)
