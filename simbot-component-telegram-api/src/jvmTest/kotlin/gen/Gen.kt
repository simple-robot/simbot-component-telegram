package gen

import kotlin.io.path.Path
import kotlin.test.Test


class Gen {
    @Test
    fun gen() {
        val parser = ParseTypes()
        Gen::class.java.classLoader.getResourceAsStream("models-html.txt")!!.use { inp ->
            val types = parser.readTypes(inp)
            parser.gen(Path(BASE_PATH), types)
        }

    }


}
