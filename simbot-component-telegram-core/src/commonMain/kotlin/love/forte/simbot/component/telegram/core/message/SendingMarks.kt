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

import love.forte.simbot.telegram.type.LinkPreviewOptions

internal const val PROTECT_CONTENT_MARK: Int = 1 shl 0
internal const val DISABLE_NOTIFICATION_MARK: Int = 1 shl 1


internal class SendingMarks(
    private val value: Int = 0,
    val linkPreviewOptions: LinkPreviewOptions? = null
) {
    val isProtectContent: Boolean
        get() = value and PROTECT_CONTENT_MARK != 0

    val isDisableNotification: Boolean
        get() = value and DISABLE_NOTIFICATION_MARK != 0

    companion object {
        val Default = SendingMarks()
    }
}
