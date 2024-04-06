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

package love.forte.simbot.telegram.api.message

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonObject
import love.forte.simbot.telegram.api.Telegram
import love.forte.simbot.telegram.api.message.ReplyMarkupWrapper.Companion.wrapper
import love.forte.simbot.telegram.type.ForceReply
import love.forte.simbot.telegram.type.InlineKeyboardMarkup
import love.forte.simbot.telegram.type.ReplyKeyboardMarkup
import love.forte.simbot.telegram.type.ReplyKeyboardRemove
import kotlin.jvm.JvmName
import kotlin.jvm.JvmStatic


/**
 * A wrapper for `reply_markup` field.
 *
 * One of:
 * - [InlineKeyboardMarkup]
 * - [ReplyKeyboardMarkup]
 * - [ReplyKeyboardRemove]
 * - [ForceReply]
 *
 * Note: Can only be used for serialization, not deserialization.
 *
 * @author ForteScarlet
 */
@Serializable(ReplyMarkupWrapperSerializer::class)
public sealed class ReplyMarkupWrapper {
    public abstract val value: Any

    public companion object {
        @JvmStatic
        @JvmName("valueOf")
        public fun InlineKeyboardMarkup.wrapper(): ReplyMarkupWrapper = InlineKeyboardMarkupWrapper(this)
        @JvmStatic
        @JvmName("valueOf")
        public fun ReplyKeyboardMarkup.wrapper(): ReplyMarkupWrapper = ReplyKeyboardMarkupWrapper(this)
        @JvmStatic
        @JvmName("valueOf")
        public fun ReplyKeyboardRemove.wrapper(): ReplyMarkupWrapper = ReplyKeyboardRemoveWrapper(this)
        @JvmStatic
        @JvmName("valueOf")
        public fun ForceReply.wrapper(): ReplyMarkupWrapper = ForceReplyWrapper(this)
    }
}


private data class InlineKeyboardMarkupWrapper(override val value: InlineKeyboardMarkup): ReplyMarkupWrapper()
private data class ReplyKeyboardMarkupWrapper(override val value: ReplyKeyboardMarkup): ReplyMarkupWrapper()
private data class ReplyKeyboardRemoveWrapper(override val value: ReplyKeyboardRemove): ReplyMarkupWrapper()
private data class ForceReplyWrapper(override val value: ForceReply): ReplyMarkupWrapper()


internal object ReplyMarkupWrapperSerializer : KSerializer<ReplyMarkupWrapper> {
    override fun deserialize(decoder: Decoder): ReplyMarkupWrapper {
        // deserialize unsupported,
        // decode to JsonObject.
        val obj = JsonObject.serializer().deserialize(decoder)
        // check fields

        obj["force_reply"]?.also {
            return Telegram.DefaultJson.decodeFromJsonElement(ForceReply.serializer(), obj).wrapper()
        }

        obj["remove_keyboard"]?.also {
            return Telegram.DefaultJson.decodeFromJsonElement(ReplyKeyboardRemove.serializer(), obj).wrapper()
        }

        obj["inline_keyboard"]?.also {
            return Telegram.DefaultJson.decodeFromJsonElement(InlineKeyboardMarkup.serializer(), obj).wrapper()
        }

        return Telegram.DefaultJson.decodeFromJsonElement(ReplyKeyboardMarkup.serializer(), obj).wrapper()

    }

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ReplyMarkupWrapper")

    override fun serialize(encoder: Encoder, value: ReplyMarkupWrapper) {
        when (value) {
            is ForceReplyWrapper -> ForceReply.serializer().serialize(encoder, value.value)
            is ReplyKeyboardRemoveWrapper -> ReplyKeyboardRemove.serializer().serialize(encoder, value.value)
            is InlineKeyboardMarkupWrapper -> InlineKeyboardMarkup.serializer().serialize(encoder, value.value)
            is ReplyKeyboardMarkupWrapper -> ReplyKeyboardMarkup.serializer().serialize(encoder, value.value)
        }
    }
}
