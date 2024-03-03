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

package love.forte.simbot.telegram.api.update

import kotlinx.serialization.DeserializationStrategy
import love.forte.simbot.telegram.api.EmptyBodyTelegramApi
import love.forte.simbot.telegram.api.TelegramApiResult
import kotlin.jvm.JvmStatic


/**
 * [getWebhookInfo](https://core.telegram.org/bots/api#getwebhookinfo)
 *
 * Use this method to get current webhook status. Requires no parameters.
 * On success, returns a WebhookInfo object. If the bot is using getUpdates,
 * will return an object with the url field empty.
 *
 * @author ForteScarlet
 */
public class GetWebhookInfoApi private constructor() : EmptyBodyTelegramApi<WebhookInfo>() {
    public companion object Factory {
        private const val NAME = "getWebhookInfo"
        private val INSTANCE = GetWebhookInfoApi()
        private val SER = TelegramApiResult.serializer(WebhookInfo.serializer())

        /**
         * Create an instance of [GetWebhookInfoApi].
         */
        @JvmStatic
        public fun create(): GetWebhookInfoApi = INSTANCE
    }

    override val name: String
        get() = NAME

    override val responseDeserializer: DeserializationStrategy<WebhookInfo>
        get() = WebhookInfo.serializer()

    override val resultDeserializer: DeserializationStrategy<TelegramApiResult<WebhookInfo>>
        get() = SER
}
