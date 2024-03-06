# Module simbot-component-telegram-api

> TODO


**API**

```Kotlin
val client: HttpClient = ...
val api = GetMeApi.create()

val result = api.requestData(client, "botTOKEN")
```

**getUpdates as flow (Long polling)**

```Kotlin
val client: HttpClient = ...
// Don't forget to set `request timeout` to a larger value to support client's long polling,
// e.g. requestTimeoutMillis = 60_000

val flow = getUpdateFlow(
    timeout = 60,  // Optional, but recommended configuration. Suggested `timeout` (to millis) <= `requestTimeout` millis
    limit = 100,   // Optional. parameter: limit, default: 100
    onEachResult = { // Optional. callback on eack result responsed. Default is `{ it }`
        println("On Each: $it")
        it
    }
) { api ->
    client.requestData(
        client,
        token = TOKEN, // Bot token. e.g. "bot123123.aaabbbccc"
    )
}

flow.collect { update ->
    // ...
}
```
