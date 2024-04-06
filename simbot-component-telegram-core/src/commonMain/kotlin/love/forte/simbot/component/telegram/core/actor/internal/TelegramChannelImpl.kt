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

package love.forte.simbot.component.telegram.core.actor.internal

import love.forte.simbot.component.telegram.core.bot.internal.TelegramBotImpl
import love.forte.simbot.telegram.type.Chat
import kotlin.coroutines.CoroutineContext


/**
 *
 * @author ForteScarlet
 */
internal class TelegramChannelImpl(
    override val bot: TelegramBotImpl,
    override val source: Chat
) : AbstractTelegramChatGroupActor(),
    love.forte.simbot.component.telegram.core.actor.TelegramChannel {
    override val coroutineContext: CoroutineContext = bot.subContext

    override fun toString(): String =
        "TelegramChatGroup(id=${source.id}, name=${source.title}, type=${source.type})"
}


internal fun Chat.toTelegramChannel(bot: TelegramBotImpl): TelegramChannelImpl =
    TelegramChannelImpl(bot, this)
