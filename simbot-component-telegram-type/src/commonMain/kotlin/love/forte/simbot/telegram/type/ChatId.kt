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

package love.forte.simbot.telegram.type

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import love.forte.simbot.telegram.type.ChatId.Companion.valueOf
import kotlin.jvm.JvmInline

/**
 * A Chat id that can be either [Long] or [String].
 * If it's a string, it should start with '@'.
 *
 * The description of [ChatId] is usually:
 * _Unique identifier for the target chat or username of the target supergroup or channel (in the format `@channelusername`)_.
 *
 * Note: [ChatId] is usually used for request body types and is not recommended for scenarios that require deserialization.
 */
@Suppress("MemberVisibilityCanBePrivate")
@JvmInline
@Serializable(ChatIdSerializer::class)
public value class ChatId internal constructor(internal val value: Any) {
    init {
        require(value is String || value is Long) {
            "`value` can only be String or Long"
        }
    }

    /**
     * `true` if `value` is [Long].
     */
    public val isLongValue: Boolean
        get() = value is Long

    /**
     * `true` if `value` is [String].
     */
    public val isStringValue: Boolean
        get() = value is String

    /**
     * If the value of [ChatId] is type of [Long], return long value,
     * otherwise, return `null`.
     */
    public val longOrNull: Long?
        get() = value as? Long

    /**
     * If the value of [ChatId] is type of [String], return string value,
     * otherwise, return `null`.
     */
    public val stringOrNull: String?
        get() = value as? String

    /**
     * If the value of [ChatId] is type of [Long], return long value,
     * otherwise, throw [ClassCastException].
     */
    public val long: Long
        get() = longOrNull ?: throw ClassCastException("`value` !is Long")

    /**
     * If the value of [ChatId] is type of [String], return string value,
     * otherwise, throw [ClassCastException].
     */
    public val string: String
        get() = stringOrNull ?: throw ClassCastException("`value` !is String")

    /**
     * return `value`.toString().
     */
    override fun toString(): String = value.toString()

    public companion object {
        /**
         * @throws IllegalArgumentException if [value] !is [Long] or [String].
         */
        public fun valueOf(value: Any): ChatId = ChatId(value)
    }
}

/**
 * Create a [ChatId] by [Long].
 */
public fun ChatId(value: Long): ChatId = valueOf(value)

/**
 * Create a [ChatId] by [String].
 */
public fun ChatId(value: String): ChatId = valueOf(value)


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
        when (val v = value.value) {
            is Long -> encoder.encodeLong(v)
            else -> encoder.encodeString(v.toString())
        }
    }
}
