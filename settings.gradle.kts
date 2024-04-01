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

rootProject.name = "simbot-component-telegram"

include(":internal-processors:update-events-processor")
include(":internal-processors:stdlib-processor-extensions-processor")
include(":internal-processors:component-events-processor")

include(":simbot-component-telegram-type")
include(":simbot-component-telegram-api")
include(":simbot-component-telegram-stdlib")
include(":simbot-component-telegram-core")
