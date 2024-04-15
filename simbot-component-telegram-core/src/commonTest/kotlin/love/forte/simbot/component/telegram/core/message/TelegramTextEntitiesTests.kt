package love.forte.simbot.component.telegram.core.message

import kotlinx.coroutines.test.runTest
import love.forte.simbot.common.id.StringID.Companion.ID
import love.forte.simbot.common.id.literal
import love.forte.simbot.component.telegram.core.message.internal.toMessages
import love.forte.simbot.message.Text
import love.forte.simbot.message.toMessages
import love.forte.simbot.telegram.api.message.SendMessageApi
import love.forte.simbot.telegram.type.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull


/**
 *
 * @author ForteScarlet
 */
class TelegramTextEntitiesTests {

    @Test
    fun textEntitiesSendResolveTest() = runTest {
        val textLink = TelegramMessageEntity.createTextLink("GitHub", "https://github.com/")
        val textMention = TelegramMessageEntity.createTextMention("@forte", User(id = 100, firstName = "forte"))
        val preCode = TelegramMessageEntity.createPre("CODE", "JAVA")
        val customEmoji = TelegramMessageEntity.createCustomEmoji("EMOJI", "EMOJI".ID)
        val simple = TelegramMessageEntity.create("1=1", MessageEntityType.CODE)

        assertIs<TelegramMessageEntity.TextLink>(textLink)
        assertIs<TelegramMessageEntity.TextMention>(textMention)
        assertIs<TelegramMessageEntity.Pre>(preCode)
        assertIs<TelegramMessageEntity.CustomEmoji>(customEmoji)
        assertIs<TelegramMessageEntity.Simple>(simple)

        val (resolved, marks) = listOf(
            textLink,
            textMention,
            Text { "Hello" },
            preCode,
            customEmoji,
            simple
        ).toMessages().resolve(ChatId(10000)) {
            SendMessageApi.builder().also { it.chatId = ChatId(10000) }
        }

        val body = resolved.first()(marks).body
        assertIs<SendMessageApi.Body>(body)
        val text = body.text
        assertEquals("GitHub@forteHelloCODEEMOJI1=1", text)

        val entities = body.entities
        assertNotNull(entities)
        // textLink
        with(entities.first { it.type == textLink.type }) {
            assertEquals(textLink.type, type)
            assertEquals(textLink.url, url)
            assertEquals(
                textLink.text,
                text.substring(this)
            )
        }
        // textMention
        with(entities.first { it.type == textMention.type }) {
            assertEquals(textMention.type, type)
            assertEquals(textMention.user?.id, user?.id)
            assertEquals(
                textMention.text,
                text.substring(this)
            )
        }

        // preCode
        with(entities.first { it.type == preCode.type }) {
            assertEquals(preCode.type, type)
            assertEquals(preCode.language, language)
            assertEquals(
                preCode.text,
                text.substring(this)
            )
        }

        // customEmoji
        with(entities.first { it.type == customEmoji.type }) {
            assertEquals(customEmoji.type, type)
            assertEquals(customEmoji.customEmojiId?.literal, customEmojiId)
            assertEquals(
                customEmoji.text,
                text.substring(this)
            )
        }

        // simple
        with(entities.first { it.type == simple.type }) {
            assertEquals(simple.type, type)
            assertEquals(
                simple.text,
                text.substring(this)
            )
        }

    }

    @Test
    fun receivedMessageToMessagesTest() = runTest {
        val textLink = TelegramMessageEntity.createTextLink("GitHub", "https://github.com/")
        val textMention = TelegramMessageEntity.createTextMention("@forte", User(id = 100, firstName = "forte"))
        val preCode = TelegramMessageEntity.createPre("CODE", "JAVA")
        val customEmoji = TelegramMessageEntity.createCustomEmoji("EMOJI", "EMOJI".ID)
        val simple = TelegramMessageEntity.create("1=1", MessageEntityType.CODE)

        val (resolved, marks) = listOf(
            textLink,
            textMention,
            Text { "Hello" },
            preCode,
            customEmoji,
            simple
        ).toMessages().resolve(ChatId(10000)) {
            SendMessageApi.builder().also { it.chatId = ChatId(10000) }
        }

        val body = resolved.first()(marks).body
        assertIs<SendMessageApi.Body>(body)
        val bodyText = body.text
        assertEquals("GitHub@forteHelloCODEEMOJI1=1", bodyText)

        val messages = Message(
            messageId = 10000,
            chat = Chat(
                id = 10000L,
                title = "Test Chat",
                type = "channel"
            ),
            text = bodyText,
            entities = body.entities?.toList(),
            date = 1234567890,
        ).toMessages().toList()

        // textLink,
        // textMention,
        // Text { "Hello" },
        // preCode,
        // customEmoji,
        // simple

        assertEquals(6, messages.size)

        with(messages[0]) {
            assertIs<TelegramMessageEntity.TextLink>(this)
            assertEquals(textLink.text, text)
        }
        with(messages[1]) {
            assertIs<TelegramMessageEntity.TextMention>(this)
            assertEquals(textMention.text, text)
        }
        with(messages[2]) {
            assertIs<Text>(this)
            assertEquals("Hello", text)
        }
        with(messages[3]) {
            assertIs<TelegramMessageEntity.Pre>(this)
            assertEquals(preCode.text, text)
        }
        with(messages[4]) {
            assertIs<TelegramMessageEntity.CustomEmoji>(this)
            assertEquals(customEmoji.text, text)
        }
        with(messages[5]) {
            assertIs<TelegramMessageEntity.Simple>(this)
            assertEquals(simple.text, text)
        }


    }

}

private fun String.substring(entity: MessageEntity): String =
    substring(entity.offset, entity.offset + entity.length)
