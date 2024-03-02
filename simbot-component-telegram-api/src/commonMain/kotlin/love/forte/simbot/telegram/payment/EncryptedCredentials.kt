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

package love.forte.simbot.telegram.payment

import kotlinx.serialization.Serializable

/**
 * [EncryptedCredentials](https://core.telegram.org/bots/api#encryptedcredentials)
 *
 * Describes data required for decrypting and authenticating EncryptedPassportElement.
 * See the Telegram Passport Documentation for a complete description of the data decryption and authentication processes.
 *
 * @author ForteScarlet
 */
@Serializable
public data class EncryptedCredentials(
    /**
     * Base64-encoded encrypted JSON-serialized data with unique user's payload, data hashes and secrets required for EncryptedPassportElement decryption and authentication
     */
    val data: String,
    /**
     * Base64-encoded data hash for data authentication
     */
    val hash: String,
    /**
     * Base64-encoded secret, encrypted with the bot's public RSA key, required for data decryption
     */
    val secret: String,
)
