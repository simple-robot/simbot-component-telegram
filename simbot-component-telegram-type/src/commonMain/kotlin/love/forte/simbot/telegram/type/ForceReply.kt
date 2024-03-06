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
 * [ForceReply](https://core.telegram.org/bots/api#forcereply)
 *
 * Upon receiving a message with this object, Telegram clients will display a reply interface to the
 * user (act as if the user has selected the bot's message and tapped 'Reply'). This can be extremely
 * useful if you want to create user-friendly step-by-step interfaces without having to sacrifice
 * privacy mode.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class ForceReply(
    /**
     * Shows reply interface to the user, as if they manually selected the bot's message and tapped
     * 'Reply'
     *
     * type: `True`
     */
    @SerialName("force_reply")
    public val forceReply: Boolean = false,
    /**
     * Optional.
     * The placeholder to be shown in the input field when the reply is active; 1-64 characters
     *
     * type: `String`
     */
    @SerialName("input_field_placeholder")
    public val inputFieldPlaceholder: String? = null,
    /**
     * Optional.
     * Use this parameter if you want to force reply from specific users only.
     * Targets: 1) users that are @mentioned in the text of the Message object; 2) if the bot's
     * message is a reply to a message in the same chat and forum topic, sender of the original
     * message.
     *
     * type: `Boolean`
     */
    public val selective: Boolean? = null,
)
