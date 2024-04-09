# Stdlib module

> [!caution]
> WIP

## Setup

> [!note]
> Version (`VERSION` below) goes to the [releases](https://github.com/simple-robot/simbot-component-telegram/releases) reference.

### Gradle

`build.gradle.kts`

```kotlin
plugins {
    kotlin("...") version "..."
}

dependencies {
    implementation("love.forte.simbot.component:simbot-component-telegram-stdlib:$VERSION")
}
```

### Maven

```xml
<dependencies>
    <dependency>
        <groupId>love.forte.simbot.component</groupId>
        <artifactId>simbot-component-telegram-stdlib-jvm</artifactId>
        <version>${VERSION}</version>
    </dependency>
</dependencies>
```

## Examples

**Create and start the bot**

```Kotlin
// create and config bot
val bot = BotFactory.create(token = "bot111222333.xxxxxxxxx") {
        // configs..
        // custom coroutineContext
        coroutineContext = Dispatchers.IO

        // use a HttpClient engine
        // or use `apiClientEngineFactory = ...`
        apiClientEngine = CIO.create {
            // ... engine builder
        }
    
        // If use long polling (via api `GetUpdatesApi`), set a value
        longPolling = LongPolling(
            limit = 100,
            timeout = 10.minutes.inWholeSeconds.toInt(),
            allowedUpdates = setOf(UpdateValues.MESSAGE_NAME)
        )
        
        // ...
    }

// start bot
bot.start()

// join bot
bot.join()
```

**Subscribe to events, reply to messages**

```Kotlin
val bot = createYourBot()

// Subscribe to all events
bot.subscribe { event ->
    println("Event: $event")
}

// Subscribe to events by event name (optional) and event type
bot.subscribe<Message>(UpdateValues.MESSAGE_NAME) { event, message ->
    println("Event message: $message")
    // ...
}

// Subscribe to events based on existing auxiliary extensions
// The effect is the same as the previous one
bot.onMessage { _, message ->
    if (message.text != null) {
        if (chat.type == "private" && message.text == "/stop") {
            // If the `/stop` message is from a private chat, then close the bot
            bot.cancel()
        }
        // Reply to the same message
        // Here you build the `SendMessageApi` and make the request through the bot
        val repliedMessage = buildSendMessageApi(message.chat.id, message.text) {
            replyParameters = ReplyParameters(message.messageId)
        }.requestDataBy(bot)
    }
    
}

bot.start()
bot.join() // Suspend until the bot is canceled
```
