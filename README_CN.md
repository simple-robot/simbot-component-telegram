<!--suppress HtmlDeprecatedAttribute -->
<div align="center">
<img src=".simbot/logo.png" alt="logo" style="width:230px; height:230px; border-radius:50%; " />
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

这是 
[**Simple Robot v4**](https://github.com/simple-robot/simpler-robot/tree/v4-dev)
下的子项目，是针对 
[**Telegram Bot**](https://telegram.com/developers/docs/intro) 
各方面的 simbot 组件库实现， 
包括对 `API` 内容的实现、事件相关的实现以及BOT对于事件的监听与交互等。

Telegram Component 可以作为底层API依赖使用、
轻量级的 Telegram 事件调度框架使用，
也可以基于 simbot 核心库的种种快速开发一个功能强大的 Telegram Bot！

- 基于 [`Kotlin`](https://kotlinlang.org/) 提供 [KMP 多平台](https://kotlinlang.org/docs/multiplatform.html) 特性，并提供 Java 友好的API。
- 基于 [`Kotlin coroutines`](https://github.com/Kotlin/kotlinx.coroutines) 与 [`Ktor`](https://ktor.io/) 提供轻量高效的API。

> [!Note]
> 下文中 `Simple Robot v4` 简称为 `simbot4`

## 文档

- 了解simbot: [**Simple Robot 应用手册**](https://simbot.forte.love)
- **Telegram组件**手册：(待建设)
- **API文档**: [**文档引导站点**](https://docs.simbot.forte.love) 中 Telegram 的 [**KDoc站点**](https://docs.simbot.forte.love/components/telegram)

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

