<!--suppress HtmlDeprecatedAttribute -->
<div align="center">
<picture>
  <source media="(prefers-color-scheme: dark)" srcset=".simbot/logo-dark.svg">
  <source media="(prefers-color-scheme: light)" srcset=".simbot/logo.svg">
  <img alt="simbot logo" src=".simbot/logo.svg" width="260" />
</picture>
<h2>
    ~ Simple Robot ~ <br/> <small>Telegram 组件</small>
</h2>
<a href="https://github.com/simple-robot/simbot-component-telegram/releases/latest"><img alt="release" src="https://img.shields.io/github/v/release/simple-robot/simbot-component-telegram" /></a>
<a href="https://repo1.maven.org/maven2/love/forte/simbot/component/simbot-component-telegram-api/" target="_blank">
  <img alt="release" src="https://img.shields.io/maven-central/v/love.forte.simbot.component/simbot-component-telegram-api" /></a>
   <hr>
   <img alt="stars" src="https://img.shields.io/github/stars/simple-robot/simbot-component-telegram" />
   <img alt="forks" src="https://img.shields.io/github/forks/simple-robot/simbot-component-telegram" />
   <img alt="watchers" src="https://img.shields.io/github/watchers/simple-robot/simbot-component-telegram" />
   <img alt="repo size" src="https://img.shields.io/github/repo-size/simple-robot/simbot-component-telegram" />
   <img alt="lines" src="https://img.shields.io/tokei/lines/github/simple-robot/simbot-component-telegram" />
   <img alt="issues" src="https://img.shields.io/github/issues-closed/simple-robot/simbot-component-telegram?color=green" />
   <img alt="last commit" src="https://img.shields.io/github/last-commit/simple-robot/simbot-component-telegram" />
   <a href="./COPYING"><img alt="copying" src="https://img.shields.io/github/license/simple-robot/simbot-component-telegram" /></a>

</div>

_中文_ | [English](README.md)

> [!caution]
> WIP.
> 
> 有些想法？欢迎
> [留言交流](https://github.com/simple-robot/simbot-component-telegram/issues)
> 或
> [加入社群](https://simbot.forte.love/communities.html)~

这是一个基于 [Kotlin coroutines](https://github.com/Kotlin/kotlinx.coroutines)
的 [**Telegram Bot**][telegram bot doc] API/SDK 
Kotlin 多平台库，异步高效、Java友好。

它同样是一个 [Simple Robot v4][simbot4 gh] (下文简称 simbot)
的组件库，是 simbot 的子项目之一。
借助 simbot 核心库提供的能力，它可以支持更多高级功能和封装，比如组件协同、Spring支持等。

它可以作为一个低级别的 API/SDK 辅助依赖库，
也可在 simbot 核心库的支持下用作为一个轻量级的快速开发框架！ 

序列化和网络请求相关分别基于 [Kotlin serialization](https://github.com/Kotlin/kotlinx.serialization) 
和 [Ktor](https://ktor.io/).

## 文档

- **Telegram组件**手册：(待建设)
- 了解simbot: [**Simple Robot 应用手册**](https://simbot.forte.love)
- **API文档**: [**文档引导站点**](https://docs.simbot.forte.love) 中 Telegram 的 [**KDoc站点**](https://docs.simbot.forte.love/components/telegram)
- [**社群**](https://simbot.forte.love/communities.html): 与我们和其他开发者愉快地交流！


## 安装

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

## 贡献

前往 [贡献指南](docs/CONTRIBUTING_CN.md) 了解更多！

我们欢迎并期望着您的
[反馈](https://github.com/simple-robot/simbot-component-telegram/issues)
或
[协助](https://github.com/simple-robot/simbot-component-telegram/pulls)，
感谢您的贡献与支持！

## License

`simbot-component-telegram` 使用 `LGPLv3` 许可证开源。

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
