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

package love.forte.simbot.telegram.api.utils

import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.http.content.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.StringFormat
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.modules.SerializersModule
import love.forte.simbot.common.ktor.inputfile.InputFile


internal fun interface FormParamHeaderHandler {
    fun handle(
        descriptor: SerialDescriptor,
        name: String,
        index: Int,
        value: Any
    ): Headers

    companion object {
        inline fun isInputFileAndNotStringValueInputFile(
            supportStringValueInputFile: Boolean = false,
            crossinline block: (
                descriptor: SerialDescriptor,
                name: String,
                index: Int,
                value: InputFile
            ) -> Headers
        ): FormParamHeaderHandler =
            FormParamHeaderHandler { descriptor, name, index, value ->
                if (value is InputFile && (supportStringValueInputFile || value !is StringValueInputFile)) {
                    block(descriptor, name, index, value)
                } else {
                    Headers.Empty
                }
            }

        inline fun isInputFileAndNotStringValueInputFileWithoutParams(
            supportStringValueInputFile: Boolean = false,
            crossinline block: () -> Headers
        ): FormParamHeaderHandler = isInputFileAndNotStringValueInputFile(
            supportStringValueInputFile
        ) { _, _, _, _ -> block() }

    }
}

private val DefaultFormParamHeaderHandler: FormParamHeaderHandler =
    FormParamHeaderHandler { _, _, _, _ ->
        Headers.Empty
    }

/**
 * 一个将实体处理为 List<[PartData]> 的序列化器。
 * 如果属性为 [InputFile] 则会特殊处理。
 *
 * @author ForteScarlet
 */
internal open class KtorFormSerializer<T>(
    private val serializer: KSerializer<T>,
    private val structFormat: StringFormat,
    private val headerHandler: FormParamHeaderHandler = DefaultFormParamHeaderHandler
) : SerializationStrategy<T> {

    override val descriptor: SerialDescriptor
        get() = serializer.descriptor

    override fun serialize(encoder: Encoder, value: T) {
        encoder.encodeSerializableValue(serializer, value)
    }

    fun serialize(value: T, headerHandler: FormParamHeaderHandler? = null): List<FormPart<*>> {
        val parts = mutableListOf<FormPart<*>>()
        serialize(
            KtorFormSerializerEncoder(
                structFormat,
                parts,
                headerHandler ?: this.headerHandler
            ),
            value
        )

        return parts
    }
}


