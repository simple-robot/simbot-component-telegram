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

package telegram.internal.processors.stdlibprocessor

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
import com.squareup.kotlinpoet.ParameterSpec.Companion.unnamed
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo
import java.util.concurrent.atomic.AtomicBoolean


/**
 * 为 `love.forte.simbot.telegram.api.update.Update`
 * 生成:
 *
 * ```
 * object UpdateValues {
 *   val Names: Set<String>
 *   fun getUpdateType(name: String): KClass<*>?
 * }
 * ```
 *
 * @author ForteScarlet
 */
class StdlibProcessorExtensionsProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor =
        UpdateEventProcessor(environment)
}

private const val SERIAL_NAME_ANNOTATION_TYPE = "kotlinx.serialization.SerialName"

private const val STDLIB_EVENT_PACKAGE = "love.forte.simbot.telegram.stdlib.event"
private const val BOT_CLASS_PACKAGE = "love.forte.simbot.telegram.stdlib.bot"
private const val BOT_CLASS_SIMPLE_NAME = "Bot"
private val BotClassName = ClassName(BOT_CLASS_PACKAGE, BOT_CLASS_SIMPLE_NAME)

private const val UPDATE_CLASS_PACKAGE = "love.forte.simbot.telegram.api.update"
private const val UPDATE_CLASS_SIMPLE_NAME = "Update"
private const val UPDATE_CLASS_NAME = "$UPDATE_CLASS_PACKAGE.$UPDATE_CLASS_SIMPLE_NAME"
private val UpdateClassName = ClassName(UPDATE_CLASS_PACKAGE, UPDATE_CLASS_SIMPLE_NAME)

private const val EVENT_CLASS_PACKAGE = "love.forte.simbot.telegram.stdlib.event"
private const val EVENT_CLASS_SIMPLE_NAME = "Event"
private val EventClassName = ClassName(EVENT_CLASS_PACKAGE, EVENT_CLASS_SIMPLE_NAME)

private val SubscribeSequenceClassName = ClassName(BOT_CLASS_PACKAGE, "SubscribeSequence")
private val DefaultSubscribeSequenceValue = MemberName(SubscribeSequenceClassName, "NORMAL")

private const val FILE_NAME = "BotEvents.Generated"

private val BotSubscribeExtensionMember = MemberName(BOT_CLASS_PACKAGE, "subscribe")
private val UpdateValuesClassName =
    ClassName(UPDATE_CLASS_PACKAGE, "UpdateValues")

private const val SEQUENCE_PARAM_NAME = "sequence"
private const val PROCESSOR_PARAM_NAME = "processor"

private class UpdateEventProcessor(private val environment: SymbolProcessorEnvironment) : SymbolProcessor {
    private val generated = AtomicBoolean(false)

    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (!generated.compareAndSet(false, true)) {
            environment.logger.warn("Processor was used.")
            return emptyList()
        }

        val updateClass = resolver.getClassDeclarationByName(UPDATE_CLASS_NAME)
            ?: throw NoSuchElementException("Class: $UPDATE_CLASS_NAME")

        val functions = generateFunctions(updateClass)

        val file = FileSpec.builder(BOT_CLASS_PACKAGE, FILE_NAME).apply {
            functions.forEach { addFunction(it) }
            indent("    ")
            addFileComment(
                """
                ****************************
                此文件内容是 **自动生成** 的
                ****************************
                """.trimIndent()
            )
        }.build()

        file.writeTo(
            codeGenerator = environment.codeGenerator,
            aggregating = true,
            originatingKSFiles = updateClass.containingFile?.let { listOf(it) } ?: emptyList()
        )

        return emptyList()
    }

    private data class EventProperty(val name: String, val declaration: KSPropertyDeclaration)

    private fun generateFunctions(updateClass: KSClassDeclaration): List<FunSpec> {
        return updateClass.getDeclaredProperties()
            .filter { it.type.resolve().isMarkedNullable }
            .map {
                val annoValue = it.annotations
                    .find { an ->
                        an.annotationType.resolve().toClassName().canonicalName == SERIAL_NAME_ANNOTATION_TYPE
                    }
                    ?.arguments
                    ?.firstOrNull { a -> a.name?.asString() == "value" }
                    ?.value

                val name = annoValue?.toString() ?: it.simpleName.asString()

                EventProperty(name, it)
            }
            .map {
                generateFunction(it)
            }
            .toList()
    }


    /**
     * ```kotlin
     * inline Bot.onXxx(
     * sequence: SubscribeSequence = SubscribeSequence.NORMAL,
     * crossinline processor: suspend (Update, T) -> Unit
     * ) {
     *      bot.subscribe<T>(NAME, sequence, processor)
     * }
     *
     * ```
     */
    private fun generateFunction(ep: EventProperty): FunSpec {
        val propertyName = ep.declaration.simpleName.asString()
        val eventType = ep.declaration.type.toTypeName().copy(nullable = false)

        return FunSpec.builder(onFunName(propertyName)).apply {
            addModifiers(KModifier.INLINE)
            receiver(BotClassName)
            addParameter(
                ParameterSpec.builder(SEQUENCE_PARAM_NAME, SubscribeSequenceClassName)
                    .defaultValue("%M", DefaultSubscribeSequenceValue)
                    .build()
            )
            addParameter(
                ParameterSpec.builder(
                    PROCESSOR_PARAM_NAME,
                    LambdaTypeName.get(
                        receiver = null,
                        parameters = listOf(
                            unnamed(EventClassName),
                            ParameterSpec(propertyName, eventType),
                        ),
                        returnType = UNIT
                    ).copy(suspending = true)
                ).addModifiers(KModifier.CROSSINLINE).build()
            )

            val nameMember = MemberName(UpdateValuesClassName, constantName(ep.name))

            addCode(
                CodeBlock.builder()
                    // subscribe<Type>(NAME, sequence, processor)
                    .addStatement(
                        "%M<%T>(%M, %L, %L)",
                        BotSubscribeExtensionMember,
                        eventType,
                        nameMember,
                        SEQUENCE_PARAM_NAME,
                        PROCESSOR_PARAM_NAME
                    )
                    .build()
            )

            addKdoc("Register processor for [%T] named [%M]\n\n", eventType, nameMember)
            addKdoc(
                """
                Equivalent to the following:
                ```kotlin
                // process or preProcess
                bot.subscribe<%T>(sequence, %M) { event, %L ->
                    // ...
                }
                ```
                
                @see %M
                @see %M
                """.trimIndent(),
                eventType,
                nameMember,
                propertyName,
                BotSubscribeExtensionMember,
                MemberName(UpdateClassName, propertyName)
            )

        }.build()
    }


    private fun onFunName(name: String): String = "on${name.replaceFirstChar { it.uppercase() }}"
    private fun constantName(name: String): String = "${name.uppercase()}_NAME"
}

