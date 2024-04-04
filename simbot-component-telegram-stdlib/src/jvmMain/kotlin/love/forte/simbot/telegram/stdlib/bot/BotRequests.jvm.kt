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

@file:JvmName("BotRequests")
@file:JvmMultifileClass

package love.forte.simbot.telegram.stdlib.bot

import io.ktor.client.statement.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.future
import love.forte.simbot.suspendrunner.runInNoScopeBlocking
import love.forte.simbot.telegram.api.*
import java.util.concurrent.CompletableFuture

/**
 * [requestRawBy] in async.
 * @see TelegramApi.requestRaw
 */
@JvmSynthetic
@JvmOverloads
public fun TelegramApi<*>.requestByAsync(bot: Bot, scope: CoroutineScope = bot): CompletableFuture<HttpResponse> =
    scope.future { requestRawBy(bot) }


/**
 * [requestResultBy] in async.
 * @see TelegramApi.requestResult
 */
@JvmSynthetic
@JvmOverloads
public fun <R : Any> TelegramApi<R>.requestResultByAsync(
    bot: Bot,
    scope: CoroutineScope = bot
): CompletableFuture<TelegramApiResult<R>> =
    scope.future { requestResultBy(bot) }


/**
 * [requestDataBy] in async.
 * @see TelegramApi.requestData
 */
@JvmSynthetic
@JvmOverloads
public fun <R : Any> TelegramApi<R>.requestDataByAsync(bot: Bot, scope: CoroutineScope = bot): CompletableFuture<R> =
    scope.future { requestDataBy(bot) }

/**
 * [requestRawBy] in blocking.
 * @see requestRawBy
 */
@JvmSynthetic
public fun TelegramApi<*>.requestByBlocking(bot: Bot): HttpResponse =
    runInNoScopeBlocking { requestRawBy(bot) }

/**
 * [requestResultBy] in blocking.
 * @see requestResultBy
 */
@JvmSynthetic
public fun <R : Any> TelegramApi<R>.requestResultByBlocking(
    bot: Bot
): TelegramApiResult<R> =
    runInNoScopeBlocking { requestResultBy(bot) }

/**
 * [requestDataBy] in blocking.
 * @see requestDataBy
 */
@JvmSynthetic
public fun <R : Any> TelegramApi<R>.requestDataByBlocking(bot: Bot): R =
    runInNoScopeBlocking { requestDataBy(bot) }

