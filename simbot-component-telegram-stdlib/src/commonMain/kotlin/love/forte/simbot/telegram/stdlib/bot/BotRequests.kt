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

import love.forte.simbot.telegram.api.TelegramApi
import love.forte.simbot.telegram.api.requestData
import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName

public suspend fun <R : Any> TelegramApi<R>.requestDataBy(bot: Bot): R {
    return requestData(bot.apiClient, bot.ticket.token) // TODO bot.server
}
