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
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.jvm.jvmField
import com.squareup.kotlinpoet.jvm.jvmMultifileClass
import com.squareup.kotlinpoet.jvm.jvmName
import com.squareup.kotlinpoet.jvm.jvmStatic
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo
import java.time.Instant
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

private val IllegalArgumentExceptionClassName = ClassName("kotlin", "IllegalArgumentException")
private val UnknownUpdateFieldExceptionClassName =
    ClassName("love.forte.simbot.telegram.api", "UnknownUpdatedFieldException")

private const val SERIAL_NAME_ANNOTATION_TYPE = "kotlinx.serialization.SerialName"
private const val UPDATE_PACKAGE = "love.forte.simbot.telegram.api.update"

private const val UPDATE_CLASS_NAME = "$UPDATE_PACKAGE.Update"
private val UpdateClassName = ClassName(UPDATE_PACKAGE, "Update")

private const val UPDATE_VALUES_CLASS_NAME = "UpdateValues"
private const val FILE_NAME = "Updates.Generated"
private const val FILE_JVM_NAME = "Updates"

private const val NAME_PROPERTY_NAME = "Names"

/**
 * 生成一个函数，
 * 根据 `name` 返回对应
 * `Update` 中字段的类型。
 *
 * ```kotlin
 * // throws kotlin.IllegalArgumentException
 * fun getUpdateType(name: String): KClass<*>
 * ```
 *
 */
private const val GET_UPDATE_TYPE_FUNC_NAME = "getUpdateType"

/**
 * 生成一个 inline 函数:
 *
 * ```kotlin
 * @JvmName("resolveTo")
 * inline fun <T> resolveTo(update, block: (name, content) -> T): T // or throw UnknownUpdatedFieldException
 * ```
 */
private const val RESOLVE_FUN_NAME = "resolveTo"

// Divider

private const val DIVIDER_CLASS_NAME = "UpdateDivider"
private val UpdateDividerClassName = ClassName(UPDATE_PACKAGE, DIVIDER_CLASS_NAME)
private val SuspendUpdateDividerClassName = ClassName(UPDATE_PACKAGE, "Suspendable$DIVIDER_CLASS_NAME")
private const val DIVIDER_NAME_PARAM_NAME = "name"
private const val DIVIDER_VALUE_PARAM_NAME = "value"
private const val DIVIDER_UPDATE_PARAM_NAME = "update"
private const val DIVIDER_CONTEXT_PARAM_NAME = "context"

private class UpdateEventProcessor(private val environment: SymbolProcessorEnvironment) : SymbolProcessor {
    private val generated = AtomicBoolean(false)

    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (!generated.compareAndSet(false, true)) {
            environment.logger.warn("Processor was used.")
            return emptyList()
        }

        val updateClass = resolver.getClassDeclarationByName(UPDATE_CLASS_NAME)
            ?: throw NoSuchElementException("Class: $UPDATE_CLASS_NAME")

        val updateValuesObjectBuilder = TypeSpec.objectBuilder(ClassName(UPDATE_PACKAGE, UPDATE_VALUES_CLASS_NAME))
        updateValuesObjectBuilder.addKdoc(
            "Some generated constants and helper methods related to [%T]\n\n",
            UpdateClassName
        )
        updateValuesObjectBuilder.addKdoc("(Automatically generated at %L)", Instant.now().toString())

        val optionalPropertiesWithNames = updateClass.optionalPropertiesWithNames()

        val nameConstants = updateValuesObjectBuilder.addNameConstants(optionalPropertiesWithNames, updateClass)
        updateValuesObjectBuilder.addNamesProperty(updateClass, nameConstants)
        updateValuesObjectBuilder.addGetUpdateTypeFun(optionalPropertiesWithNames)
        updateValuesObjectBuilder.addResolveToFun(optionalPropertiesWithNames)

        val updateValuesObject = updateValuesObjectBuilder.build()

