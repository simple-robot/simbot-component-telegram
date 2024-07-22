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

**Telegram组件**
是一个 [Kotlin 多平台](https://kotlinlang.org/docs/multiplatform.html) 的 [**Telegram Bot API**][telegram bot doc] SDK实现库，
也是 Simple Robot 标准API下实现的组件库，异步高效、Java友好！

> 序列化和网络请求相关分别基于 [Kotlin serialization](https://github.com/Kotlin/kotlinx.serialization)
> 和 [Ktor](https://ktor.io/).

> 序列化和网络请求相关分别基于 [Kotlin serialization](https://github.com/Kotlin/kotlinx.serialization)
> 和 [Ktor](https://ktor.io/).

 
## 文档

- 手册: [**Simple Robot 应用手册**](https://simbot.forte.love) 及其中的 [**Telegram组件**](https://simbot.forte.love/component-telegram.html) 部分。
- **API文档**: [**文档引导站点**](https://docs.simbot.forte.love) 中 Telegram 的 [**KDoc站点**](https://docs.simbot.forte.love/components/telegram)
- [**社群**](https://simbot.forte.love/communities.html): 与我们和其他开发者愉快地交流！

## 安装

参考手册的 [**Telegram组件**](https://simbot.forte.love/component-telegram.html) 部分。

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
