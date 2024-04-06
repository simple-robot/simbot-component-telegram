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

package love.forte.simbot.component.telegram.core

import love.forte.simbot.common.function.ConfigurerFunction
import love.forte.simbot.common.function.invokeBy
import love.forte.simbot.common.function.invokeWith
import love.forte.simbot.component.ComponentInstaller
import love.forte.simbot.component.telegram.core.bot.TelegramBotManager
import love.forte.simbot.component.telegram.core.bot.TelegramBotManagerConfiguration
import love.forte.simbot.component.telegram.core.component.TelegramComponent
import love.forte.simbot.component.telegram.core.component.TelegramComponentConfiguration
import love.forte.simbot.plugin.PluginInstaller

// TODO usage


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

public class TelegramUsageBuilder {
    internal val componentConfigurers = mutableListOf<ConfigurerFunction<TelegramComponentConfiguration>>()
    internal val botManagerConfigurers = mutableListOf<ConfigurerFunction<TelegramBotManagerConfiguration>>()

    public fun component(block: ConfigurerFunction<TelegramComponentConfiguration>) {
        componentConfigurers.add(block)
    }

    public fun botManager(block: ConfigurerFunction<TelegramBotManagerConfiguration>) {
        botManagerConfigurers.add(block)
    }
}
