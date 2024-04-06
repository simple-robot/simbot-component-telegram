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

@file:JvmName("TelegramComponents")

package love.forte.simbot.component.telegram.component

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.subclass
import love.forte.simbot.bot.serializableBotConfigurationPolymorphic
import love.forte.simbot.common.function.ConfigurerFunction
import love.forte.simbot.common.function.invokeBy
import love.forte.simbot.common.services.Services
import love.forte.simbot.common.services.addProviderExceptJvm
import love.forte.simbot.component.*
import love.forte.simbot.component.telegram.bot.SerializableTelegramBotConfiguration
import love.forte.simbot.component.telegram.message.includeAllComponentMessageElementImpls
import love.forte.simbot.message.messageElementPolymorphic
import kotlin.jvm.JvmField
import kotlin.jvm.JvmName


/**
 *
 * @author ForteScarlet
 */
public class TelegramComponent : Component {
    override val id: String
        get() = ID_VALUE

    override val serializersModule: SerializersModule
        get() = SerializersModule


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        return other is TelegramComponent
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String =
        "TelegramComponent(id=$ID_VALUE)"

    public companion object Factory : ComponentFactory<TelegramComponent, TelegramComponentConfiguration> {
        public const val ID_VALUE: String = "simbot.telegram"

        @JvmField
        public val SerializersModule: SerializersModule = SerializersModule {
            serializableBotConfigurationPolymorphic {
                subclass(SerializableTelegramBotConfiguration.serializer())
            }

            messageElementPolymorphic {
                includeAllComponentMessageElementImpls()
            }
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

}

/**
 * A config for [TelegramComponent].
 */
public class TelegramComponentConfiguration

/**
 * The service provider for [TelegramComponent.Factory].
 */
public class TelegramComponentFactoryProvider : ComponentFactoryProvider<TelegramComponentConfiguration> {
    public companion object {
        init {
            Services.addProviderExceptJvm(ComponentFactoryProvider::class) { TelegramComponentFactoryProvider() }
        }
    }

    override fun provide(): ComponentFactory<*, TelegramComponentConfiguration> = TelegramComponent
    override fun loadConfigures(): Sequence<ComponentFactoryConfigurerProvider<TelegramComponentConfiguration>> {
        return Services.loadProviders<TelegramComponentFactoryConfigurerProvider>().map { it.invoke() }
    }
}

/**
 * The extension configurers for [TelegramComponentFactoryProvider].
 */
public interface TelegramComponentFactoryConfigurerProvider : ComponentFactoryConfigurerProvider<TelegramComponentConfiguration>

/**
 * install [TelegramComponent] to [ComponentInstaller].
 *
 * ```Kotlin
 * launchApplication(...) {
 *     useTelegramComponent()
 * }
 * ```
 *
 */
public fun ComponentInstaller.useTelegramComponent(configurer: ConfigurerFunction<TelegramComponentConfiguration>? = null) {
    if (configurer == null) {
        install(TelegramComponent)
    } else {
        install(TelegramComponent, configurer)
    }
}
