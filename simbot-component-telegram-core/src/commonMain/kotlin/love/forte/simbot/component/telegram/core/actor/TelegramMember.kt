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

package love.forte.simbot.component.telegram.core.actor

import kotlinx.coroutines.Job
import love.forte.simbot.common.id.ID
import love.forte.simbot.common.id.LongID.Companion.ID
import love.forte.simbot.component.telegram.core.bot.TelegramBot
import love.forte.simbot.component.telegram.core.message.TelegramMessageReceipt
import love.forte.simbot.definition.Member
import love.forte.simbot.message.Message
import love.forte.simbot.message.MessageContent
import love.forte.simbot.suspendrunner.ST
import love.forte.simbot.telegram.type.User
import kotlin.coroutines.CoroutineContext

/**
 *
 * @author ForteScarlet
 */
public interface TelegramMember : TelegramUser, Member {
    /**
     * From [TelegramBot], without [Job].
     */
    override val coroutineContext: CoroutineContext
    override val source: User

    override val id: ID
        get() = source.id.ID

    override val name: String
        get() = source.username ?: source.firstName

    override val nick: String?
        get() = null

    override val avatar: String?
        get() = null

    /**
     * Always throws [UnsupportedOperationException].
     * Telegram bot cannot send a private message proactively
     * without the private chat channel id.
     *
     * @throws UnsupportedOperationException Unsupported
     */
    @ST
    override suspend fun send(text: String): TelegramMessageReceipt =
        sendToMemberIsUnsupported()

    /**
     * Always throws [UnsupportedOperationException].
     * Telegram bot cannot send a private message proactively
     * without the private chat channel id.
     *
     * @throws UnsupportedOperationException Unsupported
     */
    @ST
    override suspend fun send(message: Message): TelegramMessageReceipt =
        sendToMemberIsUnsupported()

    /**
     * Always throws [UnsupportedOperationException].
     * Telegram bot cannot send a private message proactively
     * without the private chat channel id.
     *
     * @throws UnsupportedOperationException Unsupported
     */
    @ST
    override suspend fun send(messageContent: MessageContent): TelegramMessageReceipt =
        sendToMemberIsUnsupported()
}

internal const val SEND_TO_MEMBER_IS_UNSUPPORTED = "Telegram bot cannot proactively send a message to a member " +
        "because the private chat id is unknown."

internal fun sendToMemberIsUnsupported(): Nothing =
    throw UnsupportedOperationException(SEND_TO_MEMBER_IS_UNSUPPORTED)
