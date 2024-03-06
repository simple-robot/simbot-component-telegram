# Module simbot-component-telegram-api

> TODO


**API**

```Kotlin
val client: HttpClient = ...
val api = GetMeApi.create()

val result = api.requestData(client, "botTOKEN")
```

**getUpdates as flow (长轮询)**

```Kotlin
val client: HttpClient = ...
// 不要忘了为 `request timeout` 设置一个比较大的值来支持 client 使用'长'轮询（一些引擎会自己默认配置一个不是很长的超时时间），
// 比如： requestTimeoutMillis = 60_000

val flow = getUpdateFlow(
    timeout = 60,  // Optional, but recommended configuration. Suggested `timeout` (to millis) <= `requestTimeout` millis
    limit = 100,   // Optional. parameter: limit, default: 100
    onEachResult = { // Optional. callback on eack result responsed. Default is `{ it }`
        println("On Each: $it")
        it
    }
) { api ->
    api.requestData(
        client,
        token = TOKEN, // Bot token. e.g. "bot123123.aaabbbccc"
    )
}

flow.collect { update ->
    // ...
}
```
