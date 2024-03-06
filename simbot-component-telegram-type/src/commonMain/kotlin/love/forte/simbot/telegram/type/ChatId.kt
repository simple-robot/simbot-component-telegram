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

@file:JvmName("ChatIds")
@file:JvmMultifileClass

package love.forte.simbot.telegram.type

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName

/**
 * A Chat id that can be either [Long] or [String].
 * If it's a string, it should start with '@'.
 *
 * [ChatId] is usually used for request body types and is not recommended for scenarios that require deserialization.
 * It is serialized directly to a literal value, such as a number or a string, rather than a struct.
 *
 * In Java, create an instance of [ChatId] via [`ChatIds.valueOf(...)`][ChatId] .
 */
@Serializable(ChatIdSerializer::class)
public sealed class ChatId {
    /**
     * Chat id of type [Long].
     * This parameter is **valid only** if [string] is not null,
     * otherwise, return `0` always.
     */
    public abstract val long: Long

    /**
     * Chat id of type [String].
     * This parameter is valid only if [string] is not null.
     */
    public abstract val string: String?

    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ChatId) return false

        if (long != other.long) return false
        if (string != other.string) return false

        return true
    }

    final override fun hashCode(): Int {
        var result = long.hashCode()
        result = 31 * result + (string?.hashCode() ?: 0)
        return result
    }
}

/**
 * Create a [ChatId] by [Long].
 */
@JvmName("valueOf")
public fun ChatId(value: Long): ChatId = LChatId(value)

/**
 * Create a [ChatId] by [String].
 */
@JvmName("valueOf")
public fun ChatId(value: String): ChatId = SChatId(value)

private class LChatId(override val long: Long) : ChatId() {
    override val string: String? get() = null
    override fun toString(): String = "ChatId($long)"
}

private class SChatId(override val string: String) : ChatId() {
    override val long: Long get() = 0L
    override fun toString(): String = "ChatId(\"$string\")"
}

/**
 * The serializer of [ChatId].
 * It is only recommended for deserialization scenarios.
 *
 * In the deserialization of this serializer,
 * it will be assumed that the string type chatId begins with `'@'`.
 *
 * @see ChatId
 */
public object ChatIdSerializer : KSerializer<ChatId> {
    override fun deserialize(decoder: Decoder): ChatId {
        val strValue = decoder.decodeString()
        if (strValue.startsWith('@')) return ChatId(strValue)

        return strValue.toLongOrNull()?.let(::ChatId) ?: ChatId(strValue)
    }

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ChatId")

    override fun serialize(encoder: Encoder, value: ChatId) {
        when (value) {
            is LChatId -> encoder.encodeLong(value.long)
            is SChatId -> encoder.encodeString(value.string)
        }
    }
}
