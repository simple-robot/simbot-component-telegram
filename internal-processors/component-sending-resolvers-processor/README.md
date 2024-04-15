# Component Events processor

用于在核心组件模块中生成所有基础的事件类型定义的接口。

会根据 `Update` 中的所有类型，定义所有如下类型的接口：

```kotlin

interface TelegramXxxEvent : TelegramEvent {
    override val sourceContent: Xxx // 实际上的事件类型
}

```
