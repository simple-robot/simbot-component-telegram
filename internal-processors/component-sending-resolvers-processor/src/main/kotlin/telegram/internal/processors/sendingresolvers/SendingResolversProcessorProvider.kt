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

package telegram.internal.processors.sendingresolvers

import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 把所有 `love.forte.simbot.component.telegram.core.message.SendingMessageResolver`
 * 的 object 实例整合到
 * `love.forte.simbot.component.telegram.core.message.allSendingResolvers` 列表中。
 *
 *
 */
class SendingResolversProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor =
        ComponentEventProcessor(environment)
}


private const val OUTPUT_PACKAGE = "love.forte.simbot.component.telegram.core.message"
private const val RESOLVER_CLASS_NAME = "$OUTPUT_PACKAGE.SendingMessageResolver"

private const val GENERATED_EVENTS_FILE_NAME = "AllSendingMessageResolvers.generated"

private const val PROPERTY_NAME = "allSendingResolvers"

private class ComponentEventProcessor(private val environment: SymbolProcessorEnvironment) : SymbolProcessor {
    private val generated = AtomicBoolean(false)

    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (!generated.compareAndSet(false, true)) {
            environment.logger.warn("Processor was used.")
            return emptyList()
        }

        val resolverClass = resolver.getClassDeclarationByName(RESOLVER_CLASS_NAME)
            ?: throw NoSuchElementException("Class: $RESOLVER_CLASS_NAME")

        val resolverType = resolverClass.asStarProjectedType()

        val implements = resolver.getAllFiles().flatMap { f ->
            f.declarations
        }.filterIsInstance<KSClassDeclaration>()
            .filter { it.classKind == ClassKind.OBJECT }
            .filter {
                resolverType.isAssignableFrom(it.asStarProjectedType())
            }
            .toList()

        val property = PropertySpec.builder(
            PROPERTY_NAME,
            LIST.parameterizedBy(resolverType.toTypeName()),
            KModifier.INTERNAL
        ).apply {
            this.initializer(
                buildCodeBlock {
                    add("listOf(")
                    for ((index, impl) in implements.withIndex()) {
                        add("%T", impl.toClassName())
                        if (index != implements.lastIndex) add(", ")
                    }
                    add(")")
                }
            )
        }.build()

        val file = FileSpec.builder(packageName = OUTPUT_PACKAGE, fileName = GENERATED_EVENTS_FILE_NAME).apply {
            addFileComment(
                """
                ****************************
                此文件内容是 **自动生成** 的
                ****************************
                """.trimIndent()
            )
            addAnnotation(
                AnnotationSpec.builder(Suppress::class)
                    .addMember("%S, %S", "ALL", "unused")
                    .build()
            )
            addProperty(property)
            indent("    ")
        }.build()

        file.writeTo(
            codeGenerator = environment.codeGenerator,
            aggregating = true,
            originatingKSFiles = buildList {
                resolverClass.containingFile?.also { add(it) }
                implements.forEach { impl ->
                    impl.containingFile?.also { add(it) }
                }
            }
        )

        return emptyList()
    }

}
