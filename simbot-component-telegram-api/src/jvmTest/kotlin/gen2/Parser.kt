package gen2

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.appendText
import kotlin.test.Test


/**
 *
 * @author ForteScarlet
 */
class Parser {

    // @Test
    // fun readModelTypes(inputStream: InputStream): List<Type> {
    fun readModelTypesRemote() {
        // val doc = Jsoup.parse(inputStream, "UTF-8", "localhost")

        val body = Jsoup.connect("https://core.telegram.org/bots/api")
            .proxy("127.0.0.1", 7790)
            .get()

        var start = false
        val path =
            Path("G:\\code\\javaProjects\\simbot-component-telegram\\simbot-component-telegram-api\\src\\jvmTest\\resources\\models-html.txt")

        val col1 = body.select("div#dev_page_content").first()!!

        col1.children().forEach {
            if (!start) {
                if (it.nodeName() == "h3" && it.text() == "Available types") {
                    start = true
                    // path.appendText(it.outerHtml())
                    // skip h3 self
                }
            } else {
                if (it.nodeName() == "h3") {
                    start = false
                } else {
                    path.appendText(it.outerHtml())
                }
            }
        }


        // TODO()
    }


    // TODO
    @Test
    fun readTypes() {
        val doc =
            Jsoup.parse(File("G:\\code\\javaProjects\\simbot-component-telegram\\simbot-component-telegram-api\\src\\jvmTest\\resources\\models-html.txt"))
        val body = doc.body()

        data class Info(val title: Element, val content: List<Element>, val table: Element?)

        val infoList = mutableListOf<Info>()

        var h4: Element? = null
        var content: MutableList<Element> = mutableListOf()

        for (ele in body.children()) {
            val tag = ele.tagName()

            if (tag == "h4") {
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
                        infoList.add(Info(h4, content, ele))
                        h4 = null
                        content = mutableListOf()
                    }

                    else -> content.add(ele)
                }
            }
        }

        println(infoList.size)
        infoList.forEach {
            println(it.title.text())
        }

    }


}
