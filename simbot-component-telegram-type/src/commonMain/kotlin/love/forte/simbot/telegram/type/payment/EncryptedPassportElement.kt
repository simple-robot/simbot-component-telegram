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

package love.forte.simbot.telegram.type.payment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * [EncryptedPassportElement](https://core.telegram.org/bots/api#encryptedpassportelement)
 *
 */
@Serializable
public data class EncryptedPassportElement(
    /**
     * Element type.
     * One of “personal_details”, “passport”, “driver_license”, “identity_card”, “internal_passport”, “address”, “utility_bill”, “bank_statement”, “rental_agreement”, “passport_registration”, “temporary_registration”, “phone_number”, “email”.
     */
    val type: String,
    /**
     * Optional.
     * Base64-encoded encrypted Telegram Passport element data provided by the user; available only for “personal_details”, “passport”, “driver_license”, “identity_card”, “internal_passport” and “address” types. Can be decrypted and verified using the accompanying EncryptedCredentials.
     */
    val data: String? = null,
    /**
     * Optional.
     * User's verified phone number; available only for “phone_number” type
     */
    @SerialName("phone_number")
    val phoneNumber: String? = null,
    /**
     * Optional.
     * User's verified email address; available only for “email” type
     */
    val email: String? = null,
    /**
     * Optional.
     * Array of encrypted files with documents provided by the user;
     * available only for “utility_bill”, “bank_statement”, “rental_agreement”, “passport_registration” and “temporary_registration” types.
     * Files can be decrypted and verified using the accompanying EncryptedCredentials.
     */
    val files: List<love.forte.simbot.telegram.type.passport.PassportFile>? = null,
    /**
     * Optional.
     * Encrypted file with the front side of the document, provided by the user;
     * available only for “passport”, “driver_license”, “identity_card” and “internal_passport”.
     * The file can be decrypted and verified using the accompanying EncryptedCredentials.
     */
    @SerialName("front_side")
    val frontSide: love.forte.simbot.telegram.type.passport.PassportFile? = null,

    /**
     * Optional.
     * Encrypted file with the reverse side of the document, provided by the user;
     * available only for “driver_license” and “identity_card”.
     * The file can be decrypted and verified using the accompanying EncryptedCredentials.
     */
    @SerialName("reverse_side")
    val reverseSide: love.forte.simbot.telegram.type.passport.PassportFile? = null,

    /**
     * Optional.
     * Encrypted file with the selfie of the user holding a document, provided by the user;
     * available if requested for “passport”, “driver_license”, “identity_card” and “internal_passport”.
     * The file can be decrypted and verified using the accompanying EncryptedCredentials.
     */
    val selfie: love.forte.simbot.telegram.type.passport.PassportFile? = null,
    /**
     * Optional.
     * Array of encrypted files with translated versions of documents provided by the user;
     * available if requested for “passport”, “driver_license”, “identity_card”, “internal_passport”, “utility_bill”, “bank_statement”, “rental_agreement”, “passport_registration” and “temporary_registration” types.
     * Files can be decrypted and verified using the accompanying EncryptedCredentials.
     */
    val translation: List<love.forte.simbot.telegram.type.passport.PassportFile>? = null,
    /**
     * Base64-encoded element hash for using in PassportElementErrorUnspecified
     */
    val hash: String
)
