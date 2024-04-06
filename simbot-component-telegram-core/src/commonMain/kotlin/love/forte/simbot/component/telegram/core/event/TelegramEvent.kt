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

package love.forte.simbot.component.telegram.core.event

import love.forte.simbot.common.id.ID
import love.forte.simbot.common.id.IntID.Companion.ID
import love.forte.simbot.component.telegram.core.bot.TelegramBot
import love.forte.simbot.component.telegram.core.component.TelegramComponent
import love.forte.simbot.event.BotEvent
import love.forte.simbot.telegram.api.update.Update
import love.forte.simbot.telegram.type.Message

/**
 * The [Event][love.forte.simbot.telegram.stdlib.event.Event] from the `stdlib` module.
 */
public typealias StdlibEvent = love.forte.simbot.telegram.stdlib.event.Event

/**
 * Is a Telegram simbot component event definition type.
 * Is an implementation of the event type in the simbot API.
 *
 * @author ForteScarlet
 */
public interface TelegramEvent : BotEvent {
    // 所有的 Telegram Event 都是建立在 Bot 之上的，
    // 因此所有 TelegramEvent 都实现 BotEvent

    /**
     * The [TelegramBot].
     */
    override val bot: TelegramBot

    /**
     * The [TelegramComponent].
     */
    override val component: TelegramComponent
        get() = bot.component

    /**
     * The source event [StdlibEvent].
     */
    public val sourceEvent: StdlibEvent

    /**
     * [Update] in [sourceEvent]
     */
    public val sourceUpdate: Update
        get() = sourceEvent.update

    /**
     * [Update.updateId] in [sourceUpdate]
     */
    override val id: ID
        get() = sourceUpdate.updateId.ID

    /**
     * The `content` in [StdlibEvent].
     * In `BasicTelegramXxxEvent` (for example [BasicTelegramMessageEvent])
     * is covered by a more specific type (for example [Message] in [BasicTelegramMessageEvent.sourceContent]).
     */
    public val sourceContent: Any

    /**
     * The [sourceEvent.raw][love.forte.simbot.telegram.stdlib.event.Event.raw].
     */
    public val sourceRaw: String?
        get() = sourceEvent.raw

}

/**
 * Annotate a type as _automatically generated_.
 */
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
public annotation class GeneratedEvent
