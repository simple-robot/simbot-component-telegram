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

import love.forte.simbot.common.id.LongID
import love.forte.simbot.common.id.LongID.Companion.ID
import love.forte.simbot.component.telegram.message.TelegramMessageReceipt
import love.forte.simbot.definition.Contact
import love.forte.simbot.message.Message
import love.forte.simbot.message.MessageContent
import love.forte.simbot.suspendrunner.ST
import love.forte.simbot.telegram.type.User


/**
 * A Telegram contact (that from a private chat).
 *
 * @author ForteScarlet
 */
public interface TelegramContact : TelegramUser, Contact {
    override val source: User

    /**
     * The [User.id]
     */
    override val id: LongID
        get() = source.id.ID

    /**
     * The [User.username] or [User.firstName] if its null.
     */
    override val name: String
        get() = source.username ?: source.firstName

    /**
     * Always `null`.
     * [User] can not get avatar directly.
     */
    override val avatar: String?
        get() = null

    @ST
    override suspend fun send(messageContent: MessageContent): TelegramMessageReceipt

    @ST
    override suspend fun send(message: Message): TelegramMessageReceipt

    @ST
    override suspend fun send(text: String): TelegramMessageReceipt
}