@OptIn(ExperimentalSerializationApi::class)
private class KtorFormSerializerEncoder(
    private val structFormat: StringFormat,
    private val parts: MutableList<FormPart<*>>,
    private val headerHandler: FormParamHeaderHandler
) : Encoder {
    private fun addPart(part: FormPart<*>) {
        parts.add(part)
    }

    override val serializersModule: SerializersModule
        get() = structFormat.serializersModule

    override fun beginStructure(descriptor: SerialDescriptor): CompositeEncoder {
        return CompositeEncoderImpl(descriptor)
    }

    private inner class CompositeEncoderImpl(
        private val descriptor: SerialDescriptor,
    ) : CompositeEncoder, Encoder {
        override val serializersModule: SerializersModule
            get() = this@KtorFormSerializerEncoder.serializersModule

        override fun encodeBooleanElement(descriptor: SerialDescriptor, index: Int, value: Boolean) {
            encodeStringElement(descriptor, index, value.toString())
        }

        override fun encodeByteElement(descriptor: SerialDescriptor, index: Int, value: Byte) {
            encodeStringElement(descriptor, index, value.toString())
        }

        override fun encodeCharElement(descriptor: SerialDescriptor, index: Int, value: Char) {
            encodeStringElement(descriptor, index, value.toString())
        }

        override fun encodeDoubleElement(descriptor: SerialDescriptor, index: Int, value: Double) {
            encodeStringElement(descriptor, index, value.toString())
        }

        override fun encodeFloatElement(descriptor: SerialDescriptor, index: Int, value: Float) {
            encodeStringElement(descriptor, index, value.toString())
        }

        override fun encodeInlineElement(descriptor: SerialDescriptor, index: Int): Encoder {
            return CompositeEncoderImpl(descriptor.getElementDescriptor(index))
        }

        override fun encodeIntElement(descriptor: SerialDescriptor, index: Int, value: Int) {
            encodeStringElement(descriptor, index, value.toString())
        }

        override fun encodeLongElement(descriptor: SerialDescriptor, index: Int, value: Long) {
            encodeStringElement(descriptor, index, value.toString())
        }

        @ExperimentalSerializationApi
        override fun <T : Any> encodeNullableSerializableElement(
            descriptor: SerialDescriptor,
            index: Int,
            serializer: SerializationStrategy<T>,
            value: T?
        ) {
            value?.also { v ->
                encodeSerializableElement(descriptor, index, serializer, v)
            }
        }

        override fun <T> encodeSerializableElement(
            descriptor: SerialDescriptor,
            index: Int,
            serializer: SerializationStrategy<T>,
            value: T
        ) {
            if (value is InputFile) {
                val name = descriptor.getElementName(index)
                addPart(
                    value.toFormPart(
                        name,
                        headerHandler.handle(
                            descriptor,
                            name,
                            index,
                            value
                        )
                    )
                )
            } else {
                encodeStringElement(
                    descriptor,
                    index,
                    structFormat.encodeToString(serializer, value)
                )
            }
        }

        override fun encodeShortElement(descriptor: SerialDescriptor, index: Int, value: Short) {
            encodeStringElement(descriptor, index, value.toString())
        }

        override fun encodeStringElement(descriptor: SerialDescriptor, index: Int, value: String) {
            val name = descriptor.getElementName(index)
            addPart(
                FormPart(
                    descriptor.getElementName(index),
                    value,
                    headerHandler.handle(
                        descriptor,
                        name,
                        index,
                        value
                    )
                )
            )
        }


        override fun endStructure(descriptor: SerialDescriptor) {
            // End.
        }

        // The encoder
        override fun beginStructure(descriptor: SerialDescriptor): CompositeEncoder {
            return CompositeEncoderImpl(descriptor)
        }

        override fun encodeBoolean(value: Boolean) {
            encodeString(value.toString())
        }

        override fun encodeByte(value: Byte) {
            encodeString(value.toString())
        }

        override fun encodeChar(value: Char) {
            encodeString(value.toString())
        }

        override fun encodeDouble(value: Double) {
            encodeString(value.toString())
        }

        override fun encodeEnum(enumDescriptor: SerialDescriptor, index: Int) {
            encodeString(enumDescriptor.getElementName(index))
        }

        override fun encodeFloat(value: Float) {
            encodeString(value.toString())
        }

        override fun encodeInline(descriptor: SerialDescriptor): Encoder {
            return CompositeEncoderImpl(descriptor.getElementDescriptor(0))
        }

        override fun encodeInt(value: Int) {
            encodeString(value.toString())
        }

        override fun encodeLong(value: Long) {
            encodeString(value.toString())
        }

        @ExperimentalSerializationApi
        override fun encodeNull() {
            // nothing.
        }

        override fun encodeShort(value: Short) {
            encodeString(value.toString())
        }

        override fun encodeString(value: String) {
            val name = descriptor.serialName
            addPart(
                FormPart(
                    name,
                    value,
                    headerHandler.handle(
                        descriptor,
                        name,
                        0,
                        value
                    )
                )
            )
        }
    }

    override fun encodeBoolean(value: Boolean) {
        // nothing.
    }

    override fun encodeByte(value: Byte) {
        // nothing.
    }

    override fun encodeChar(value: Char) {
        // nothing.
    }

    override fun encodeDouble(value: Double) {
        // nothing.
    }

    override fun encodeEnum(enumDescriptor: SerialDescriptor, index: Int) {
        // nothing.
    }

    override fun encodeFloat(value: Float) {
        // nothing.
    }

    override fun encodeInline(descriptor: SerialDescriptor): Encoder {
        return CompositeEncoderImpl(descriptor.getElementDescriptor(0))
    }

    override fun encodeInt(value: Int) {
        // nothing.
    }

    override fun encodeLong(value: Long) {
        // nothing.
    }

    @ExperimentalSerializationApi
    override fun encodeNull() {
        // nothing.
    }

    override fun encodeShort(value: Short) {
        // nothing.
    }

    override fun encodeString(value: String) {
        // nothing.
    }
}
