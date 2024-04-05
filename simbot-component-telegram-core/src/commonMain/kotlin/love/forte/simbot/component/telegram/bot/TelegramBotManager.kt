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

package love.forte.simbot.component.telegram.bot

import kotlinx.coroutines.Job
import love.forte.simbot.bot.*
import love.forte.simbot.common.coroutines.mergeWith
import love.forte.simbot.common.function.ConfigurerFunction
import love.forte.simbot.common.function.invokeBy
import love.forte.simbot.common.id.ID
import love.forte.simbot.common.id.NumericalID
import love.forte.simbot.component.NoSuchComponentException
import love.forte.simbot.component.telegram.bot.internal.TelegramBotManagerImpl
import love.forte.simbot.component.telegram.component.TelegramComponent
import love.forte.simbot.plugin.PluginConfigureContext
import love.forte.simbot.plugin.PluginFactory
import love.forte.simbot.telegram.stdlib.bot.Bot
import love.forte.simbot.telegram.type.User
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


/**
 * A [BotManager] for [TelegramBot].
 *
 * @author ForteScarlet
 */
public interface TelegramBotManager : BotManager {
    /**
     * Determine whether [configuration] is of type [SerializableTelegramBotConfiguration]
     */
    override fun configurable(configuration: SerializableBotConfiguration): Boolean =
        configuration is SerializableTelegramBotConfiguration

    /**
     * Register a bot with a serializable configuration [SerializableBotConfiguration].
     * The type of [configuration] must be [SerializableTelegramBotConfiguration].
     * There can only be one bot (alive) with the same token.
     *
     * @see configurable
     * @throws UnsupportedBotConfigurationException The type of [configuration] is not [SerializableTelegramBotConfiguration]
     * @throws BotRegisterFailureException If a bot with the same `token` has already been registered and is still alive
     */
    override fun register(configuration: SerializableBotConfiguration): TelegramBot {
        if (configuration !is SerializableTelegramBotConfiguration) {
            throw UnsupportedBotConfigurationException("Expect: love.forte.simbot.component.telegram.bot.SerializableTelegramBotConfiguration, " +
                    "but $configuration(${configuration::class})")
        }

        val ticket = configuration.ticket.toBotTicket()
        val botConfiguration = configuration.toBotConfiguration()

        return register(ticket, botConfiguration)
    }

    /**
     * Register a bot.
     * There can only be one bot (alive) with the same token.
     *
     * @throws BotRegisterFailureException If a bot with the same `token` has already been registered and is still alive
     */
    public fun register(ticket: Bot.Ticket, configuration: TelegramBotConfiguration): TelegramBot

    /**
     * Register a bot.
     * There can only be one bot (alive) with the same token.
     *
     * @throws BotRegisterFailureException If a bot with the same `token` has already been registered and is still alive
     */
    public fun register(ticket: Bot.Ticket, configuration: ConfigurerFunction<TelegramBotConfiguration>): TelegramBot =
        register(ticket, TelegramBotConfiguration().invokeBy(configuration))

    /**
     * Register a bot.
     * There can only be one bot (alive) with the same token.
     *
     * @throws BotRegisterFailureException If a bot with the same `token` has already been registered and is still alive
     */
    public fun register(token: String, configuration: TelegramBotConfiguration): TelegramBot =
        register(Bot.Ticket(token), configuration)

    /**
     * Register a bot.
     * There can only be one bot (alive) with the same token.
     *
     * @throws BotRegisterFailureException If a bot with the same `token` has already been registered and is still alive
     */
    public fun register(token: String, configuration: ConfigurerFunction<TelegramBotConfiguration>): TelegramBot =
        register(Bot.Ticket(token), configuration)

    override fun all(): Sequence<TelegramBot>

    /**
     * Find a matching bot by token or user id.
     * if [id] is a [NumericalID], think of it as a [User.id] for finding.
     * Otherwise, it is treated as a `token`, then converted to a Long and then treated as a [User.id].
     *
     * @see find
     * @throws NoSuchBotException If there is no matching bot
     */
    override fun get(id: ID): TelegramBot = find(id) ?: throw NoSuchBotException("TelegramBot(id=$id)")

    /**
     * Find a matching bot by token or user id.
     * if [id] is a [NumericalID], think of it as a [User.id] for finding.
     * Otherwise, it is treated as a `token`, then converted to a Long and then treated as a [User.id].
     *
     */
    override fun find(id: ID): TelegramBot?

    public companion object Factory : BotManagerFactory<TelegramBotManager, TelegramBotManagerConfiguration> {
        override val key: PluginFactory.Key = object : PluginFactory.Key {}

        override fun create(
            context: PluginConfigureContext,
            configurer: ConfigurerFunction<TelegramBotManagerConfiguration>
        ): TelegramBotManager {
            val component = context.components.find { it is TelegramComponent } as? TelegramComponent
                ?:  throw NoSuchComponentException("TelegramComponent(id=${TelegramComponent.ID_VALUE}) not found in current context. Maybe you didn't install it in application?")

            val configuration = TelegramBotManagerConfiguration().invokeBy(configurer)
            val coroutineContext = configuration.coroutineContext
            val applicationCoroutineContext = context.applicationConfiguration.coroutineContext

            val mergedContext = coroutineContext.mergeWith(applicationCoroutineContext)

            return TelegramBotManagerImpl(
                job = mergedContext[Job]!!,
                coroutineContext = mergedContext,
                component,
                context.eventDispatcher,
                configuration
            )
        }
    }

}

/**
 * Configuration for [TelegramBotManager]
 */
public class TelegramBotManagerConfiguration {
    public var coroutineContext: CoroutineContext = EmptyCoroutineContext

}
