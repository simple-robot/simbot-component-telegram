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

import kotlinx.serialization.Serializable
import love.forte.simbot.message.PlainText
import love.forte.simbot.telegram.api.message.SendMessageApi
import love.forte.simbot.telegram.type.FormattingOption

/**
 * A [PlainText]'s [`parse_mode`](https://core.telegram.org/bots/api#formatting-options)
 * (see also [FormattingOption]).
 *
 * @see SendMessageApi.Body.parseMode
 * @see FormattingOption
 */
@Serializable
public data class TelegramTextParseMode(val parseMode: String) : TelegramMessageElement {
    public constructor(formattingOption: FormattingOption) : this(formattingOption.value)
}

// The Message.entities

