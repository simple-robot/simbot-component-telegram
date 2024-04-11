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

package love.forte.simbot.component.telegram.core.message

import love.forte.simbot.telegram.api.TelegramApi
import love.forte.simbot.telegram.api.message.CopyMessageApi
import love.forte.simbot.telegram.api.message.ForwardMessageApi
import love.forte.simbot.telegram.api.message.SendMessageApi
import love.forte.simbot.telegram.type.Message
import love.forte.simbot.telegram.type.MessageId
import kotlin.jvm.JvmStatic


/**
 * A [TelegramMessageElement] implementation that provide a [TelegramApi]
 * (witch the type of result is [Message] or [MessageId],
 * e.g. [SendMessageApi], [ForwardMessageApi], [CopyMessageApi], etc.) directly.
 *
 * [TelegramMessageResultApiElement] is a [SendOnly] type,
 * and it is also a 'malleable' best message element.
 *
 * Note: The [TelegramMessageResultApiElement] is NOT serializable.
 * @author ForteScarlet
 */
@SendOnly
public sealed class TelegramMessageResultApiElement : TelegramMessageElement {
    /**
     * The result `*` is [Message] or [MessageId].
     */
    public abstract val api: TelegramApi<*>

    public companion object {
        /**
         * Create a [TelegramMessageResultApiElement] by [api].
         */
        @JvmStatic
        public fun createByMessageApi(api: TelegramApi<Message>): TelegramMessageResultApiElement =
            MessageResult(api)

        /**
         * Create a [TelegramMessageResultApiElement] by [api].
         */
        @JvmStatic
        public fun createByMessageIdApi(api: TelegramApi<MessageId>): TelegramMessageResultApiElement =
            MessageIdResult(api)
    }
}

internal data class MessageResult(override val api: TelegramApi<Message>) : TelegramMessageResultApiElement()
internal data class MessageIdResult(override val api: TelegramApi<MessageId>) : TelegramMessageResultApiElement()


internal object TelegramMessageResultApiElementSendingResolver : SendingMessageResolver {
    override suspend fun resolve(
        index: Int,
        element: love.forte.simbot.message.Message.Element,
        source: love.forte.simbot.message.Message,
        context: SendingMessageResolverContext
    ) {
        if (element is TelegramMessageResultApiElement) {
            when (element) {
                is MessageIdResult -> {
                    context.addToStackMsgId { element.api }
                }
                is MessageResult -> {
                    context.addToStackMsg { element.api }
                }
            }
        }
    }
}
