import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import love.forte.simbot.telegram.type.ChatId
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 *
 * @author ForteScarlet
 */
class ChatIdTests {
    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        prettyPrint = false
    }

    @Serializable
    private data class Value(@SerialName("chat_id") val chatId: ChatId)

    @Test
    fun chatIdEncodeTest() {
        with(ChatId(123)) {
            assertEquals("123", json.encodeToString(ChatId.serializer(), this))
            assertEquals("{\"chat_id\":123}", json.encodeToString(Value.serializer(), Value(this)))
        }
        with(ChatId("@name")) {
            assertEquals("\"@name\"", json.encodeToString(ChatId.serializer(), this))
            assertEquals("{\"chat_id\":\"@name\"}", json.encodeToString(Value.serializer(), Value(this)))
        }
    }


}
