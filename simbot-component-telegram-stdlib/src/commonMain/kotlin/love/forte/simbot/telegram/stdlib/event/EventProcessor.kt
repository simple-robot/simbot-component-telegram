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

@file:JvmName("EventProcessors")
@file:JvmMultifileClass

package love.forte.simbot.telegram.stdlib.event

import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName
import kotlin.jvm.JvmSynthetic

/**
 * An [Event] processor.
 *
 * Tip: It can be created under the JVM platform using a factory function in `EventProcessors`.
 *
 * @author ForteScarlet
 */
public fun interface EventProcessor {
    /**
     * Process the [Event].
     */
    @JvmSynthetic
    public suspend fun process(event: Event)
}
