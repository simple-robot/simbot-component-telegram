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

package love.forte.simbot.component.telegram.actor

import kotlinx.coroutines.Job
import love.forte.simbot.component.telegram.bot.TelegramBot
import love.forte.simbot.definition.ChatGroup
import love.forte.simbot.telegram.type.Chat
import kotlin.coroutines.CoroutineContext


/**
 *
 * @author ForteScarlet
 */
public interface TelegramChatGroup : TelegramChat, ChatGroup {
    /**
     * From [TelegramBot], without [Job].
     */
    override val coroutineContext: CoroutineContext

    override val source: Chat

}
