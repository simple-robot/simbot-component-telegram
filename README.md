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

It can be used as aid as a low-level API/SDK dependency or as a lightweight framework 
that can be developed quickly with the help of the simbot core library!

Serialization and network requests are based on [Kotlin serialization](https://github.com/Kotlin/kotlinx.serialization) and [Ktor](https://ktor.io/).

## Documentation

- **Telegram Component** Reference Manual: (to be built)
- Learn about simbot: [**The Simple Robot Reference Manual**][simbot doc]
- **API Documentation**: [**The KDoc of Telegram Component**](https://docs.simbot.forte.love/components/telegram) in the [**document guide site**](https://docs.simbot.forte.love). 
- [**Communities**](https://simbot.forte.love/communities.html): Have fun interacting with us and other developers!

## Modules
### ⭐ Type module

Provides definitions for most types in Telegram and supports serialization based on
[Kotlin Serialization](https://github.com/Kotlin/kotlinx.serialization).

👉 [Go to the module](simbot-component-telegram-type) to learn more.

### ⭐ API module

Based on [Type module](simbot-component-telegram-type) 
and [Ktor](https://ktor.io/)'s 
[KMP][KMP] implementation for [Telegram Bot API][telegram bot doc],
is a simple, efficient and lightweight API implementation module.

This module provides very little extra implementation.
The goal is to preserve the feel of the original API as much as possible without overwrapping it.

👉 [Go to the module](simbot-component-telegram-api) to learn more.

### ⭐ Stdlib module

Based on [API module](simbot-component-telegram-api), 
for the implementation of authentication, subscription events and other related functions in bot.

The `stdlib` module can be thought of as a lightweight SDK implementation library.

This module provides an implementation of event-related functionality based on the API module, 
including the ability to subscribe to events.

Again, the goal is to provide as much of the feel of the original API as possible without overwrapping it.

👉 [Go to the module](simbot-component-telegram-stdlib) to learn more.

### ⭐ Core Component module

A component implementation of the [simbot4 core library][simbot4 gh] based on [standard library module](simbot-component-telegram-stdlib).

Is a relatively highly encapsulated module that provides most of the capabilities of simbot4, 
including event listeners, multi-component coordination, Spring Boot starter, and more.

👉 [Go to Modules](simbot-component-telegram-core) to learn more.

## CONTRIBUTING

See [CONTRIBUTING.md](docs/CONTRIBUTING.md) for more information! 

We welcome you and look forward to it
[feed back](https://github.com/simple-robot/simbot-component-telegram/issues)
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
