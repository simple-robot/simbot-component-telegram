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

@file:JvmName("FormattingOptions")
@file:JvmMultifileClass

package love.forte.simbot.telegram.type

import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName

// 如果直接使用 FormattingOptions.xxx_STYLE, 会得到:
// Companion object of enum class 'FormattingOptions' is uninitialized here

/**
 * [MarkdownV2 style](https://core.telegram.org/bots/api#markdownv2-style)
 */
public const val FORMATTING_OPTION_MARKDOWN_V2_STYLE: String = "MarkdownV2"

/**
 * [HTML style](https://core.telegram.org/bots/api#html-style)
 */
public const val FORMATTING_OPTION_HTML_STYLE: String = "HTML"

/**
 * [Markdown style](https://core.telegram.org/bots/api#markdown-style)
 */
public const val FORMATTING_OPTION_MARKDOWN_STYLE: String = "Markdown"

/**
 * All known [formatting options](https://core.telegram.org/bots/api#formatting-options).
 *
 * @see [Formatting options](https://core.telegram.org/bots/api#formatting-options)
 */
public enum class FormattingOption(public val value: String) {
    /**
     * [MarkdownV2 style](https://core.telegram.org/bots/api#markdownv2-style)
     */
    MARKDOWN_V2(FORMATTING_OPTION_MARKDOWN_V2_STYLE),

    /**
     * [HTML style](https://core.telegram.org/bots/api#html-style)
     */
    HTML(FORMATTING_OPTION_HTML_STYLE),

    /**
     * [Markdown style](https://core.telegram.org/bots/api#markdown-style)
     */
    MARKDOWN(FORMATTING_OPTION_MARKDOWN_STYLE),
    ;
}
