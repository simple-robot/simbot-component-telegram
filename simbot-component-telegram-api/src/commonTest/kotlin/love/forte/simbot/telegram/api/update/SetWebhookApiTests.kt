package love.forte.simbot.telegram.api.update

import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.request.forms.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.builtins.serializer
import love.forte.simbot.common.ktor.inputfile.InputFile
import love.forte.simbot.telegram.api.Telegram
import love.forte.simbot.telegram.api.TelegramApiResult
import love.forte.simbot.telegram.api.requestData
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SetWebhookApiTests {
    @Test
    fun setWebhookApiSerializerTest() = runTest {
        val byteStr = "VALUE".toByteArray()
        val api = SetWebhookApi.create(
            SetWebhookApi.Params(
                url = "url-value",
                certificate = InputFile(
                    InputProvider(byteStr.size.toLong()) {
                        ByteReadPacket(byteStr)
                    }
                ),
                ipAddress = null,
                maxConnections = 1000,
                allowedUpdates = listOf("a", "b", "c"),
                dropPendingUpdates = "dropPendingUpdates-value",
                secretToken = "secretToken-value",
            )
        )

        val client = HttpClient(
            MockEngine { req ->
                val bodyString = String(req.body.toByteArray())
                println(bodyString)

                // ipAddress is null
                assertFalse(bodyString.contains("name=ipAddress"))

                respondOk(
                    Telegram.DefaultJson.encodeToString(
                        TelegramApiResult.serializer(Boolean.serializer()),
                        TelegramApiResult(ok = true, result = true)
                    )
                )
            }
        )

        assertTrue(
            api.requestData(
                client = client,
                "TOKEN",
            )
        )
    }
}
