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

package love.forte.simbot.component.telegram.message

import love.forte.simbot.ability.DeleteOption
import love.forte.simbot.common.id.ID
import love.forte.simbot.common.id.IntID.Companion.ID
import love.forte.simbot.message.MessageContent
import love.forte.simbot.message.Messages

public typealias StdlibMessage = love.forte.simbot.telegram.type.Message

/**
 *
 * @author ForteScarlet
 */
public interface TelegramMessageContent : MessageContent {
    public val source: StdlibMessage

    override val id: ID
        get() = source.messageId.ID

    override val messages: Messages
        get() = TODO("Not yet implemented")

    override val plainText: String?
        get() = TODO("Not yet implemented")

    override suspend fun delete(vararg options: DeleteOption) {

        TODO("Not yet implemented")
    }
}
