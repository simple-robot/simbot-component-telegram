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

package love.forte.simbot.telegram.stdlib.event

import love.forte.simbot.telegram.api.update.Update
import love.forte.simbot.telegram.stdlib.bot.Bot
import love.forte.simbot.telegram.type.Message


/**
 * A `Update` event for [Bot].
 *
 * _By internal implementation, third-party implementation is unstable._
 *
 * @see Bot
 * @see EventProcessor
 *
 * @author ForteScarlet
 */
public interface Event {
    /**
     * The source [Update] instance.
     */
    public val update: Update

    /**
     * Name of the event.
     * Is the name of the field corresponding to the event,
     * such as `edited_message`.
     */
    public val name: String

    /**
     * The content object of the event.
     * The type is one of the possible optional fields in [Update],
     * such as [Message] ([Update.message]).
     */
    public val content: Any

    /**
     * The raw JSON string used to parse out [update].
     * If [update] is externally provided and raw is not provided, it may get `null`
     */
    public val raw: String?
}
