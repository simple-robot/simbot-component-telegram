# Module simbot-component-telegram-type

提供 Telegram 中的绝大多数类型定义，并基于
[Kotlin Serialization](https://github.com/Kotlin/kotlinx.serialization)
实现可序列化。

支持的平台:

- JVM
- JS
- Native (支持 Tier 1~3, 参考 [Kotlin/Native target support](https://kotlinlang.org/docs/native-target-support.html))
- Wasm (Experimental)

更多参考:

- [Telegram available types](https://core.telegram.org/bots/api#available-types)

## 安装

> [!note]
> 版本 (即下文的 `VERSION`) 前往 [releases](https://github.com/simple-robot/simbot-component-telegram/releases) 参考.

### Gradle

`build.gradle.kts`

```kotlin
plugins {
    kotlin("...") version "..."
}

dependencies {
    implementation("love.forte.simbot.component:simbot-component-telegram-type:$VERSION")
}
```

### Maven

```xml
<dependencies>
    <dependency>
        <groupId>love.forte.simbot.component</groupId>
        <artifactId>simbot-component-telegram-type-jvm</artifactId>
        <version>${VERSION}</version>
    </dependency>
</dependencies>
```
