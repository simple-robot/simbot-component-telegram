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
import love.forte.simbot.annotations.InternalSimbotAPI
import love.forte.simbot.suspendrunner.reserve.SuspendReserve
import love.forte.simbot.suspendrunner.reserve.suspendReserve
import love.forte.simbot.suspendrunner.runInNoScopeBlocking
import love.forte.simbot.telegram.api.*
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * [requestRawBy] in async.
 * @see TelegramApi.requestRaw
 */
@JvmOverloads
public fun TelegramApi<*>.requestByAsync(
    bot: Bot,
    scope: CoroutineScope? = null,
    context: CoroutineContext? = null,
): CompletableFuture<HttpResponse> =
    (scope ?: bot).future(context = context ?: EmptyCoroutineContext) { requestRawBy(bot) }


/**
 * [requestResultBy] in async.
 * @see TelegramApi.requestResult
 */
@JvmOverloads
public fun <R : Any> TelegramApi<R>.requestResultByAsync(
    bot: Bot,
    scope: CoroutineScope? = null,
    context: CoroutineContext? = null,
): CompletableFuture<TelegramApiResult<R>> =
    (scope ?: bot).future(context = context ?: EmptyCoroutineContext) { requestResultBy(bot) }


/**
 * [requestDataBy] in async.
 * @see TelegramApi.requestData
 */
@JvmOverloads
public fun <R : Any> TelegramApi<R>.requestDataByAsync(
    bot: Bot,
    scope: CoroutineScope? = null,
    context: CoroutineContext? = null,
): CompletableFuture<R> =
    (scope ?: bot).future(context = context ?: EmptyCoroutineContext) { requestDataBy(bot) }

/**
 * [requestRawBy] in blocking.
 * @see requestRawBy
 */
@JvmOverloads
public fun TelegramApi<*>.requestByBlocking(
    bot: Bot,
    context: CoroutineContext? = null,
): HttpResponse =
    runInNoScopeBlocking(context = context ?: EmptyCoroutineContext) { requestRawBy(bot) }

/**
 * [requestResultBy] in blocking.
 * @see requestResultBy
 */
@JvmOverloads
public fun <R : Any> TelegramApi<R>.requestResultByBlocking(
    bot: Bot,
    context: CoroutineContext? = null,
): TelegramApiResult<R> =
    runInNoScopeBlocking(context = context ?: EmptyCoroutineContext) { requestResultBy(bot) }

/**
 * [requestDataBy] in blocking.
 * @see requestDataBy
 */
@JvmOverloads
public fun <R : Any> TelegramApi<R>.requestDataByBlocking(
    bot: Bot,
    context: CoroutineContext? = null,
): R =
    runInNoScopeBlocking(context = context ?: EmptyCoroutineContext) { requestDataBy(bot) }


/**
 * [requestRawBy] in blocking.
 * @see requestRawBy
 */
@OptIn(InternalSimbotAPI::class)
@JvmOverloads
public fun TelegramApi<*>.requestByReserve(
    bot: Bot,
    scope: CoroutineScope? = null,
    context: CoroutineContext? = null,
): SuspendReserve<HttpResponse> =
    suspendReserve(
        scope = scope ?: bot,
        context = context ?: EmptyCoroutineContext,
    ) { requestRawBy(bot) }

/**
 * [requestResultBy] in blocking.
 * @see requestResultBy
 */
@OptIn(InternalSimbotAPI::class)
@JvmOverloads
public fun <R : Any> TelegramApi<R>.requestResultByReserve(
    bot: Bot,
    scope: CoroutineScope? = null,
    context: CoroutineContext? = null,
): SuspendReserve<TelegramApiResult<R>> =
    suspendReserve(
        scope = scope ?: bot,
        context = context ?: EmptyCoroutineContext,
    ) { requestResultBy(bot) }

/**
 * [requestDataBy] in blocking.
 * @see requestDataBy
 */
@OptIn(InternalSimbotAPI::class)
@JvmOverloads
public fun <R : Any> TelegramApi<R>.requestDataByReserve(
    bot: Bot,
    scope: CoroutineScope? = null,
    context: CoroutineContext? = null,
): SuspendReserve<R> =
    suspendReserve(
        scope = scope ?: bot,
        context = context ?: EmptyCoroutineContext,
    ) { requestDataBy(bot) }

