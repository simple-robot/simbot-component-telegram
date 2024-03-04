# stdlib-processor-extensions-processor

为所有 `Update` 中的事件类型提供一个 `onXxx` 扩展。

例如：

```Kotlin
Bot.onMessage(onPreProcessor = true / false) { update, message -> 
    // ...
}
```

基于 `Bot.process` 和 `Bot.preProcess` 。
