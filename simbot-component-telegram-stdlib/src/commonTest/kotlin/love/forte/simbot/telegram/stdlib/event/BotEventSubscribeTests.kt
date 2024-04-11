package love.forte.simbot.telegram.stdlib.event

import io.ktor.client.engine.mock.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.runTest
import love.forte.simbot.telegram.stdlib.*
import love.forte.simbot.telegram.stdlib.bot.*
import love.forte.simbot.telegram.type.MessageOriginChannel
import love.forte.simbot.telegram.type.MessageOriginUser
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull


/**
 *
 * @author ForteScarlet
 */
class BotEventSubscribeTests {
    private suspend fun botAndStart(): Bot {
        return BotFactory.create("TOKEN") {
            apiClientEngine = MockEngine {
                respondOk()
            }
        }.also {
            it.start()
        }
    }

    @Test
    fun messageWithTextTest() = runTest {
        val bot = botAndStart()
        val onMessaged = Job()
        val onMessaged2 = Job()

        bot.onMessage { _, _ ->
            onMessaged.complete()
        }

        bot.pushRawUpdate(MESSAGE_WITH_TEXT)

        onMessaged.join()
        onMessaged2.join()
    }

    @Test
    fun forwardedMessageTest() = runTest {
        val bot = botAndStart()
        val onMessaged = Job()

        bot.onMessage { _, message ->
            assertNotNull(message.forwardOrigin)
            assertIs<MessageOriginUser>(message.forwardOrigin)
            onMessaged.complete()
        }

        bot.pushRawUpdate(FORWARDED_MESSAGE)

        onMessaged.join()
    }

    @Test
    fun forwardedChannelMessageTest() = runTest {
        val bot = botAndStart()
        val onMessaged = Job()

        bot.onMessage { _, message ->
            assertNotNull(message.forwardOrigin)
            assertIs<MessageOriginChannel>(message.forwardOrigin)
            onMessaged.complete()
        }

        bot.pushRawUpdate(FORWARDED_CHANNEL_MESSAGE)

        onMessaged.join()
    }

    @Test
    fun messageWithAReplyTest() = runTest {
        val bot = botAndStart()
        val onMessaged = Job()

        bot.onMessage { _, message ->
            assertNotNull(message.replyToMessage)
            onMessaged.complete()
        }

        bot.pushRawUpdate(MESSAGE_WITH_A_REPLY)

        onMessaged.join()
    }

    @Test
    fun editedMessageTest() = runTest {
        val bot = botAndStart()
        val onMessaged = Job()

        bot.onEditedMessage { _, _ ->
            onMessaged.complete()
        }

        bot.onMessage { _, _ ->
            onMessaged.completeExceptionally(IllegalStateException("NOT onMessage"))
        }

        bot.pushRawUpdate(EDITED_MESSAGE)

        onMessaged.join()
    }

    @Test
    fun messageWithEntitiesTest() = runTest {
        val bot = botAndStart()
        val onMessaged = Job()

        bot.onMessage { _, message ->
            val entities = message.entities
            assertNotNull(entities)
            assertEquals(2, entities.size)
            onMessaged.complete()
        }

        bot.pushRawUpdate(MESSAGE_WITH_ENTITIES)

        onMessaged.join()
    }

    @Test
    fun messageWithAudioTest() = runTest {
        val bot = botAndStart()
        val onMessaged = Job()

        bot.onMessage { _, message ->
            assertNotNull(message.audio)
            onMessaged.complete()
        }

        bot.pushRawUpdate(MESSAGE_WITH_AUDIO)

        onMessaged.join()
    }

    @Test
    fun voiceMessageTest() = runTest {
        val bot = botAndStart()
        val onMessaged = Job()

        bot.onMessage { _, message ->
            assertNotNull(message.voice)
            onMessaged.complete()
        }

        bot.pushRawUpdate(VOICE_MESSAGE)

        onMessaged.join()
    }

    @Test
    fun messageWithADocumentTest() = runTest {
        val bot = botAndStart()
        val onMessaged = Job()

        bot.onMessage { _, message ->
            assertNotNull(message.document)
            onMessaged.complete()
        }

        bot.pushRawUpdate(MESSAGE_WITH_A_DOCUMENT)

        onMessaged.join()
    }

    @Test
    fun inlineQueryTest() = runTest {
        val bot = botAndStart()
        val onMessaged = Job()

        bot.onInlineQuery { _, _ ->
            onMessaged.complete()
        }

        bot.pushRawUpdate(INLINE_QUERY)

        onMessaged.join()
    }

    @Test
    fun chosenInlineQueryTest() = runTest {
        val bot = botAndStart()
        val onMessaged = Job()

        bot.onChosenInlineResult { _, _ ->
            onMessaged.complete()
        }

        bot.pushRawUpdate(CHOSEN_INLINE_QUERY)

        onMessaged.join()
    }

    @Test
    fun callbackQueryTest() = runTest {
        val bot = botAndStart()
        val onMessaged = Job()

        bot.onCallbackQuery { _, _ ->
            onMessaged.complete()
        }

        bot.pushRawUpdate(CALLBACK_QUERY)

        onMessaged.join()
    }

}


