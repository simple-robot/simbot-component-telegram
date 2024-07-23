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

package love.forte.simbot.component.telegram.core.bot.internal

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import love.forte.simbot.bot.ConflictBotException
import love.forte.simbot.bot.JobBasedBotManager
import love.forte.simbot.common.collection.computeValue
import love.forte.simbot.common.collection.concurrentMutableMap
import love.forte.simbot.common.collection.removeValue
import love.forte.simbot.common.coroutines.mergeWith
import love.forte.simbot.common.id.ID
import love.forte.simbot.common.id.literal
import love.forte.simbot.component.telegram.core.bot.TelegramBot
import love.forte.simbot.component.telegram.core.bot.TelegramBotConfiguration
import love.forte.simbot.component.telegram.core.bot.TelegramBotManager
import love.forte.simbot.component.telegram.core.bot.TelegramBotManagerConfiguration
import love.forte.simbot.component.telegram.core.component.TelegramComponent
import love.forte.simbot.component.telegram.core.event.internal.TelegramBotRegisteredEventImpl
import love.forte.simbot.event.EventDispatcher
import love.forte.simbot.telegram.stdlib.bot.Bot
import love.forte.simbot.telegram.stdlib.bot.BotFactory
import kotlin.coroutines.CoroutineContext


/**
 *
 * @author ForteScarlet
 */
internal class TelegramBotManagerImpl(
    override val job: Job,
    private val coroutineContext: CoroutineContext,
    private val component: TelegramComponent,
    private val eventDispatcher: EventDispatcher,
    private val configuration: TelegramBotManagerConfiguration,
) : TelegramBotManager, JobBasedBotManager() {

    private val botsWithTokenKey = concurrentMutableMap<String, TelegramBotImpl>()

    // id token cache
    // private val idTokenMap = concurrentMutableMap<Long, String>()

    override fun register(ticket: Bot.Ticket, configuration: TelegramBotConfiguration): TelegramBot {
        val token = ticket.token

        fun createBot(): TelegramBotImpl {
            val context = configuration.coroutineContext.mergeWith(coroutineContext)
            val job = context[Job]!!

            return TelegramBotImpl(
                job = job,
                coroutineContext = context,
                component = component,
                source = BotFactory.create(ticket, configuration.botConfiguration),
                configuration = configuration,
                eventDispatcher = eventDispatcher
            )
        }


        val newBot = botsWithTokenKey.computeValue(token) { k, old ->
            if (old != null && old.isActive) throw ConflictBotException("TelegramBot with token $k")
            createBot()
        }!!

        newBot.onCompletion {
            botsWithTokenKey.removeValue(token) { newBot }
        }

        eventDispatcher.pushRegisteredEvent(newBot)

        return newBot
    }

    override fun all(): Sequence<TelegramBot> =
        botsWithTokenKey.values.asSequence()

    override fun find(id: ID): TelegramBot? {
        val token = id.literal
        return botsWithTokenKey[token]
    }
}

internal fun EventDispatcher.pushRegisteredEvent(
    bot: TelegramBotImpl
): Job {
    val e = TelegramBotRegisteredEventImpl(bot)
    return pushEventWithBot(e, bot).launchIn(bot)
}
