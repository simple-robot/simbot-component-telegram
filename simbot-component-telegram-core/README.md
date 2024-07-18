# Core module

> [!caution]
> WIP

## Setup

To use the simbot component library, you first need to add the core implementation of simbot 
(such as the core library (`simbot-core`) or Spring Boot starter (`simbot-core-spring-boot-starter`)), 
and then add the component library dependencies of the Telegram (`simbot-component-telegram-core`).

> [!note]
> The version of simbot core implementation library (`SIMBOT_VERSION` below) goes [here](https://github.com/simple-robot/simpler-robot/releases) for reference;
> 
> Telegram Component library versions (`VERSION` below) go to the [releases](https://github.com/simple-robot/simbot-component-telegram/releases) reference.

**With simbot core**

### Gradle

`build.gradle.kts`

```kotlin
plugins {
    kotlin("...") version "..."
}

dependencies {
    implementation("love.forte.simbot:simbot-core:${SIMBOT_VERSION}")
    implementation("love.forte.simbot.component:simbot-component-telegram-core:$VERSION")
}
```

### Maven

`pom.xml`

```xml
<dependencies>
    <dependency>
        <groupId>love.forte.simbot</groupId>
        <artifactId>simbot-core-jvm</artifactId>
        <version>${SIMBOT_VERSION}</version>
    </dependency>
    <dependency>
        <groupId>love.forte.simbot.component</groupId>
        <artifactId>simbot-component-telegram-core-jvm</artifactId>
        <version>${VERSION}</version>
    </dependency>
</dependencies>
```

**With simbot spring boot starter**

### Gradle

`build.gradle.kts`

```kotlin
plugins {
    kotlin("jvm") version "..."
}

dependencies {
    implementation("love.forte.simbot:simbot-core-spring-boot-starter:${SIMBOT_VERSION}")
    implementation("love.forte.simbot.component:simbot-component-telegram-core:$VERSION")
}
```

### Maven

`pom.xml`

```xml
<dependencies>
    <dependency>
        <groupId>love.forte.simbot</groupId>
        <artifactId>simbot-core-spring-boot-starter</artifactId>
        <version>${SIMBOT_VERSION}</version>
    </dependency>
    <dependency>
        <groupId>love.forte.simbot.component</groupId>
        <artifactId>simbot-component-telegram-core-jvm</artifactId>
        <version>${VERSION}</version>
    </dependency>
</dependencies>
```

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
The configuration file `*.bot.json`

> Comments are not supported. 
> Remember to clean them up when you use them.

```json5
{
    "component": "simbot.telegram",
    "ticket": {
        "token": "Your Bot Token"
    },
    // config and its properties are optional and default to `null`.
    "config": {
        "server": null,
        "proxy": null,
        "longPolling": null
    }
}
```


```json5
{
    "component": "simbot.telegram",
    "ticket": {
        "token": "Your Bot Token"
    },
    // config and its properties are optional and default to `null`.
    "config": {
        "server": null,
        "proxy": null,
        // config the `longPolling` to subscribe evnets
        "longPolling": {
            "limit": 100
        }
    }
}
```

