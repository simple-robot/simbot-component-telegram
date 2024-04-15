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

package love.forte.simbot.telegram.api.utils

import io.ktor.client.request.forms.*
import io.ktor.http.*
import love.forte.simbot.common.ktor.inputfile.InputFile
import kotlin.jvm.JvmName
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

/**
 * A [InputFile] implement with a simple [String value][value].
 */
public class StringValueInputFile internal constructor(
    public val value: String,
    private val defaultHeaders: Headers
) : InputFile {
    override fun includeTo(key: String, headers: Headers, formBuilder: FormBuilder) {
        formBuilder.append(key, value, defaultHeaders + headers)
    }

    override fun toFormPart(key: String, headers: Headers): FormPart<*> =
        FormPart(key, value, defaultHeaders + headers)

    public companion object {
        /**
         * Create a [StringValueInputFile].
         */
        @JvmName("of")
        @JvmStatic
        @JvmOverloads
        public fun create(value: String, defaultHeaders: Headers = Headers.Empty): InputFile =
            StringValueInputFile(value, defaultHeaders)
    }
}

private operator fun Headers.plus(other: Headers): Headers {
    if (isEmpty()) return other
    if (other.isEmpty()) return this

    return headers {
        appendAll(this@plus)
        appendAll(other)
    }
}
