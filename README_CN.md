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
> WIP

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

## 模块引导
### Type模块

提供 Telegram 中的绝大多数类型定义，并基于
[Kotlin Serialization](https://github.com/Kotlin/kotlinx.serialization)
实现可序列化。

👉 [前往模块](simbot-component-telegram-type/README_CN.md) 了解更多。

### API模块

基于 `Ktor` 针对 [Telegram API](https://telegram.com/developers/docs/intro) 
的基本完整的[KMP](https://kotlinlang.org/docs/multiplatform.html)多平台封装实现，
是一个简单高效轻量级的API实现模块。

此模块基本不会提供什么多余的实现，其目标为在提供封装的情况下尽可能地保留原始API的使用手感，不做过多的封装。

👉 [前往模块](simbot-component-telegram-api/README_CN.md) 了解更多。

### 标准库模块

基于 [API模块](simbot-component-telegram-api) 针对 bot 的鉴权、事件订阅等功能的实现。

同样的，其目标为在提供封装的情况下尽可能地保留原始API的使用手感，不做过多的封装。

👉 [前往模块](simbot-component-telegram-stdlib) 了解更多。

### 核心组件模块

基于 
[标准库模块](simbot-component-telegram-stdlib) 
对 [simbot4核心库](https://github.com/simple-robot/simpler-robot) 
的组件实现，
是一个相对高度封装的模块，并提供simbot4大部分能力，包括事件监听、多组件协同、Spring Boot starter 等。

👉 [前往模块](simbot-component-telegram-core) 了解更多。

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
