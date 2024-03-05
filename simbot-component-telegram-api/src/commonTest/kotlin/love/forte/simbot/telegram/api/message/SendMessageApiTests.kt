package love.forte.simbot.telegram.api.message

import love.forte.simbot.telegram.Telegram
import love.forte.simbot.telegram.type.*
import kotlin.test.Test
import kotlin.test.assertEquals


/**
 *
 * @author ForteScarlet
 */
class SendMessageApiTests {

    @Test
    fun bodySerTest() {
        with(Telegram.DefaultJson) {
            val body = SendMessageApi.Body(
                chatId = 123L,
                text = "forte"
            )

            // check no errors
            val json = encodeToString(SendMessageApi.Body.serializer(), body)
            assertEquals("""{"chat_id":123,"text":"forte"}""", json)
        }
        with(Telegram.DefaultJson) {
            val body = SendMessageApi.Body(
                chatId = "@1234",
                text = "forte"
            )

            // check no errors
            val json = encodeToString(SendMessageApi.Body.serializer(), body)
            assertEquals("""{"chat_id":"@1234","text":"forte"}""", json)
        }
        with(Telegram.DefaultJson) {
            val body = SendMessageApi.builder().apply {
                chatId(1)
                text = "forte"
                replyMarkup(
                    ForceReply(
                        forceReply = true,
                        inputFieldPlaceholder = "inputFieldPlaceholder",
                        selective = true
                    )
                )
            }.buildBody()

            // check no errors
            val json = encodeToString(SendMessageApi.Body.serializer(), body)
            assertEquals(
                """{"chat_id":1,"text":"forte","reply_markup":{"force_reply":true,"input_field_placeholder":"inputFieldPlaceholder","selective":true}}""",
                json
            )
        }
        with(Telegram.DefaultJson) {
            val body = SendMessageApi.builder().apply {
                chatId(1)
                text = "forte"
                replyMarkup(
                    ReplyKeyboardMarkup(
                        keyboard = listOf(listOf(KeyboardButton(text = "button"))),
                        isPersistent = true,
                        resizeKeyboard = true,
                        oneTimeKeyboard = true,
                        inputFieldPlaceholder = "inputFieldPlaceholder",
                        selective = true,
                    )
                )
            }.buildBody()

            // check no errors
            val json = encodeToString(SendMessageApi.Body.serializer(), body)
            assertEquals(
                expected = "{\"chat_id\":1,\"text\":\"forte\",\"reply_markup\":{\"keyboard\":[[{\"text\":\"button\"}]],\"is_persistent\":true,\"resize_keyboard\":true,\"one_time_keyboard\":true,\"input_field_placeholder\":\"inputFieldPlaceholder\",\"selective\":true}}",
                actual = json
            )
        }
        with(Telegram.DefaultJson) {
            val body = SendMessageApi.builder().apply {
                chatId(1)
                text = "forte"
                replyMarkup(
                    ReplyKeyboardRemove(
                        removeKeyboard = true,
                        selective = true,
                    )
                )
            }.buildBody()

            // check no errors
            val json = encodeToString(SendMessageApi.Body.serializer(), body)
            assertEquals(
                expected = "{\"chat_id\":1,\"text\":\"forte\",\"reply_markup\":{\"remove_keyboard\":true,\"selective\":true}}",
                actual = json
            )
        }
        with(Telegram.DefaultJson) {
            val body = SendMessageApi.builder().apply {
                chatId(1)
                text = "forte"
                replyMarkup(
                    InlineKeyboardMarkup(
                        inlineKeyboard = listOf(listOf(
                            InlineKeyboardButton(text = "button")
                        )),
                    )
                )
            }.buildBody()

            // check no errors
            val json = encodeToString(SendMessageApi.Body.serializer(), body)
            assertEquals(
                expected = "{\"chat_id\":1,\"text\":\"forte\",\"reply_markup\":{\"inline_keyboard\":[[{\"text\":\"button\"}]]}}",
                actual = json
            )
        }

    }

}
