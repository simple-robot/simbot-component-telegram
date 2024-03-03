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
 * [SwitchInlineQueryChosenChat](https://core.telegram.org/bots/api#switchinlinequerychosenchat)
 *
 * This object represents an inline button that switches the current user to inline mode in a chosen
 * chat, with an optional default inline query.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class SwitchInlineQueryChosenChat(
    /**
     * Optional. 
     * The default inline query to be inserted in the input field. 
     * If left empty, only the bot's username will be inserted
     *
     * type: `String`
     */
    public val query: String? = null,
    /**
     * Optional. 
     * True, if private chats with users can be chosen
     *
     * type: `Boolean`
     */
    @SerialName("allow_user_chats")
    public val allowUserChats: Boolean? = null,
    /**
     * Optional. 
     * True, if private chats with bots can be chosen
     *
     * type: `Boolean`
     */
    @SerialName("allow_bot_chats")
    public val allowBotChats: Boolean? = null,
    /**
     * Optional. 
     * True, if group and supergroup chats can be chosen
     *
     * type: `Boolean`
     */
    @SerialName("allow_group_chats")
    public val allowGroupChats: Boolean? = null,
    /**
     * Optional. 
     * True, if channel chats can be chosen
     *
     * type: `Boolean`
     */
    @SerialName("allow_channel_chats")
    public val allowChannelChats: Boolean? = null,
)