        val dividerClass = generateUpdateDivider(
            UpdateDividerClassName,
            false,
            optionalPropertiesWithNames
        )
        val suspendDividerClass = generateUpdateDivider(
            SuspendUpdateDividerClassName,
            true,
            optionalPropertiesWithNames
        )

        val updateValuesFile = FileSpec.builder(UPDATE_PACKAGE, FILE_NAME)
            .jvmMultifileClass()
            .jvmName(FILE_JVM_NAME)
            .addFileComment(
                """
                ****************************
                此文件内容是 **自动生成** 的
                ****************************
            """.trimIndent()
            )
            .addType(updateValuesObject)
            .addType(dividerClass)
            .addType(suspendDividerClass)
            .indent("    ")
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

    private fun TypeSpec.Builder.addResolveToFun(
        optionalPropertiesWithNames: List<Pair<KSPropertyDeclaration, String>>
    ) {
        /*
         *  inline fun <T> resolveTo(update, block: (name, content) -> T): T {
         *     update.a?.also { return block('*_NAME', it) }
         *     ...
         *     throw UnknownUpdatedFieldException()
         *  }
         */

        val tv = TypeVariableName("T")

        val func = FunSpec.builder(RESOLVE_FUN_NAME).apply {
            jvmName(RESOLVE_FUN_NAME)
            jvmStatic()
            addModifiers(KModifier.INLINE)
            addTypeVariable(tv)

            addParameter("update", UpdateClassName)
            val lambda = LambdaTypeName.get(
                receiver = null,
                parameters = listOf(
                    ParameterSpec.unnamed(STRING),
                    ParameterSpec.unnamed(ANY)
                ),
                returnType = tv
            )

            addParameter("block", lambda)

            for ((property, name) in optionalPropertiesWithNames) {
                addStatement(
                    "update.%N?.also { return block(%N, it) }",
                    property.simpleName.asString(),
                    constantName(name)
                )
            }

            addStatement("throw %T()", UnknownUpdateFieldExceptionClassName)

            returns(tv)

            addKdoc(
                "Find the first optional property that is not null based on [%T], " +
                        "and use [block] to process the serialized name and value of this property." +
                        "\n\n", UpdateClassName
            )
            addKdoc(
                "@throws %T If no optional properties that are not null are found in [%T]",
                UnknownUpdateFieldExceptionClassName,
                UpdateClassName
            )
        }

        addFunction(func.build())

    }

    private fun TypeSpec.Builder.addNameConstants(
        optionalPropertiesWithNames: List<Pair<KSPropertyDeclaration, String>>,
        updateClass: KSClassDeclaration
    ): List<PropertySpec> {
        val nameConstants = mutableListOf<PropertySpec>()

        for ((property, name) in optionalPropertiesWithNames) {
            val member = MemberName(updateClass.toClassName(), property.simpleName.asString())

            addProperty(
                PropertySpec.builder(
                    constantName(name), STRING,
                    KModifier.CONST
                )
                    .addKdoc("The serialized name constant of [%M]\n\n", member)
                    .addKdoc("@see %M", member)
                    .initializer("%S", name)
                    .build()
                    .also {
                        nameConstants.add(it)
                    }
            )
        }

        return nameConstants
    }

