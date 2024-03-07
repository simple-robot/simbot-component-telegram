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

package love.forte.simbot.component.telegram.component

import kotlinx.serialization.modules.SerializersModule
import love.forte.simbot.common.function.ConfigurerFunction
import love.forte.simbot.common.function.invokeBy
import love.forte.simbot.component.Component
import love.forte.simbot.component.ComponentConfigureContext
import love.forte.simbot.component.ComponentFactory
import kotlin.jvm.JvmField


/**
 *
 * @author ForteScarlet
 */
public class TelegramComponent : Component {
    override val id: String
        get() = ID_VALUE

    override val serializersModule: SerializersModule
        get() = SerializersModule

    public companion object Factory : ComponentFactory<TelegramComponent, TelegramComponentConfiguration> {
        public const val ID_VALUE: String = "simbot.telegram"

        @JvmField
        public val SerializersModule: SerializersModule = SerializersModule {
            // TODO SerializersModule
        }

        override val key: ComponentFactory.Key = object : ComponentFactory.Key {}

        override fun create(
            context: ComponentConfigureContext,
            configurer: ConfigurerFunction<TelegramComponentConfiguration>
        ): TelegramComponent {
            TelegramComponentConfiguration().invokeBy(configurer)
            return TelegramComponent()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        return other is TelegramComponent
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String =
        "TelegramComponent(id=$ID_VALUE)"
}

public class TelegramComponentConfiguration
