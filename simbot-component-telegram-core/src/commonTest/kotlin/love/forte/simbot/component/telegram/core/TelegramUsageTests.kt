package love.forte.simbot.component.telegram.core

import kotlinx.coroutines.test.runTest
import love.forte.simbot.component.NoSuchComponentException
import love.forte.simbot.component.telegram.core.bot.useTelegramBotManager
import love.forte.simbot.component.telegram.core.component.useTelegramComponent
import love.forte.simbot.core.application.launchSimpleApplication
import kotlin.test.*


/**
 *
 * @author ForteScarlet
 */
class TelegramUsageTests {

    @Test
    fun useTelegramTest() = runTest {
        with(launchSimpleApplication {
            useTelegram()
        }) {
            assertNotNull(telegramComponentOrNull())
            assertNotNull(telegramBotManagerOrNull())
        }

        var inComponent = false
        var inManager = false

        with(launchSimpleApplication {
            useTelegram {
                component {
                    inComponent = true
                }
                botManager {
                    inManager = true
                }
            }
        }) {
            assertNotNull(telegramComponentOrNull())
            assertNotNull(telegramBotManagerOrNull())
            assertTrue(inComponent)
            assertTrue(inManager)
        }
    }

    @Test
    fun useTelegramComponentTest() = runTest {
        var used = false
        with(launchSimpleApplication {
            useTelegramComponent {
                used = true
            }
        }) {
            assertNotNull(telegramComponentOrNull())
            assertTrue(used)
        }
    }

    @Test
    fun useTelegramBotManagerTest() = runTest {
        // botManager需要组件的安装
        val err = assertFails {
            launchSimpleApplication {
                useTelegramBotManager()
            }
        }
        assertIs<NoSuchComponentException>(err)

        var used = false

        with(launchSimpleApplication {
            useTelegramComponent()
            useTelegramBotManager {
                used = true
            }
        }) {
            assertNotNull(telegramBotManagerOrNull())
            assertTrue(used)
        }
    }

}
