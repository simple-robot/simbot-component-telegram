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

import love.forte.simbot.component.ComponentFactoryProvider;
import love.forte.simbot.component.telegram.core.bot.TelegramBotManagerFactoryConfigurerProvider;
import love.forte.simbot.component.telegram.core.bot.TelegramBotManagerFactoryProvider;
import love.forte.simbot.component.telegram.core.component.TelegramComponentFactoryConfigurerProvider;
import love.forte.simbot.component.telegram.core.component.TelegramComponentFactoryProvider;
import love.forte.simbot.plugin.PluginFactoryProvider;

module simbot.component.telegram.core {
    requires kotlin.stdlib;
    requires transitive simbot.api;
    requires transitive simbot.component.telegram.type;
    requires transitive simbot.component.telegram.api;
    requires transitive simbot.component.telegram.stdlib;
    requires simbot.common.ktor.inputfile;
    requires io.ktor.io;
    requires io.ktor.client.core;


    exports love.forte.simbot.component.telegram.core;
    exports love.forte.simbot.component.telegram.core.actor;
    exports love.forte.simbot.component.telegram.core.bot;
    exports love.forte.simbot.component.telegram.core.component;
    exports love.forte.simbot.component.telegram.core.event;
    exports love.forte.simbot.component.telegram.core.message;
    exports love.forte.simbot.component.telegram.core.time;

    // component
    provides ComponentFactoryProvider with TelegramComponentFactoryProvider;
    uses TelegramComponentFactoryConfigurerProvider;
    // bot manager
    provides PluginFactoryProvider with TelegramBotManagerFactoryProvider;
    uses TelegramBotManagerFactoryConfigurerProvider;
}
