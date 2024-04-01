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

package telegram.internal.processors.componentevents

import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.getDeclaredProperties
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo
import java.util.concurrent.atomic.AtomicBoolean

class ComponentEventProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor =
        ComponentEventProcessor(environment)
}

// private const val SERIAL_NAME_ANNOTATION_TYPE = "kotlinx.serialization.SerialName"
private const val UPDATE_PACKAGE = "love.forte.simbot.telegram.api.update"

private const val UPDATE_CLASS_NAME = "$UPDATE_PACKAGE.Update"
private val UpdateClassName = ClassName(UPDATE_PACKAGE, "Update")

private const val EVENT_PACKAGE = "love.forte.simbot.component.telegram.event"

private val TelegramEventClassName = ClassName(EVENT_PACKAGE, "TelegramEvent")

private const val GENERATED_EVENTS_FILE_NAME = "TelegramEvents.generated"

/**
 * ```kotlin
 * public val sourceContent: Any
 * ```
 */
private const val TELEGRAM_EVENT_PROPERTY_SOURCE_CONTENT_NAME = "sourceContent"

private class ComponentEventProcessor(private val environment: SymbolProcessorEnvironment) : SymbolProcessor {
    private val generated = AtomicBoolean(false)

    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (!generated.compareAndSet(false, true)) {
            environment.logger.warn("Processor was used.")
            return emptyList()
        }

        val updateClass = resolver.getClassDeclarationByName(UPDATE_CLASS_NAME)
            ?: throw NoSuchElementException("Class: $UPDATE_CLASS_NAME")

        val optionalPropertiesWithNames = updateClass.optionalPropertiesWithNames()

        val eventTypes = generateEvents(optionalPropertiesWithNames)

        val file = FileSpec.builder(packageName = EVENT_PACKAGE, fileName = GENERATED_EVENTS_FILE_NAME).apply {
            addTypes(eventTypes)
            addFileComment(
                """
                ****************************
                此文件内容是 **自动生成** 的
                ****************************
            """.trimIndent()
            )
            indent("    ")
        }.build()

        file.writeTo(
            codeGenerator = environment.codeGenerator,
            aggregating = true,
            originatingKSFiles = updateClass.containingFile?.let { listOf(it) } ?: emptyList()
        )

        return emptyList()
    }

    private fun KSClassDeclaration.optionalPropertiesWithNames(): List<Pair<KSPropertyDeclaration, String>> {
        return getDeclaredProperties()
            .filter { it.type.resolve().isMarkedNullable }
            .map {
                it to it.simpleName.asString()
            }
            .toList()
    }


    /**
     * 生成：
     *
     * ```kotlin
     * interface TelegramXxxEvent : TelegramEvent {
     *     override val sourceContent: Xxx // 实际上的事件类型, not null
     * }
     * ```
     */
    private fun generateEvents(optionalPropertiesWithNames: List<Pair<KSPropertyDeclaration, String>>): List<TypeSpec> {
        return optionalPropertiesWithNames.map { (property, name) ->
            val propertyTypeName = property.type.toTypeName().copy(nullable = false)
            val memberName = MemberName(UpdateClassName, name)

            TypeSpec.interfaceBuilder(name.toTelegramEventName()).apply {
                addSuperinterface(TelegramEventClassName)
                addProperty(
                    PropertySpec.builder(
                        name = TELEGRAM_EVENT_PROPERTY_SOURCE_CONTENT_NAME,
                        type = propertyTypeName,
                        KModifier.OVERRIDE, KModifier.PUBLIC
                    )
                        .addKdoc(
                            "Source content from [%M] with type [%T]\n\n",
                            memberName,
                            propertyTypeName
                        )
                        .addKdoc("@see %M", memberName)
                        .build()
                )

                addKdoc("Base wrapper type for event [%M] ", memberName)
                addKdoc("based on [%T].", TelegramEventClassName)
                addKdoc("\n\n")
                addKdoc("@see %T\n", TelegramEventClassName)
                addKdoc("@see %T\n", UpdateClassName)

            }.build()
        }
    }
}

private fun String.toTelegramEventName(): String = "BasicTelegram${replaceFirstChar(Char::uppercaseChar)}Event"
