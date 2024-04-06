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
import love.forte.simbot.telegram.type.User
import kotlin.coroutines.CoroutineContext


/**
 *
 * @author ForteScarlet
 */
internal class TelegramMemberImpl(
    private val bot: TelegramBotImpl,
    override val source: User,
) : love.forte.simbot.component.telegram.core.actor.TelegramMember {
    override val coroutineContext: CoroutineContext = bot.subContext

    override fun toString(): String {
        return "TelegramMember(id=${source.id}, username=${source.username}, firstName=${source.firstName})"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TelegramMemberImpl) return false

        if (bot != other.bot) return false
        if (source != other.source) return false

        return true
    }

    override fun hashCode(): Int {
        var result = bot.hashCode()
        result = 31 * result + source.hashCode()
        return result
    }


}

internal fun User.toTelegramMember(bot: TelegramBotImpl): TelegramMemberImpl =
    TelegramMemberImpl(bot, this)
