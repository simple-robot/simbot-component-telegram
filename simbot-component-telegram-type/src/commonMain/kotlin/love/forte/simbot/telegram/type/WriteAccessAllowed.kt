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
 * [WriteAccessAllowed](https://core.telegram.org/bots/api#writeaccessallowed)
 *
 * This object represents a service message about a user allowing a bot to write messages after
 * adding it to the attachment menu, launching a Web App from a link, or accepting an explicit request
 * from a Web App sent by the method requestWriteAccess.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class WriteAccessAllowed(
    /**
     * Optional. 
     * True, if the access was granted after the user accepted an explicit request from a Web App
     * sent by the method requestWriteAccess
     *
     * type: `Boolean`
     */
    @SerialName("from_request")
    public val fromRequest: Boolean? = null,
    /**
     * Optional. 
     * Name of the Web App, if the access was granted when the Web App was launched from a link
     *
     * type: `String`
     */
    @SerialName("web_app_name")
    public val webAppName: String? = null,
    /**
     * Optional. 
     * True, if the access was granted when the bot was added to the attachment or side menu
     *
     * type: `Boolean`
     */
    @SerialName("from_attachment_menu")
    public val fromAttachmentMenu: Boolean? = null,
)
