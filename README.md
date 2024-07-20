<!--suppress HtmlDeprecatedAttribute -->
<div align="center">
<picture>
  <source media="(prefers-color-scheme: dark)" srcset=".simbot/logo-dark.svg">
  <source media="(prefers-color-scheme: light)" srcset=".simbot/logo.svg">
  <img alt="simbot logo" src=".simbot/logo.svg" width="260" />
</picture>
<h2>
    ~ Simple Robot ~ <br/> <small>Telegram Component</small>
</h2>
<a href="https://github.com/simple-robot/simbot-component-telegram/releases/latest"><img alt="release" src="https://img.shields.io/github/v/release/simple-robot/simbot-component-telegram" /></a>
<a href="https://repo1.maven.org/maven2/love/forte/simbot/component/simbot-component-telegram-api/" target="_blank">
  <img alt="release" src="https://img.shields.io/maven-central/v/love.forte.simbot.component/simbot-component-telegram-api" /></a>
   <hr>
   <img alt="stars" src="https://img.shields.io/github/stars/simple-robot/simbot-component-telegram" />
   <img alt="forks" src="https://img.shields.io/github/forks/simple-robot/simbot-component-telegram" />
   <img alt="watchers" src="https://img.shields.io/github/watchers/simple-robot/simbot-component-telegram" />
   <img alt="repo size" src="https://img.shields.io/github/repo-size/simple-robot/simbot-component-telegram" />
   <img alt="issues" src="https://img.shields.io/github/issues-closed/simple-robot/simbot-component-telegram?color=green" />
   <img alt="last commit" src="https://img.shields.io/github/last-commit/simple-robot/simbot-component-telegram" />
   <a href="./COPYING"><img alt="copying" src="https://img.shields.io/github/license/simple-robot/simbot-component-telegram" /></a>

</div>

[中文](README_CN.md) | _English_

> [!caution]
> WIP.
> 
> Some thoughts? 
> [Feel free to share](https://github.com/simple-robot/simbot-component-telegram/issues)
> or join the [communities](https://simbot.forte.love/communities.html).

> [!warning]
> This content is machine-translated.

This is a [**Telegram Bot**][telegram bot doc] API/SDK Kotlin multi-platform library based on 
[Kotlin coroutines](https://github.com/Kotlin/kotlinx.coroutines), 
efficient and asynchronous, Java-friendly.

It is also a component library of [Simple Robot v4][simbot4 gh] (simbot), which is a subproject of simbot.
With the capabilities provided by the simbot core library, it can support more advanced encapsulation,
as well as component collaboration, Spring support, and more.

> Serialization and network requests are based on [Kotlin serialization](https://github.com/Kotlin/kotlinx.serialization) and [Ktor](https://ktor.io/).

## Documentation

- **Telegram Component** Reference Manual: (to be built)
- Learn about simbot: [**The Simple Robot Reference Manual**][simbot doc]
- **API Documentation**: [**The KDoc of Telegram Component**](https://docs.simbot.forte.love/components/telegram) in the [**document guide site**](https://docs.simbot.forte.love). 
- [**Communities**](https://simbot.forte.love/communities.html): Have fun interacting with us and other developers!

## Setup

To use the simbot component library, you first need to add the core implementation of simbot
(such as the core library (`simbot-core`) or Spring Boot starter (`simbot-core-spring-boot-starter`)),
and then add the component library dependencies of the Telegram (`simbot-component-telegram-core`).

> [!note]
> The version of the simbot core implementation library (`SIMBOT_VERSION` below) 
> goes [here](https://github.com/simple-robot/simpler-robot/releases) for reference;
>
> Telegram Component library versions (`VERSION` below) go to the [release](https://github.com/simple-robot/simbot-component-telegram/releases) reference.

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

### Ktor client engine

The Telegram component uses Ktor as the HTTP client implementation,
but does not rely on any specific engine by default.

Therefore, you need to choose and use a Ktor Client engine implementation.

You can go to the [Ktor documentation](https://ktor.io/docs/client-engines.html)
to select a suitable Client Engine for your platform.

Take the JVM platform as an example:

<details open><summary>Gradle</summary>

```kotlin
runtimeOnly("io.ktor:ktor-client-java:$ktor_version")
```

</details>

<details ><summary>Maven</summary>

```xml
<dependency>
    <groupId>io.ktor</groupId>
    <artifactId>ktor-client-java-jvm</artifactId>
    <version>${ktor_version}</version>
    <scope>runtime</scope>
</dependency>
```

</details>

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
        "token": "Your FULL Bot Token, e.g. Bot123456789:aaaabbbbcccc"
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
        "token": "Your FULL Bot Token, e.g. Bot123456789:aaaabbbbcccc"
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

## CONTRIBUTING

See [CONTRIBUTING.md](docs/CONTRIBUTING.md) for more information! 

We welcome you and look forward to it
[feedback](https://github.com/simple-robot/simbot-component-telegram/issues)
or
[pull request](https://github.com/simple-robot/simbot-component-telegram/pulls),
Thank you for your contribution and support!

## License

`simbot-component-telegram` is open source under the `LGPLv3` licence。

```
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General 
Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) 
any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied 
warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more 
details.

You should have received a copy of the GNU Lesser General Public License along with this program. 
If not, see <https://www.gnu.org/licenses/>.
```

[simbot4 gh]: https://github.com/simple-robot/simpler-robot/tree/v4-dev
[simbot doc]: https://simbot.forte.love
[telegram bot doc]:https://core.telegram.org/bots/api
[KMP]: https://kotlinlang.org/docs/multiplatform.html
