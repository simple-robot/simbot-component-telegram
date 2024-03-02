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

package telegram.internal.processors.updateevents

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getDeclaredProperties
import com.google.devtools.ksp.getKotlinClassByName
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.jvm.jvmField
import com.squareup.kotlinpoet.jvm.jvmMultifileClass
import com.squareup.kotlinpoet.jvm.jvmName
import com.squareup.kotlinpoet.jvm.jvmStatic
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.reflect.KClass


/**
 * 为 `love.forte.simbot.telegram.api.update.Update`
 * 生成:
 * object UpdateValues {
 *   val Names: Set<String>
 *   fun getUpdateType(name: String): KClass<*>?
 * }
 * -
 *
 * @author ForteScarlet
 */
class UpdateEventProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor =
        UpdateEventProcessor(environment)
}

private val ILL_ARG_EX = ClassName("kotlin", "IllegalArgumentException")

private const val SERIAL_NAME_ANNOTATION_TYPE = "kotlinx.serialization.SerialName"
private const val UPDATE_PACKAGE = "love.forte.simbot.telegram.api.update"
private const val UPDATE_CLASS_NAME = "love.forte.simbot.telegram.api.update.Update"
private const val UPDATE_VALUES_CLASS_NAME = "UpdateValues"
private const val FILE_NAME = "Updates.Generated"
private const val FILE_JVM_NAME = "Updates"

private const val NAME_PROPERTY_NAME = "Names"
private const val GET_UPDATE_TYPE_FUNC_NAME = "getUpdateType"

private class UpdateEventProcessor(private val environment: SymbolProcessorEnvironment) : SymbolProcessor {
    private val generated = AtomicBoolean(false)

    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (!generated.compareAndSet(false, true)) {
            environment.logger.warn("Processor was used.")
            return emptyList()
        }

        val updateClass = resolver.getKotlinClassByName(UPDATE_CLASS_NAME)
            ?: throw NoSuchElementException("Class: $UPDATE_CLASS_NAME")

        val updateValuesObjectBuilder = TypeSpec.objectBuilder(ClassName(UPDATE_PACKAGE, UPDATE_VALUES_CLASS_NAME))
        updateValuesObjectBuilder.addNamesProperty(updateClass)

        updateValuesObjectBuilder.addGetUpdateTypeFunc(updateClass)

        val updateValuesFile = FileSpec.builder(UPDATE_PACKAGE, FILE_NAME)
            .jvmMultifileClass()
            .jvmName(FILE_JVM_NAME)
            .addFileComment("\n此文件内容均为 **自动生成** 的\n")
            .addType(updateValuesObjectBuilder.build())
            .build()

        updateValuesFile.writeTo(
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
                val annoValue = it.annotations
                    .find { an ->
                        an.annotationType.resolve().toClassName().canonicalName == SERIAL_NAME_ANNOTATION_TYPE
                    }
                    ?.arguments
                    ?.firstOrNull { a -> a.name?.asString() == "value" }
                    ?.value

                val name = annoValue?.toString() ?: it.simpleName.asString()
                it to name
            }
            .toList()
    }

    private fun TypeSpec.Builder.addNamesProperty(updateClass: KSClassDeclaration) {
        val names = updateClass
            .optionalPropertiesWithNames()
            .mapTo(mutableSetOf()) { it.second }

        val typeName = updateClass.asStarProjectedType().toTypeName()

        addProperty(
            PropertySpec.builder(NAME_PROPERTY_NAME, SET.parameterizedBy(STRING))
                .initializer("setOf(${names.joinToString(", ") { "%S" }})", *names.toTypedArray())
                .addKdoc(
                    """
                    All optional parameters' name in [%T]
                    
                    @see %T
                """.trimIndent(), typeName, typeName
                )
                .jvmField()
                .build()
        )
    }

    private fun TypeSpec.Builder.addGetUpdateTypeFunc(updateClass: KSClassDeclaration) {
        val optionalPropertiesWithNames =
            updateClass.optionalPropertiesWithNames()

        val getUpdateTypeFun = FunSpec.builder(GET_UPDATE_TYPE_FUNC_NAME).apply {
            jvmStatic()
            addKdoc("@throws %T If name not in [Names]", ILL_ARG_EX)

            val nameParam = ParameterSpec.builder("name", STRING).build()
            addParameter(nameParam)
            returns(KClass::class.asTypeName().parameterizedBy(STAR))

            addCode(CodeBlock.builder().apply {
                beginControlFlow("return when(${nameParam.name})")

                for ((parameter, name) in optionalPropertiesWithNames) {
                    addStatement("%S -> %T::class", name, parameter.type.toTypeName().copy(nullable = false))

                }

                addStatement(
                    "else -> throw %T(%P)",
                    ILL_ARG_EX,
                    "Unknown name: \$${nameParam.name}"
                )
                endControlFlow()
            }.build())
        }.build()

        addFunction(getUpdateTypeFun)
    }


}
