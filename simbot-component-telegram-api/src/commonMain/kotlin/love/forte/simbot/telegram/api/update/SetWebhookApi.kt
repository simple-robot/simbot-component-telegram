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

import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.builtins.serializer
import love.forte.simbot.telegram.api.FormBodyTelegramApi
import love.forte.simbot.telegram.api.TelegramApiResult
import love.forte.simbot.telegram.file.InputFile


/**
 * [setWebhook](https://core.telegram.org/bots/api#setwebhook)
 *
 * > Use this method to specify a URL and receive incoming updates via an outgoing webhook.
 * > Whenever there is an update for the bot,
 * > we will send an HTTPS POST request to the specified URL,
 * > containing a JSON-serialized Update.
 * > In case of an unsuccessful request,
 * > we will give up after a reasonable amount of attempts.
 * > Returns True on success.
 * >
 * > If you'd like to make sure that the webhook was set by you,
 * > you can specify secret data in the parameter secret_token.
 * > If specified,
 * > the request will contain a header “X-Telegram-Bot-Api-Secret-Token” with the secret token as content.
 * >
 * > Notes
 * > 1. You will not be able to receive updates using getUpdates for as long as an outgoing webhook is set up.
 * > 2. To use a self-signed certificate, you need to upload your public key certificate using certificate parameter.
 * >    Please upload as InputFile, sending a String will not work.
 * > 3. Ports currently supported for webhooks: 443, 80, 88, 8443.
 * >
 * > If you're having any trouble setting up webhooks, please check out this amazing guide to webhooks.
 *
 * @author ForteScarlet
 */
public class SetWebhookApi private constructor(params: Params) : FormBodyTelegramApi<Boolean>() {
    public companion object Factory {
        private const val NAME = "setWebhook"
        private val SER = TelegramApiResult.serializer(Boolean.serializer())
        private fun Params.toForm(): MultiPartFormDataContent {
            return MultiPartFormDataContent(formData {
                append("url", url)
                ipAddress?.also { append("ip_address", it) }
                maxConnections?.also { append("max_connections", it) }
                allowedUpdates?.also { append("allowed_updates", it) }
                dropPendingUpdates?.also { append("drop_pending_updates", it) }
                secretToken?.also { append("secret_token", it) }

                // upload file
                certificate?.includeTo("certificate", headers {
                    append(HttpHeaders.ContentType, "image/png")
                    append(HttpHeaders.ContentDisposition, "filename=\"file\"")
                }, this)
            })
        }

    }

    override val name: String
        get() = NAME

    override val body: MultiPartFormDataContent = params.toForm()

    override val responseDeserializer: DeserializationStrategy<Boolean>
        get() = Boolean.serializer()

    override val resultDeserializer: DeserializationStrategy<TelegramApiResult<Boolean>>
        get() = SER

    /**
     * Request body of [SetWebhookApi].
     *
     * @property url HTTPS URL to send updates to. Use an empty string to remove webhook integration
     * @property certificate Optional.
     * Upload your public key certificate so that the root certificate in use can be checked.
     * See our self-signed guide for details.
     * @property ipAddress Optional.
     * The fixed IP address which will be used to send webhook requests instead of the IP address resolved through DNS
     *
     * @property maxConnections Optional.
     * The maximum allowed number of simultaneous HTTPS connections to the webhook for update delivery,
     * 1-100. Defaults to 40. Use lower values to limit the load on your bot's server,
     * and higher values to increase your bot's throughput.
     *
     * @property allowedUpdates Optional.
     * A JSON-serialized list of the update types you want your bot to receive.
     * For example, specify `["message", "edited_channel_post", "callback_query"]` to only receive updates of these types.
     * See Update for a complete list of available update types.
     * Specify an empty list to receive all update types except chat_member, message_reaction, and message_reaction_count (default).
     * If not specified, the previous setting will be used.
     *
     * Please note that this parameter doesn't affect updates created before the call to the setWebhook,
     * so unwanted updates may be received for a short period of time.
     *
     * @property dropPendingUpdates Optional.
     * Pass True to drop all pending updates
     * @property secretToken Optional.
     * A secret token to be sent in a header “X-Telegram-Bot-Api-Secret-Token” in every webhook request, 1-256 characters.
     * Only characters `A-Z`, `a-z`, `0-9`, `_` and `-` are allowed.
     * The header is useful to ensure that the request comes from a webhook set by you.
     *
     */
    public data class Params(
        var url: String,
        var certificate: InputFile? = null,
        var ipAddress: String? = null,
        var maxConnections: Int? = null,
        var allowedUpdates: Collection<String>? = null,
        var dropPendingUpdates: String? = null,
        var secretToken: String? = null,
    ) {
        public constructor(urlValue: String) : this(url = urlValue)
    }

}

