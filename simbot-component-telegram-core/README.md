# Core module

> [!caution]
> WIP



## Examples

**simbot core**

```Kotlin
suspend fun main() {
    val app = launchSimpleApplication { 
        useTelegram() // install Telegram Component
    }

    // subscribe to events
    app.listeners {
        // subscribe to ChatGroupMessageEvent 
        listen<ChatGroupMessageEvent> { event ->
            // process event...
            event.reply("Hello!")
            event.reply(At(event.authorId) + "Where are you?".toText())

            // Required an result
            EventResult.empty()
        }

        // subscribe to ChatGroupMessageEvent
        process<TelegramPrivateMessageEvent> { event ->
            // process event...
            event.content().send("Welcome, " + At(event.member().id))

            // Without result in `process<T>` 
        }
    }
    
    // register bots
    app.telegramBots {
        // register a bot via token
        val bot = register(token = "botaaabbb.123123444") {
            // Config...
            // The source stdlib bot config 
            botConfiguration {
                apiClientConfigurer {
                    engine {
                        // A proxy?
                        proxy = ProxyBuilder.http("http://127.0.0.1:7790")
                    }
                }

                // Enable longPolling?
                longPolling = LongPolling(
                    limit = 100,
                    timeout = 10.minutes.inWholeSeconds.toInt(),
                    allowedUpdates = setOf(UpdateValues.MESSAGE_NAME),
                    // Enable retry?
                    retry = LongPolling.Retry()
                )
            }
        }

        // start the bot
        bot.start()
    }

    
    app.join()   
}
```

**simbot Spring Boot starter**

```Kotlin
@SpringBootApplication
@EnableSimbot // enable
class App

fun main(args: Array<String>) {
    runApplication<App>(*args)
}

@Component
class MyHandles {

    @Listener // subscribe to ChatGroupMemberIncreaseEvent
    suspend fun handleMemberIncrease(event: ChatGroupMemberIncreaseEvent) {
        // ...
    }
    
    @Filter("Hello.*")
    @Listener // subscribe to ChatGroupMessageEvent
    suspend fun handleGroupMessage(event: ChatGroupMessageEvent) {
        event.reply("Hello!")
    }
}
```
