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

package love.forte.simbot.component.telegram.bot.internal

import kotlinx.coroutines.Job
import love.forte.simbot.bot.JobBasedBotManager
import love.forte.simbot.common.collection.concurrentMutableMap
import love.forte.simbot.common.id.ID
import love.forte.simbot.common.id.NumericalID
import love.forte.simbot.common.id.literal
import love.forte.simbot.component.telegram.bot.TelegramBot
import love.forte.simbot.component.telegram.bot.TelegramBotConfiguration
import love.forte.simbot.component.telegram.bot.TelegramBotManager
import love.forte.simbot.component.telegram.bot.TelegramBotManagerConfiguration
import love.forte.simbot.component.telegram.component.TelegramComponent
import love.forte.simbot.telegram.stdlib.bot.Bot
import kotlin.coroutines.CoroutineContext


/**
 *
 * @author ForteScarlet
 */
internal class TelegramBotManagerImpl(
    override val job: Job,
    private val coroutineContext: CoroutineContext,
    private val component: TelegramComponent,
    private val configuration: TelegramBotManagerConfiguration,
) : TelegramBotManager, JobBasedBotManager() {

    private val botsWithTokenKey = concurrentMutableMap<String, TelegramBot>() // TODO BotImpl
    // id token cache
    private val idTokenMap = concurrentMutableMap<Long, String>() // TODO BotImpl

    override fun register(ticket: Bot.Ticket, configuration: TelegramBotConfiguration): TelegramBot {
        TODO("Not yet implemented")
    }

    override fun all(): Sequence<TelegramBot> =
        botsWithTokenKey.values.asSequence()

    override fun find(id: ID): TelegramBot? {
        fun findByLongId(lid: Long): TelegramBot? =
            idTokenMap[lid]?.let { token -> botsWithTokenKey[token] }?.takeIf { it.isActive }

        if (id is NumericalID) {
            return findByLongId(id.toLong())
        }

        val token = id.literal
        val foundByToken = botsWithTokenKey[token]
        if (foundByToken != null) return foundByToken

        return token.toLongOrNull()?.let { longId -> findByLongId(longId) }
    }
}
