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

package telegram.internal.processors.includemessageelements

import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.isAbstract
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.writeTo
import java.util.concurrent.atomic.AtomicBoolean


/**
 *
 * @author ForteScarlet
 */
class IncludeMessageElementsProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor =
        IncludeMessageElementsProcessor(environment)
}

// simbot Message.Element class
// private val SimbotMessageElementClassName = ClassName("love.forte.simbot.message", "Message", "Element")

private val PolymorphicModuleBuilderClassName =
    ClassName("kotlinx.serialization.modules", "PolymorphicModuleBuilder")

// 此组件下，实现 Message.Element 的顶级类型
private const val BASE_MESSAGE_ELEMENT_CLASS_NAME =
    "love.forte.simbot.component.telegram.core.message.TelegramMessageElement"
private val BaseMessageElementClassName =
    ClassName("love.forte.simbot.component.telegram.core.message", "TelegramMessageElement")

// 生成的函数会在此包下
private const val COMPONENT_PACKAGE = "love.forte.simbot.component.telegram.core.message"

// ktx 可序列化标记
private const val SERIALIZABLE_ANNOTATION = "kotlinx.serialization.Serializable"

private const val FUNCTION_NAME = "includeAllComponentMessageElementImpls"

private const val FILE_NAME = "TelegramMessageElements.generated"

private class IncludeMessageElementsProcessor(val environment: SymbolProcessorEnvironment) : SymbolProcessor {
    private val generated = AtomicBoolean(false)

    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (!generated.compareAndSet(false, true)) {
            environment.logger.warn("Processor was used.")
            return emptyList()
        }

        val baseDeclaration = resolver.getClassDeclarationByName(BASE_MESSAGE_ELEMENT_CLASS_NAME)
            ?: throw NoSuchElementException("Class: $BASE_MESSAGE_ELEMENT_CLASS_NAME")

        val baseDeclarationType = baseDeclaration.asStarProjectedType()

        val allKritorMessageElementImpls = resolver.getSymbolsWithAnnotation(SERIALIZABLE_ANNOTATION)
            .filterIsInstance<KSClassDeclaration>()
            // 是一个可序列化的具体的类
            .filter { !it.isAbstract() }
            // 是 BaseMessageElement 的子类
            .filter { baseDeclarationType.isAssignableFrom(it.asStarProjectedType()) }
            .onEach {
                environment.logger.info("resolved message element impl: $it", it)
            }
            .toList()


        val function = generateIncludeFunction(allKritorMessageElementImpls)

        val generatedFile = FileSpec.builder(COMPONENT_PACKAGE, FILE_NAME).apply {
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
            addFunction(function)
            indent("    ")
        }.build()

        generatedFile.writeTo(
            codeGenerator = environment.codeGenerator,
            aggregating = true,
            originatingKSFiles = buildList {
                for (impl in allKritorMessageElementImpls) {
                    baseDeclaration.containingFile?.also { add(it) }
                    impl.containingFile?.also { add(it) }
                }
            }
        )

        return emptyList()
    }

    /**
     * 生成：
     *
     * ```kotlin
     * internal fun kotlinx.serialization.modules.PolymorphicModuleBuilder<Message.Element>.includeAllComponentMessageElementImpls() {
     *     subclass(...)
     *     subclass(...)
     *     subclass(...)
     * }
     *```
     */
    private fun generateIncludeFunction(impls: List<KSClassDeclaration>): FunSpec {
        // kotlinx.serialization.modules.subclass
        val memberName = MemberName("kotlinx.serialization.modules", "subclass")

        return FunSpec.builder(FUNCTION_NAME).apply {
            addModifiers(KModifier.INTERNAL)
            receiver(PolymorphicModuleBuilderClassName.parameterizedBy(BaseMessageElementClassName))
            for (impl in impls) {
                addCode("%M(%T.serializer())\n", memberName, impl.toClassName())
            }
        }.build()
    }
}
