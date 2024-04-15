package love.forte.simbot.telegram.api.utils

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import love.forte.simbot.common.ktor.inputfile.InputFile
import love.forte.simbot.telegram.api.Telegram
import kotlin.jvm.JvmInline
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertIs


/**
 *
 * @author ForteScarlet
 */
class KtorFormDeserializerTests {

    @Serializable
    private data class Data(val value: String, val num: UInt)

    @Serializable
    private data class User(
        val id: Int,
        val name: String,
        @SerialName("type_list")
        val types: List<Int>,
        val data: Data,
        val uint: UInt,
        val uintw: UIntWarp,
        @SerialName("file")
        val inputFile: InputFile
    )

    @JvmInline
    @Serializable
    private value class UIntWarp(val value: UInt)

    @Test
    fun ktorFormSerializerTest() {
        val formParts = KtorFormSerializer(
            User.serializer(),
            Telegram.DefaultJson
        ).serialize(
            User(
                1,
                "forte",
                listOf(1, 2, 3),
                Data("data", 100u),
                200u,
                UIntWarp(300u),
                InputFile(bytes = byteArrayOf(1, 2, 3))
            )
        )

        assertEquals(7, formParts.size)
        with(formParts[0]) {
            assertEquals("id", key)
            assertEquals("1", value)
        }
        with(formParts[1]) {
            assertEquals("name", key)
            assertEquals("forte", value)
        }
        with(formParts[2]) {
            assertEquals("type_list", key)
            assertEquals("[1,2,3]", value)
        }
        with(formParts[3]) {
            assertEquals("data", key)
            assertEquals("""{"value":"data","num":100}""", value)
        }
        with(formParts[4]) {
            assertEquals("uint", key)
            assertEquals("200", value)
        }
        with(formParts[5]) {
            assertEquals("uintw", key)
            assertEquals("300", value)
        }
        with(formParts[6]) {
            assertEquals("file", key)
            assertIs<ByteArray>(value)
            assertContentEquals(byteArrayOf(1, 2, 3), value as ByteArray)
        }

    }

}
