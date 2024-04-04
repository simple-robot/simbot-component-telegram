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
import kotlinx.coroutines.SupervisorJob
import love.forte.simbot.common.function.ConfigurerFunction
import love.forte.simbot.common.function.invokeWith
import love.forte.simbot.telegram.stdlib.bot.BotConfiguration
import kotlin.coroutines.CoroutineContext
import kotlin.jvm.JvmOverloads


/**
 * A [TelegramBot] configuration.
 *
 * @author ForteScarlet
 */
public class TelegramBotConfiguration @JvmOverloads constructor(
    /**
     * The source [configuration][BotConfiguration] for [StdlibBot].
     */
    public var botConfiguration: BotConfiguration = BotConfiguration()
) {
    /**
     * The [CoroutineContext] delegate from [botConfiguration].
     * If [Job] is provided, it ends up wrapped as parent in a new [SupervisorJob].
     */
    public var coroutineContext: CoroutineContext by botConfiguration::coroutineContext

    /**
     * configure the [botConfiguration].
     */
    public fun botConfiguration(block: ConfigurerFunction<BotConfiguration>): TelegramBotConfiguration = apply {
        block.invokeWith(botConfiguration)
    }
}
