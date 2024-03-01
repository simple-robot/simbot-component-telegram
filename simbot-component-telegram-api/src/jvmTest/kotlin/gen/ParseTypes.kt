package gen

import jdk.internal.org.jline.utils.Colors.s
import java.io.InputStream
import java.io.Writer
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import java.util.*
import kotlin.io.path.bufferedWriter
import kotlin.io.path.exists
import kotlin.io.path.fileSize

internal const val BASE_PACKAGE = "love.forte.simbot.telegram.model"
internal const val BASE_PATH =
    "G:\\code\\javaProjects\\simbot-component-telegram\\simbot-component-telegram-api\\src\\commonMain\\kotlin\\love\\forte\\simbot\\telegram\\model"

/**
 *
 * @author ForteScarlet
 */
class ParseTypes {
    sealed class State {
        data object Init : State()
        data class Desc(val name: String, val lines: MutableList<String> = mutableListOf()) : State()
        data class InField(val name: String, val desc: List<String>, val lines: MutableList<String> = mutableListOf()) :
            State() {
            fun toType(): Type {
                return Type(name, desc, lines, "<TODO>")
            }
        }
    }

    fun readTypes(inputStream: InputStream): List<Type> {
        var state: State = State.Init

        val types = mutableListOf<Type>()

        val lines = inputStream.bufferedReader()
            .lineSequence()
            .map { it.trim() }

        for (line in lines) {
            when (state) {
                is State.Init -> {
                    // 纯英文
                    if (!line.matches(Regex("^[A-Z][A-Za-z0-9]+"))) {
                        continue
                    }

                    state = State.Desc(line)
                }

                is State.Desc -> {
                    if (line == "Field\tType\tDescription") {
                        state = State.InField(state.name, state.lines)
                    } else if (line.matches(Regex("^[A-Z][A-Za-z0-9]+"))) {
                        // 无内容的
                        types.add(Type(state.name, state.lines, mutableListOf(), ""))
                        state = State.Desc(name = line)
                    } else {
                        state.lines.add(line)
                    }
                }

                is State.InField -> {
                    var tcount = 0
                    for (c in line) {
                        if (tcount >= 2) break
                        if (c == '\t') {
                            tcount++
                            continue
                        }
                    }

                    if (tcount < 2) {
                        types.add(state.toType())
                        state = if (line.matches(Regex("^[A-Z][A-Za-z0-9]+"))) {
                            State.Desc(line)
                        } else {
                            State.Init
                        }
                    } else {
                        state.lines.add(line)
                    }
                }
            }
        }

        return resolveTypeFiles(types)
    }

    /**
     * 根据名称大小排序、并重新匹配他们的 file.
     */
    private fun resolveTypeFiles(types: MutableList<Type>): List<Type> {
        types.sortWith(Comparator
            .comparing<Type?, String?> { it.name }
            .thenComparingInt { it.name.length }
        )

        val result = mutableListOf<Type>()

        return types.mapTo(result) { type ->
            val pre = result.lastOrNull() ?: return@mapTo type.copy(file = type.name)

            // 如果上一个是自己的 prefix，使用它
            if (type.name.startsWith(pre.file)) {
                type.copy(file = pre.file)
            } else {
                type.copy(file = type.name)
            }
        }

    }

    fun gen(dir: Path, types: List<Type>) {
        types.forEach { type -> gen(dir, type) }
    }

    data class Field(val name: String, val rawName: String, val type: String, val def: String?, val comment: String)

    fun gen(dir: Path, type: Type) {

        val fields = type.fieldLines
            .map { it.split("\t", limit = 3) }
            .map(::toField)
            .toList()

        val file = dir.resolve(type.file + ".kt")
        val fileIsNew = !(file.exists() && file.fileSize() > 0L)

        file.bufferedWriter(
            options = arrayOf(
                StandardOpenOption.WRITE,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND,
            ),
        ).use { writer ->
            if (fileIsNew) {
                writer.writeClassInit()
            }

            writer.appendLine("/**")
            writer.appendLine(" * [${type.name}](https://core.telegram.org/bots/api#${type.name.lowercase()})")
            writer.appendLine(" * ")
            type.commentLines.forEach {
                writer.appendLine(" * $it")
            }
            writer.appendLine(" * ")
            fields.forEach {
                writer.appendLine(" * @property ${it.name} ${it.comment}")
            }
            writer.appendLine(" * ")
            writer.appendLine(" * @author ForteScarlet")
            writer.appendLine(" */")
            writer.appendLine("@Serializable")
            if (type.fieldLines.isEmpty()) {
                writer.appendLine("// TODO empty class?\npublic class ${type.name} {")
            } else {
                writer.appendLine("public data class ${type.name}(")
            }
            fields.forEach {
                if (it.rawName != it.name) {
                    writer.appendLine("    @SerialName(\"${it.rawName}\")")
                }
                writer.appendLine("    val ${it.name}: ${it.type}${if (it.def != null) " = ${it.def}" else ""},")
            }
            if (type.fieldLines.isEmpty()) {
                writer.appendLine("}\n\n")
            } else {
                writer.appendLine(")\n\n")
            }

        }
    }

    private val ArrayRegex = Regex("Array of ([A-Za-z0-9_]+)")

    private fun toField(line: List<String>): Field {
        var fieldDef: String? = null

        val fieldRawName = line[0]
        val fieldRawType = line[1]
        val fieldRawComm = line[2].trim()
        val fieldTypeOptional = fieldRawComm.startsWith("Optional", true)
        val fieldTypeOpEnd = if (fieldTypeOptional) {
            fieldDef = "null"
            "?"
        } else ""

        val fieldName = buildString(fieldRawName.length) {
            fieldRawName.split('_').forEachIndexed { index, s ->
                if (index == 0) {
                    append(s)
                } else {
                    append(s.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
                }
            }
        }

        fun resolveType(raw: String): String {
            return when (raw) {
                "Integer" -> if (fieldRawComm.contains(
                        "64-bit integer",
                        true
                    )
                ) "Long" else "Int"

                "True" -> {
                    fieldDef = "true"
                    "Boolean"
                }

                "False" -> {
                    fieldDef = "false"
                    "Boolean"
                }

                else -> {
                    val array = ArrayRegex.matchEntire(raw)
                    if (array == null) {
                        return raw
                    } else {
                        val arrayType = array.groupValues[1]
                        return "List<${resolveType(arrayType)}>"
                    }
                }
            }
        }

        val type = resolveType(fieldRawType)

        return Field(
            name = fieldName,
            rawName = fieldRawName,
            type = type + fieldTypeOpEnd,
            def = fieldDef,
            comment = fieldRawComm
        )
    }
}


private fun Writer.writeClassInit() {
    append(
        """
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

        package $BASE_PACKAGE
        
        import kotlinx.serialization.SerialName
        import kotlinx.serialization.Serializable
        
    """.trimIndent()
    )
}
