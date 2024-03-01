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

package love.forte.simbot.telegram.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * The Telegram API response data.
 *
 * > The response contains a JSON object, which always has a Boolean field 'ok'
 * and may have an optional String field 'description' with a human-readable description of the result.
 * If 'ok' equals True, the request was successful and the result of the query can be found in the 'result' field.
 * In case of an unsuccessful request, 'ok' equals false and the error is explained in the 'description'.
 * An Integer 'error_code' field is also returned, but its contents are subject to change in the future.
 * Some errors may also have an optional field 'parameters' of the type `ResponseParameters`, which can help to automatically handle the error.
 *
 * @author ForteScarlet
 */
@Serializable
public data class TelegramApiResult<T : Any>(
    val ok: Boolean,
    /**
     * If 'ok' equals True, the request was successful and the result of the query can be found in the 'result' field.
     */
    val result: T? = null,
    /**
     * May have an optional String field 'description' with a human-readable description of the result.
     */
    val description: String? = null,
    /**
     * An Integer 'error_code' field is also returned, but its contents are subject to change in the future.
     */
    @SerialName("error_code")
    val errorCode: Int? = null,
    /**
     * Some errors may also have an optional field 'parameters' of the type `ResponseParameters`,
     * which can help to automatically handle the error.
     */
    val parameters: ResponseParameters? = null
)

/**
 * Get [TelegramApiResult.result], or throw [TelegramApiResultNotOkException]
 * if [TelegramApiResult.ok] = `false` or [TelegramApiResult.result] is null.
 */
public fun <T : Any> TelegramApiResult<T>.resultOrThrow(): T {
    if (!ok || result == null) {
        throw TelegramApiResultNotOkException(result = this)
    }

    return result
}


/**
 * Describes why a request was unsuccessful.
 */
@Serializable
public data class ResponseParameters(
    /**
     * *Optional*. The group has been migrated to a supergroup with the specified identifier.
     * This number may have more than 32 significant bits and some programming languages may have difficulty/silent defects in interpreting it.
     * But it has at most 52 significant bits, so a signed 64-bit integer or double-precision float type are safe for storing this identifier.
     */
    @SerialName("migrate_to_chat_id")
    val migrateToChatId: Long? = null,
    /**
     * *Optional*. In case of exceeding flood control,
     * the number of seconds left to wait before the request can be repeated
     */
    @SerialName("retry_after")
    val retryAfter: Int? = null,
)
