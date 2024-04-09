# Module simbot-component-telegram-type

Provides definitions for most types in Telegram and supports serialization based on 
[Kotlin Serialization](https://github.com/Kotlin/kotlinx.serialization).

Support platforms:

- JVM
- JS
- Native (Tier 1~3, see [Kotlin/Native target support](https://kotlinlang.org/docs/native-target-support.html))
- Wasm (Experimental)

See also:

- [Telegram available types](https://core.telegram.org/bots/api#available-types)

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

## Notes
### Compatibility

Currently, most types in modules are defined using `data class`. These types are considered to be used for serialization.
When officials modify/extend a type (such as adding fields), binary compatibility of the data class may not be guaranteed 
(the parameters of the constructor have changed).

This change usually increments the `minor` version number.
