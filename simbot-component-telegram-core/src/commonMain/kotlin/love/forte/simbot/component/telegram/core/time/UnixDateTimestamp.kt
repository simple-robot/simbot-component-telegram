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

package love.forte.simbot.component.telegram.core.time

import love.forte.simbot.common.time.TimeUnit
import love.forte.simbot.common.time.Timestamp


/**
 *
 * @author ForteScarlet
 */
internal class UnixDateTimestamp(private val date: Int) : Timestamp {
    override val milliseconds: Long
        get() = TimeUnit.SECONDS.toMillis(date.toLong())

    override fun compareTo(other: Timestamp): Int {
        return date.compareTo(other.timeAs(TimeUnit.SECONDS))
    }

    override fun timeAs(unit: TimeUnit): Long {
        return unit.convert(date.toLong(), TimeUnit.SECONDS)
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other is UnixDateTimestamp) return date == other.date
        if (other !is Timestamp) return false

        return date.toLong() == other.timeAs(TimeUnit.SECONDS)
    }

    override fun hashCode(): Int = date.hashCode()
    override fun toString(): String {
        return "UnixDateTimestamp(date=$date)"
    }
}

internal fun unixDateTimestamp(date: Int): UnixDateTimestamp = UnixDateTimestamp(date)
