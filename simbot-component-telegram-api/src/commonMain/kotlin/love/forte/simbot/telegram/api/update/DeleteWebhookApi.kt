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
import kotlinx.serialization.builtins.serializer
import love.forte.simbot.telegram.api.JsonBodyTelegramApi
import love.forte.simbot.telegram.api.TelegramApiResult
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic


/**
 * [deleteWebhook](https://core.telegram.org/bots/api#deletewebhook)
 *
 * Use this method to remove webhook integration if you decide to switch back to getUpdates. Returns True on success.
 *
 * @author ForteScarlet
 */
public class DeleteWebhookApi private constructor(dropPendingUpdates: Boolean?) : JsonBodyTelegramApi<Boolean>() {
    public companion object Factory {
        private const val NAME = "deleteWebhook"
        private val SER = TelegramApiResult.serializer(Boolean.serializer())
        private val NULL_INSTANCE = DeleteWebhookApi(null)
        private val TRUE_INSTANCE = DeleteWebhookApi(true)
        private val FALSE_INSTANCE = DeleteWebhookApi(false)

        private fun toJsonBodyString(dropPendingUpdates: Boolean?): String =
            if (dropPendingUpdates == null) "{}"
            else """{"drop_pending_updates":$dropPendingUpdates}"""

        /**
         * Create an instance of [DeleteWebhookApi].
         *
         * @param dropPendingUpdates Optional, Pass `True` to drop all pending updates
         */
        @JvmStatic
        @JvmOverloads
        public fun create(dropPendingUpdates: Boolean? = null): DeleteWebhookApi = when (dropPendingUpdates) {
            null -> NULL_INSTANCE
            true -> TRUE_INSTANCE
            false -> FALSE_INSTANCE
        }
    }

    override val name: String
        get() = NAME

    override val body: Any = toJsonBodyString(dropPendingUpdates)

    override val responseDeserializer: DeserializationStrategy<Boolean>
        get() = Boolean.serializer()

    override val resultDeserializer: DeserializationStrategy<TelegramApiResult<Boolean>>
        get() = SER
}