    private fun TypeSpec.Builder.addNamesProperty(
        updateClass: KSClassDeclaration,
        nameConstants: List<PropertySpec>
    ) {
        val typeName = updateClass.asStarProjectedType().toTypeName()

        addProperty(
            PropertySpec.builder(NAME_PROPERTY_NAME, SET.parameterizedBy(STRING))
                .initializer("setOf(${nameConstants.joinToString(", ") { it.name }})")
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

    private fun TypeSpec.Builder.addGetUpdateTypeFun(
        optionalPropertiesWithNames: List<Pair<KSPropertyDeclaration, String>>
    ) {
        val (fp, fn) = optionalPropertiesWithNames.first()
        val getUpdateTypeFun = FunSpec.builder(GET_UPDATE_TYPE_FUNC_NAME).apply {
            jvmStatic()
            addKdoc("Gets the type of the [%T] property based on its serialized name.\n", UpdateClassName)
            addKdoc("For example: `%L` -> [%T]\n\n", fn, fp.type.toTypeName())
            addKdoc("@throws %T If name not in [Names]", IllegalArgumentExceptionClassName)

            val nameParam = ParameterSpec.builder("name", STRING).build()
            addParameter(nameParam)
            returns(KClass::class.asTypeName().parameterizedBy(STAR))

            addCode(CodeBlock.builder().apply {
                beginControlFlow("return when(${nameParam.name})")

                for ((parameter, name) in optionalPropertiesWithNames) {
                    addStatement(
                        "%N -> %T::class",
                        constantName(name),
                        parameter.type.toTypeName().copy(nullable = false)
                    )

                }

                addStatement(
                    "else -> throw %T(%P)",
                    IllegalArgumentExceptionClassName,
                    "Unknown name: \$${nameParam.name}"
                )
                endControlFlow()
            }.build())
        }.build()

        addFunction(getUpdateTypeFun)
    }


    private fun constantName(name: String) = "${name.uppercase()}_NAME"

    /**
     *
     * 为 Update中所有的事件实现一个 Divider。
     *
     * ```
     * abstract class UpdateEventDivider {
     *     abstract fun onMismatchEvent(name: String, value: xxx, update: Update?) {
     *     }
     *     open fun onXxx(name: String, value: xxx, update: Update?) {
     *         onMismatchEvent(name, value)
     *     }
     *
     *     ...
     *
     *     final fun accept(update: Update? = null, name: String, value: Any) {
     *         when (name) {
     *             // ...
     *         }
     *     }
     *     final fun accept(update: Update) {
     *         // ...
     *     }
     * }
     * ```
     *
     * @author ForteScarlet
     */
    private fun generateUpdateDivider(
        name: ClassName,
        suspend: Boolean,
        optionalPropertiesWithNames: List<Pair<KSPropertyDeclaration, String>>,
        ): TypeSpec {
        val tv = TypeVariableName("C")
        return TypeSpec.classBuilder(name).apply {
            addTypeVariable(tv)
            addModifiers(KModifier.PUBLIC, KModifier.ABSTRACT)
            // onMismatchEvent
            addFunction(updateDividerOnMismatchEventFun(suspend, tv))
            // visitFunctions

            for ((property, _) in optionalPropertiesWithNames) {
                addFunction(updateDividerEventFun(suspend, tv, property.simpleName.asString(), property.type.resolve().toTypeName()))
            }

            addFunction(updateDividerAcceptByUpdateFun(suspend, tv, optionalPropertiesWithNames))
            addFunction(updateDividerAcceptByNameValueFun(suspend, tv, optionalPropertiesWithNames))

        }.build()
    }

    private fun String.toOnFunName(): String = "on${replaceFirstChar(Char::uppercase)}"

    private fun updateDividerOnMismatchEventFun(suspend: Boolean, tv: TypeVariableName): FunSpec {
        return FunSpec.builder("onMismatchUpdateEvent").apply {
            addModifiers(KModifier.PROTECTED, KModifier.ABSTRACT)
            if (suspend) {
                addModifiers(KModifier.SUSPEND)
            }
            addParameter(DIVIDER_NAME_PARAM_NAME, STRING)
            addParameter(DIVIDER_VALUE_PARAM_NAME, ANY)
            addParameter(DIVIDER_UPDATE_PARAM_NAME, UpdateClassName.copy(nullable = true))
            addParameter(DIVIDER_CONTEXT_PARAM_NAME, tv)
        }.build()
    }

    private fun updateDividerEventFun(suspend: Boolean, tv: TypeVariableName, name: String, type: TypeName): FunSpec {
        return FunSpec.builder(name.toOnFunName()).apply {
            addModifiers(KModifier.PROTECTED, KModifier.OPEN)
            if (suspend) {
                addModifiers(KModifier.SUSPEND)
            }
            addParameter(DIVIDER_NAME_PARAM_NAME, STRING)
            addParameter(DIVIDER_VALUE_PARAM_NAME, type.copy(nullable = false))
            addParameter(DIVIDER_UPDATE_PARAM_NAME, UpdateClassName.copy(nullable = true))
            addParameter(DIVIDER_CONTEXT_PARAM_NAME, tv)
            addCode(
                "onMismatchUpdateEvent(%L, %L, %L, %L)",
                DIVIDER_NAME_PARAM_NAME, DIVIDER_VALUE_PARAM_NAME, DIVIDER_UPDATE_PARAM_NAME, DIVIDER_CONTEXT_PARAM_NAME
            )
        }.build()
    }

    private fun updateDividerAcceptByUpdateFun(suspend: Boolean, tv: TypeVariableName, optionalPropertiesWithNames: List<Pair<KSPropertyDeclaration, String>>): FunSpec {
        return FunSpec.builder("accept").apply {
            addModifiers(KModifier.PUBLIC, KModifier.FINAL)
            if (suspend) {
                addModifiers(KModifier.SUSPEND)
            }
            addParameter(DIVIDER_UPDATE_PARAM_NAME, UpdateClassName)
            addParameter(DIVIDER_CONTEXT_PARAM_NAME, tv)

            addCode(buildCodeBlock {
                beginControlFlow("when")

                for ((property, name) in optionalPropertiesWithNames) {
                    val pname = property.simpleName.asString()
                    // update.xxx != null -> onXxx
                    add("%L.%L != null -> ", DIVIDER_UPDATE_PARAM_NAME, pname)
                    addStatement("%L(%M, %L.%L, %L, %L)",
                        pname.toOnFunName(),
                        MemberName(ClassName(UPDATE_PACKAGE, UPDATE_VALUES_CLASS_NAME), constantName(name)),
                        DIVIDER_UPDATE_PARAM_NAME, pname,
                        DIVIDER_UPDATE_PARAM_NAME,
                        DIVIDER_CONTEXT_PARAM_NAME
                    )
                }

                endControlFlow()
            })
        }.build()
    }

    private fun updateDividerAcceptByNameValueFun(suspend: Boolean, tv: TypeVariableName, optionalPropertiesWithNames: List<Pair<KSPropertyDeclaration, String>>): FunSpec {
        return FunSpec.builder("accept").apply {
            addModifiers(KModifier.PUBLIC, KModifier.FINAL)
            if (suspend) {
                addModifiers(KModifier.SUSPEND)
            }
            addParameter(DIVIDER_NAME_PARAM_NAME, STRING)
            addParameter(DIVIDER_VALUE_PARAM_NAME, ANY)
            addParameter(DIVIDER_UPDATE_PARAM_NAME, UpdateClassName.copy(nullable = true))
            addParameter(DIVIDER_CONTEXT_PARAM_NAME, tv)
            addCode(buildCodeBlock {
                beginControlFlow("when")

                for ((property, name) in optionalPropertiesWithNames) {
                    val pname = property.simpleName.asString()
                    // name == constant && value is xxx -> onXxx
                    add("%L == %M ", DIVIDER_NAME_PARAM_NAME, MemberName(ClassName(UPDATE_PACKAGE, UPDATE_VALUES_CLASS_NAME), constantName(name)))
                    add("&& %L is %T -> ", DIVIDER_VALUE_PARAM_NAME, property.type.toTypeName().copy(nullable = false))
                    addStatement("%L(%L, %L, %L, %L)",
                        pname.toOnFunName(),
                        DIVIDER_NAME_PARAM_NAME,
                        DIVIDER_VALUE_PARAM_NAME,
                        DIVIDER_UPDATE_PARAM_NAME,
                        DIVIDER_CONTEXT_PARAM_NAME
                    )
                }

                endControlFlow()
            })
        }.build()
    }

}
