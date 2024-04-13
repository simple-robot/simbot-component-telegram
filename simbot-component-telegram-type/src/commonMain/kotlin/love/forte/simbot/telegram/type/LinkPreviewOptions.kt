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
import kotlin.jvm.JvmOverloads

/**
 * [LinkPreviewOptions](https://core.telegram.org/bots/api#linkpreviewoptions)
 *
 * Describes the options used for link preview generation.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class LinkPreviewOptions(
    /**
     * Optional.
     * True, if the link preview is disabled
     *
     * type: `Boolean`
     */
    @SerialName("is_disabled")
    public val isDisabled: Boolean? = null,
    /**
     * Optional.
     * URL to use for the link preview.
     * If empty, then the first URL found in the message text will be used
     *
     * type: `String`
     */
    public val url: String? = null,
    /**
     * Optional.
     * True, if the media in the link preview is supposed to be shrunk; ignored if the URL isn't
     * explicitly specified or media size change isn't supported for the preview
     *
     * type: `Boolean`
     */
    @SerialName("prefer_small_media")
    public val preferSmallMedia: Boolean? = null,
    /**
     * Optional.
     * True, if the media in the link preview is supposed to be enlarged; ignored if the URL isn't
     * explicitly specified or media size change isn't supported for the preview
     *
     * type: `Boolean`
     */
    @SerialName("prefer_large_media")
    public val preferLargeMedia: Boolean? = null,
    /**
     * Optional.
     * True, if the link preview must be shown above the message text; otherwise, the link preview
     * will be shown below the message text
     *
     * type: `Boolean`
     */
    @SerialName("show_above_text")
    public val showAboveText: Boolean? = null,
)


/**
 * A simple builder for [LinkPreviewOptions].
 */
public class LinkPreviewOptionsBuilder {
    /**
     * @see LinkPreviewOptions.isDisabled
     */
    public var isDisabled: Boolean? = null

    /**
     * @see isDisabled
     */
    @JvmOverloads
    public fun disable(value: Boolean = true): LinkPreviewOptionsBuilder = apply {
        isDisabled = value
    }

    /**
     * @see LinkPreviewOptions.url
     */
    public var url: String? = null

    /**
     * @see LinkPreviewOptions.preferSmallMedia
     */
    public var preferSmallMedia: Boolean? = null

    /**
     * @see preferSmallMedia
     */
    @JvmOverloads
    public fun preferSmallMedia(value: Boolean = true): LinkPreviewOptionsBuilder = apply {
        preferSmallMedia = value
    }

    /**
     * @see LinkPreviewOptions.preferLargeMedia
     */
    public var preferLargeMedia: Boolean? = null

    /**
     * @see preferLargeMedia
     */
    @JvmOverloads
    public fun preferLargeMedia(value: Boolean = true): LinkPreviewOptionsBuilder = apply {
        preferLargeMedia = value
    }

    /**
     * @see LinkPreviewOptions.showAboveText
     */
    public var showAboveText: Boolean? = null

    /**
     * @see showAboveText
     */
    @JvmOverloads
    public fun showAboveText(value: Boolean = true): LinkPreviewOptionsBuilder = apply {
        showAboveText = value
    }

    /**
     * Build a [LinkPreviewOptions].
     */
    public fun build(): LinkPreviewOptions =
        LinkPreviewOptions(
            isDisabled = isDisabled,
            url = url,
            preferSmallMedia = preferSmallMedia,
            preferLargeMedia = preferLargeMedia,
            showAboveText = showAboveText,
        )


}
