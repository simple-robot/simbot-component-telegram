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
 * [LoginUrl](https://core.telegram.org/bots/api#loginurl)
 *
 * This object represents a parameter of the inline keyboard button used to automatically authorize
 * a user. Serves as a great replacement for the Telegram Login Widget when the user is coming from
 * Telegram. All the user needs to do is tap/click a button and confirm that they want to log in:
 *
 * Telegram apps support these buttons as of version 5.7.
 * Sample bot: @discussbot
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class LoginUrl(
    /**
     * An HTTPS URL to be opened with user authorization data added to the query string when the
     * button is pressed. 
     * If the user refuses to provide authorization data, the original URL without information about
     * the user will be opened. 
     * The data added is the same as described in Receiving authorization data. 
     * NOTE: You must always check the hash of the received data to verify the authentication and
     * the integrity of the data as described in Checking authorization.
     *
     * type: `String`
     */
    public val url: String,
    /**
     * Optional. 
     * New text of the button in forwarded messages.
     *
     * type: `String`
     */
    @SerialName("forward_text")
    public val forwardText: String? = null,
    /**
     * Optional. 
     * Username of a bot, which will be used for user authorization. 
     * See Setting up a bot for more details. 
     * If not specified, the current bot's username will be assumed. 
     * The url's domain must be the same as the domain linked with the bot. 
     * See Linking your domain to the bot for more details.
     *
     * type: `String`
     */
    @SerialName("bot_username")
    public val botUsername: String? = null,
    /**
     * Optional. 
     * Pass True to request the permission for your bot to send messages to the user.
     *
     * type: `Boolean`
     */
    @SerialName("request_write_access")
    public val requestWriteAccess: Boolean? = null,
)
