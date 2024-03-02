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
 * [WebAppData](https://core.telegram.org/bots/api#webappdata)
 *
 * Describes data sent from a Web App to the bot.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class WebAppData(
    /**
     * The data. 
     * Be aware that a bad client can send arbitrary data in this field.
     *
     * type: `String`
     */
    public val `data`: String,
    /**
     * Text of the web_app keyboard button from which the Web App was opened. 
     * Be aware that a bad client can send arbitrary data in this field.
     *
     * type: `String`
     */
    @SerialName("button_text")
    public val buttonText: String,
)
