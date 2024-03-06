package gen2

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.util.*
import kotlin.io.path.Path

private const val PROJECT_SOURCE_DIR =
    "src\\generated"

private const val DEF_PACKAGE = "love.forte.simbot.telegram.type"

/**
 *
 * @author ForteScarlet
 */
class TelegramTypeGenerator {

    // @Test
    // fun readModelTypes(inputStream: InputStream): List<Type> {
    // fun readModelTypesRemote() {
    //     // val doc = Jsoup.parse(inputStream, "UTF-8", "localhost")
    //
    //     val body = Jsoup.connect("https://core.telegram.org/bots/api")
    //         .proxy("127.0.0.1", 7790)
    //         .get()
    //
    //     var start = false
    //     val path =
    //         Path("G:\\code\\javaProjects\\simbot-component-telegram\\simbot-component-telegram-api\\src\\jvmTest\\resources\\models-html.txt")
    //
    //     val col1 = body.select("div#dev_page_content").first()!!
    //
    //     col1.children().forEach {
    //         if (!start) {
    //             if (it.nodeName() == "h3" && it.text() == "Available types") {
    //                 start = true
    //                 // path.appendText(it.outerHtml())
    //                 // skip h3 self
    //             }
    //         } else {
    //             if (it.nodeName() == "h3") {
    //                 start = false
    //             } else {
    //                 path.appendText(it.outerHtml())
    //             }
    //         }
    //     }
    // }

    private data class Info(val title: Element, val content: List<Element>, val table: Element?)


    // 👇 使用它解析 models-html.txt 中的数据并生成类型。
    // @kotlin.test.Test
    fun genTypes() {
        val infos = readTypes()
        genFromInfos(infos)
    }

    private val h4Matcher = Regex("^[A-Z][A-Za-z0-9]+")
    private val fieldHead = listOf("Field", "Type", "Description")

    private fun readTypes(): List<Info> {
        val doc = TelegramTypeGenerator::class.java.classLoader.getResourceAsStream("models-html.txt")!!.use { inp ->
            Jsoup.parse(inp, "UTF-8", "localhost")
        }

        val body = doc.body()

        val infoList = mutableListOf<Info>()

        var h4: Element? = null
        var content: MutableList<Element> = mutableListOf()

        for (ele in body.children()) {
            val tag = ele.tagName()

            if (tag == "h4" && ele.text().matches(h4Matcher)) {
                if (h4 != null) {
                    infoList.add(Info(h4, content, ele))
                }

                h4 = ele
                content = mutableListOf()
                continue
            }

            if (h4 != null) {
                when (tag) {
                    "table" -> {
                        // 检测是不是 Field	Type	Description
                        // 如果不是, 无视?
                        val thList = ele.select("thead tr th")
                        if (thList.eachText() == fieldHead) {
                            infoList.add(Info(h4, content, ele))
                            h4 = null
                            content = mutableListOf()
                        }

                    }

                    else -> content.add(ele)
                }
            }
        }

        println(infoList.size)

        return infoList
    }


    private fun genFromInfos(infos: List<Info>) {
        val types = infos.map(::infoToClass)
        val files = files(types)
        files.forEach {
            it.writeTo(Path(PROJECT_SOURCE_DIR))
        }
    }

    private fun files(types: List<TypeSpec>): List<FileSpec> {
        val sorted = types.sortedWith(Comparator
            .comparing<TypeSpec?, String?> { it.name ?: "" }
            .thenComparingInt { it.name?.length ?: 0 }
        )

        data class TypeWithFile(val type: TypeSpec, val file: String)

        val result = mutableListOf<TypeWithFile>()

        sorted.mapTo(result) { type ->
            val pre = result.lastOrNull() ?: return@mapTo TypeWithFile(type, type.name ?: "")

            // 如果上一个是自己的 prefix，使用它
            if (type.name?.startsWith(pre.file) == true) {
                TypeWithFile(type, pre.file)
            } else {
                TypeWithFile(type, type.name ?: "")
            }
        }

        return result.groupBy({ it.file }) { it.type }
            .map { (file, types) ->
                FileSpec.builder(DEF_PACKAGE, file)
                    .addTypes(types)
                    .indent("    ")
                    .build()
            }
    }

