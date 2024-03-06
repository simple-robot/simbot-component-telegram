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

package love.forte.simbot.telegram.api.bot

import kotlinx.serialization.DeserializationStrategy
import love.forte.simbot.telegram.api.SimpleBodyTelegramApi
import love.forte.simbot.telegram.api.TelegramApiResult
import love.forte.simbot.telegram.type.ChatAdministratorRights
import kotlin.jvm.JvmStatic


/**
 * [getMyDefaultAdministratorRights](https://core.telegram.org/bots/api#getmydefaultadministratorrights)
 *
 * Use this method to get the current default administrator rights of the bot.
 * Returns [ChatAdministratorRights] on success.
 *
 * @author ForteScarlet
 */
public class GetMyDefaultAdministratorRightsApi private constructor(forChannelsMark: Int) :
    SimpleBodyTelegramApi<ChatAdministratorRights>() {
    public companion object Factory {
        private const val NAME = "getMyDefaultAdministratorRights"
        private val SER = TelegramApiResult.serializer(ChatAdministratorRights.serializer())
        private const val B_F = 0
        private const val B_T = 1
        private const val B_N = 2
        private val INSTANCE_F = GetMyDefaultAdministratorRightsApi(B_F)
        private val INSTANCE_T = GetMyDefaultAdministratorRightsApi(B_T)
        private val INSTANCE_N = GetMyDefaultAdministratorRightsApi(B_N)

        /**
         * Create [GetMyDefaultAdministratorRightsApi]
         *
         * @param forChannels Pass `True` to get default administrator rights of the bot in channels.
         * Otherwise, default administrator rights of the bot for groups and supergroups will be returned.
         */
        @JvmStatic
        public fun create(forChannels: Boolean): GetMyDefaultAdministratorRightsApi =
            if (forChannels) INSTANCE_T else INSTANCE_F

        /**
         * Create [GetMyDefaultAdministratorRightsApi]
         */
        @JvmStatic
        public fun create(): GetMyDefaultAdministratorRightsApi = INSTANCE_N

    }

    override val name: String
        get() = NAME

    override val body: Any? = when (forChannelsMark) {
        B_F -> "{\"for_channels\":false}"
        B_T -> "{\"for_channels\":true}"
        else -> null
    }

    override val responseDeserializer: DeserializationStrategy<ChatAdministratorRights>
        get() = ChatAdministratorRights.serializer()

    override val resultDeserializer: DeserializationStrategy<TelegramApiResult<ChatAdministratorRights>>
        get() = SER


}
