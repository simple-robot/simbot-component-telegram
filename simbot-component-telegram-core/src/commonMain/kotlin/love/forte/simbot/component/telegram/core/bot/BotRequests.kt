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

@file:JvmName("TelegramBotRequests")
@file:JvmMultifileClass

package love.forte.simbot.component.telegram.core.bot

import io.ktor.client.statement.*
import love.forte.simbot.telegram.api.TelegramApi
import love.forte.simbot.telegram.api.TelegramApiResult
import love.forte.simbot.telegram.api.requestData
import love.forte.simbot.telegram.api.requestResult
import love.forte.simbot.telegram.stdlib.bot.requestDataBy
import love.forte.simbot.telegram.stdlib.bot.requestRawBy
import love.forte.simbot.telegram.stdlib.bot.requestResultBy
import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName
import kotlin.jvm.JvmSynthetic

/**
 * Use [TelegramBot] to request the [TelegramApi] and get [HttpResponse].
 * @see TelegramApi.requestRawBy
 * @see TelegramBot.executeRaw
 */
@JvmSynthetic
public suspend fun TelegramApi<*>.requestRawBy(bot: TelegramBot): HttpResponse =
    requestRawBy(bot.source)

/**
 * Use [TelegramBot] to request the [TelegramApi] and get [TelegramApiResult] with [R].
 * @see TelegramApi.requestResult
 * @see TelegramBot.executeResult
 */
@JvmSynthetic
public suspend fun <R : Any> TelegramApi<R>.requestResultBy(bot: TelegramBot): TelegramApiResult<R> =
    requestResultBy(bot.source)

/**
 * Use [TelegramBot] to request the [TelegramApi] and get [R].
 * @see TelegramApi.requestData
 * @see TelegramBot.execute
 */
@JvmSynthetic
public suspend fun <R : Any> TelegramApi<R>.requestDataBy(bot: TelegramBot): R =
    requestDataBy(bot.source)