    private fun infoToClass(info: Info): TypeSpec {
        val className = ClassName(DEF_PACKAGE, info.title.text())
        val parameters = infoParameters(info)

        val type = TypeSpec.classBuilder(className)
            .apply {
                if (parameters.isNotEmpty()) {
                    primaryConstructor(
                        FunSpec.constructorBuilder()
                            .addParameters(parameters.map { it.first })
                            .build()
                    ).addProperties(parameters.map {
                        val p = it.first
                        PropertySpec.builder(p.name, p.type)
                            .initializer(p.name)
                            .addKdoc("%L", it.second)
                            .build()
                    }).addModifiers(KModifier.DATA)
                } else {
                    this.addInitializerBlock(CodeBlock.of("TODO(%S)\n", "Empty class?"))

                }
            }
            .addAnnotation(Serializable::class)
            .apply {
                info.title.selectFirst("a")?.attr("href")?.also { href ->
                    addKdoc("%L\n\n", "[${info.title.text()}](https://core.telegram.org/bots/api$href)")
                }
                info.content.forEach {
                    addKdoc("%L", it.text())
                    addKdoc("\n")
                }
            }
            .addKdoc("\n(auto-generated)\n")
            .addKdoc("@author ForteScarlet")
            .build()

        return type
    }

    private val arrayRegex = Regex("Array of ([A-Za-z0-9_][A-Za-z0-9_ ]+)")

    /**
     * 根据 info 构建 [PropertySpec] list.
     */
    private fun infoParameters(info: Info): List<Pair<ParameterSpec, String>> {
        val trList = info.table?.select("tbody tr") ?: return emptyList()
        val parameters = trList.map { tr ->
            val tdList = tr.select("td")

            val name = tdList[0]
            val type = tdList[1]
            val comm = tdList[2]

            var fieldDef: String? = null
            val nameText = name.text()
            val typeText = type.text()
            val commText = comm.text().replace(". ", ". \n") + "\n\ntype: `$typeText`\n\n"

            val fieldOptional = commText.startsWith("Optional", true)

            fun resolveType(raw: String): TypeName {
                return when (raw) {
                    "Integer" -> if (commText.contains(
                            "64-bit integer",
                            true
                        )
                    ) LONG else INT

                    "True" -> {
                        fieldDef = "true"
                        BOOLEAN
                    }

                    "False" -> {
                        fieldDef = "false"
                        BOOLEAN
                    }

                    "String" -> STRING
                    "Boolean" -> BOOLEAN

                    "Integer or String" -> STRING

                    "Sticker" -> ClassName("love.forte.simbot.telegram.sticker", "Sticker")

                    else -> {
                        val array = arrayRegex.matchEntire(raw)
                        if (array == null) {
                            return ClassName(DEF_PACKAGE, raw)
                        } else {
                            val arrayType = array.groupValues[1]
                            return LIST.parameterizedBy(resolveType(arrayType))
                        }
                    }
                }
            }

            val fieldName = buildString(nameText.length) {
                nameText.split('_').forEachIndexed { index, s ->
                    if (index == 0) {
                        append(s)
                    } else {
                        append(s.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
                    }
                }
            }

            val annotationList = if (fieldName != nameText) {
                listOf(
                    AnnotationSpec.builder(SerialName::class)
                        .addMember("%S", nameText)
                        .build()
                )
            } else {
                emptyList()
            }

            val typeName = resolveType(typeText)
                .copy(nullable = fieldOptional)

            if (fieldOptional && fieldDef == null) {
                fieldDef = "null"
            } else if (fieldDef == null && typeName is ParameterizedTypeName && typeName.rawType == LIST) {
                fieldDef = "emptyList()"
            }


            val parameter = ParameterSpec.builder(fieldName, typeName)
                // .addKdoc("%L", commText)
                .addAnnotations(annotationList)
                .run {
                    fieldDef?.let { def -> defaultValue(def) }
                        ?: this
                }
                .build()

            // println(parameter)

            parameter to commText
        }

        return parameters
    }

}
