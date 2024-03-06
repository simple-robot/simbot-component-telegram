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
 * [MenuButton](https://core.telegram.org/bots/api#menubutton)
 *
 * This object describes the bot's menu button in a private chat.
 *
 * If a menu button other than [MenuButtonDefault] is set for a private chat, then it is applied in
 * the chat. Otherwise the default menu button is applied. By default, the menu button opens the list
 * of bot commands.
 *
 * @see MenuButtonCommands
 * @see MenuButtonWebApp
 * @see MenuButtonDefault
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public sealed class MenuButton {
    public companion object {
        public const val COMMANDS_TYPE_NAME: String = "commands"
        public const val WEB_APP_TYPE_NAME: String = "web_app"
        public const val DEFAULT_TYPE_NAME: String = "default"
    }
}

/**
 * [MenuButtonCommands](https://core.telegram.org/bots/api#menubuttoncommands)
 *
 * Represents a menu button, which opens the bot's list of commands.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
@SerialName(MenuButton.COMMANDS_TYPE_NAME)
public data object MenuButtonCommands : MenuButton()

/**
 * [MenuButtonDefault](https://core.telegram.org/bots/api#menubuttondefault)
 *
 * Describes that no specific value for the menu button was set.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
@SerialName(MenuButton.DEFAULT_TYPE_NAME)
public data object MenuButtonDefault : MenuButton()

/**
 * [MenuButtonWebApp](https://core.telegram.org/bots/api#menubuttonwebapp)
 *
 * Represents a menu button, which launches a Web App.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
@SerialName(MenuButton.WEB_APP_TYPE_NAME)
public data class MenuButtonWebApp(
    /**
     * Text on the button
     *
     * type: `String`
     */
    public val text: String,
    /**
     * Description of the Web App that will be launched when the user presses the button.
     * The Web App will be able to send an arbitrary message on behalf of the user using the method
     * answerWebAppQuery.
     *
     * type: `WebAppInfo`
     */
    @SerialName("web_app")
    public val webApp: WebAppInfo,
) : MenuButton()
