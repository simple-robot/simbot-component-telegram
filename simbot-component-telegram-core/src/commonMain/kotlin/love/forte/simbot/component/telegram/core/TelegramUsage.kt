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

@file:JvmName("TelegramUsages")

package love.forte.simbot.component.telegram.core

import love.forte.simbot.application.Application
import love.forte.simbot.application.ApplicationFactoryConfigurer
import love.forte.simbot.common.function.ConfigurerFunction
import love.forte.simbot.common.function.invokeBy
import love.forte.simbot.common.function.invokeWith
import love.forte.simbot.component.ComponentInstaller
import love.forte.simbot.component.Components
import love.forte.simbot.component.find
import love.forte.simbot.component.get
import love.forte.simbot.component.telegram.core.bot.TelegramBotManager
import love.forte.simbot.component.telegram.core.bot.TelegramBotManagerConfiguration
import love.forte.simbot.component.telegram.core.component.TelegramComponent
import love.forte.simbot.component.telegram.core.component.TelegramComponentConfiguration
import love.forte.simbot.plugin.PluginInstaller
import love.forte.simbot.plugin.Plugins
import love.forte.simbot.plugin.find
import love.forte.simbot.plugin.get
import kotlin.jvm.JvmName

/**
 * Install [TelegramComponent] and [TelegramBotManager] into [C] (e.g. [ApplicationFactoryConfigurer]).
 *
 * ```Kotlin
 * val app = launchSimpleApplication {
 *     useTelegram()
 *     // or:
 *     useTelegram {
 *         component {
 *             // config TelegramComponent...
 *         }
 *         botManager {
 *             // config TelegramBotManager...
 *         }
 *     }
 * }
 * ```
 *
 */
public fun <C> C.useTelegram(block: ConfigurerFunction<TelegramUsageBuilder>? = null)
        where C : ComponentInstaller, C : PluginInstaller {
    val builder = TelegramUsageBuilder().invokeBy(block)
    install(TelegramComponent) {
        builder.componentConfigurers.forEach { it.invokeWith(this) }
    }
    install(TelegramBotManager) {
        builder.botManagerConfigurers.forEach { it.invokeWith(this) }
    }
}

/**
 * The DSL builder in [useTelegram].
 */
public class TelegramUsageBuilder {
    internal val componentConfigurers = mutableListOf<ConfigurerFunction<TelegramComponentConfiguration>>()
    internal val botManagerConfigurers = mutableListOf<ConfigurerFunction<TelegramBotManagerConfiguration>>()

    /**
     * Config [TelegramComponentConfiguration] for the [TelegramComponent].
     */
    public fun component(block: ConfigurerFunction<TelegramComponentConfiguration>) {
        componentConfigurers.add(block)
    }

    /**
     * Config [TelegramBotManagerConfiguration] for the [TelegramBotManager].
     */
    public fun botManager(block: ConfigurerFunction<TelegramBotManagerConfiguration>) {
        botManagerConfigurers.add(block)
    }
}

/**
 * Find the [TelegramComponent] in [Application.components].
 *
 * @see Components.find
 */
public fun Application.telegramComponentOrNull(): TelegramComponent? = components.telegramComponentOrNull()

/**
 * Find the [TelegramComponent] in [Application.components]
 * and throw [NoSuchElementException] of not found.
 *
 * @see telegramComponentOrNull
 * @see Components.get
 * @throws NoSuchElementException if not found
 */
public fun Application.telegramComponent(): TelegramComponent = components.telegramComponent()

/**
 * Find the [TelegramBotManager] in [Application.plugins].
 *
 * @see Plugins.find
 */
public fun Application.telegramBotManagerOrNull(): TelegramBotManager? = plugins.telegramBotManagerOrNull()


/**
 * Find the [TelegramBotManager] in [Application.plugins]
 * and throw [NoSuchElementException] of not found.
 *
 * @see telegramBotManagerOrNull
 * @throws NoSuchElementException if not found
 */
public fun Application.telegramBotManager(): TelegramBotManager = plugins.telegramBotManager()


/**
 * Find the [TelegramComponent] in [Components].
 *
 * @see Components.find
 */
public fun Components.telegramComponentOrNull(): TelegramComponent? = find<TelegramComponent>()

/**
 * Find the [TelegramComponent] in [Components]
 * and throw [NoSuchElementException] of not found.
 *
 * @see telegramComponentOrNull
 * @see Components.get
 * @throws NoSuchElementException if not found
 */
public fun Components.telegramComponent(): TelegramComponent = get<TelegramComponent>()

/**
 * Find the [TelegramBotManager] in [Plugins].
 *
 * @see Plugins.find
 */
public fun Plugins.telegramBotManagerOrNull(): TelegramBotManager? = find<TelegramBotManager>()

/**
 * Find the [TelegramBotManager] in [Plugins]
 * and throw [NoSuchElementException] of not found.
 *
 * @see telegramBotManagerOrNull
 * @throws NoSuchElementException if not found
 */
public fun Plugins.telegramBotManager(): TelegramBotManager = get<TelegramBotManager>()


