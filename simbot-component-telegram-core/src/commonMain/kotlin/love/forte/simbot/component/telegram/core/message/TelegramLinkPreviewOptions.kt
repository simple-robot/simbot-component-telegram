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

package love.forte.simbot.component.telegram.core.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import love.forte.simbot.telegram.type.LinkPreviewOptions

// TODO

/**
 * A Telegram [LinkPreviewOptions].
 *
 * @author ForteScarlet
 */
@Serializable
@SerialName("telegram.m.link_preview_options")
public data class TelegramLinkPreviewOptions(public val source: LinkPreviewOptions) :
    TelegramMessageElement {
    /**
     * @see LinkPreviewOptions.isDisabled
     */
    public val isDisabled: Boolean?
        get() = source.isDisabled

    /**
     * @see LinkPreviewOptions.url
     */
    public val url: String?
        get() = source.url

    /**
     * @see LinkPreviewOptions.preferSmallMedia
     */
    public val preferSmallMedia: Boolean?
        get() = source.preferSmallMedia

    /**
     * @see LinkPreviewOptions.preferLargeMedia
     */
    public val preferLargeMedia: Boolean?
        get() = source.preferLargeMedia

    /**
     * @see LinkPreviewOptions.showAboveText
     */
    public val showAboveText: Boolean?
        get() = source.showAboveText

}
