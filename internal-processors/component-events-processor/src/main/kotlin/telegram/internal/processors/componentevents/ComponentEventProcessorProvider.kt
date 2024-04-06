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
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 根据 `Update` 中所有事件生成对应的基础事件类型 `BasicTelegramXxxEvent`, `Xxx` 是事件名
 * 还要根据 `Update` 的事件 **类型** 生成跟类型相关的 `BasicTelegramXxxTypeEvent`, `Xxx` 是类型。
 *
 */
class ComponentEventProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor =
        ComponentEventProcessor(environment)
}


private const val UPDATE_PACKAGE = "love.forte.simbot.telegram.api.update"

private const val UPDATE_CLASS_NAME = "$UPDATE_PACKAGE.Update"
private val UpdateClassName = ClassName(UPDATE_PACKAGE, "Update")

private const val EVENT_PACKAGE = "love.forte.simbot.component.telegram.core.event"

private val TelegramEventClassName = ClassName(EVENT_PACKAGE, "TelegramEvent")
private val GeneratedAnnotationClassName = ClassName(EVENT_PACKAGE, "GeneratedEvent")

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
     * ```kotlin
     * interface TypeBasedTelegramXxxEvent : TelegramEvent {
     *     override val sourceContent: Xxx // 实际上的事件类型, not null
     * }
     * ```
     *
     * ```kotlin
     * interface BasicTelegramXxxEvent : TypeBasedTelegramXxxEvent {
     *     override val sourceContent: Xxx // 实际上的事件类型, not null
     * }
     * ```
     */
    private fun generateEvents(optionalPropertiesWithNames: List<Pair<KSPropertyDeclaration, String>>): List<TypeSpec> {
        data class TypeBasedEventData(val property: KSPropertyDeclaration, val typeName: TypeName, val typeBuilder: TypeSpec.Builder)
        // val eventTypesWithTypeKey = mutableMapOf<>()
        val typeBasedEventTypes = optionalPropertiesWithNames.asSequence()
            .map { (property, _) ->
                property to property.type.toTypeName().copy(nullable = false)
            }
            .distinctBy { (_, typeName) -> typeName }
            .map { (property, typeName) ->
                val typeSimpleName = property.type.resolve().toClassName().simpleName
                val inter = TypeSpec.interfaceBuilder(typeSimpleName.toTelegramTypeEventName()).apply {
                    addAnnotation(GeneratedAnnotationClassName)
                    addSuperinterface(TelegramEventClassName)
                    addProperty(
                        PropertySpec.builder(
                            name = TELEGRAM_EVENT_PROPERTY_SOURCE_CONTENT_NAME,
                            type = typeName,
                            KModifier.OVERRIDE, KModifier.PUBLIC
                        )
                            .addKdoc(
                                "Source content with type [%T]\n\n",
                                typeName
                            )
                            .addKdoc("@see %T", typeName)
                            .build()
                    )

                    addKdoc("Basic wrapper type for type [%T] ", typeName)
                    addKdoc("based on [%T].", TelegramEventClassName)
                    addKdoc("\n\n")
                    addKdoc("@see %T\n", TelegramEventClassName)
                    addKdoc("@see %T\n", UpdateClassName)

                }

                TypeBasedEventData(property, typeName, inter)
            }.associateBy { data -> data.typeName }

        val basicTypeList = optionalPropertiesWithNames.map { (property, name) ->
            val propertyTypeName = property.type.toTypeName().copy(nullable = false)
            val memberName = MemberName(UpdateClassName, name)
            val eventName = name.toTelegramEventName()

            TypeSpec.interfaceBuilder(eventName).apply {
                typeBasedEventTypes[propertyTypeName]?.let { data ->
                    val typeSimpleName = data.property.type.resolve().toClassName().simpleName
                    addSuperinterface(ClassName(EVENT_PACKAGE, typeSimpleName.toTelegramTypeEventName()))
                    data.typeBuilder.addKdoc("@see %T\n", ClassName(EVENT_PACKAGE, eventName))
                } ?: run {
                    addSuperinterface(TelegramEventClassName)
                }

                addAnnotation(GeneratedAnnotationClassName)
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
                superinterfaces.keys.forEach {
                    addKdoc("@see %T\n", it)
                }

            }.build()
        }

        return buildList {
            typeBasedEventTypes.values.mapTo(this) { data -> data.typeBuilder.build() }
            addAll(basicTypeList)
        }
    }
}

private fun String.toTelegramEventName(): String = "BasicTelegram${replaceFirstChar(Char::uppercaseChar)}Event"
private fun String.toTelegramTypeEventName(): String = "TypeBasedTelegram${this}Event"
