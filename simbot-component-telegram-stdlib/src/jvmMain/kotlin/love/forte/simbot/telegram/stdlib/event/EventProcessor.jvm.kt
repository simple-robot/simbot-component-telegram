/*
 * Copyright (c) 2024. ForteScarlet.
 *
 * This file is part of simbot-component-telegram.
 *
 * simbot-component-telegram is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * simbot-component-telegram is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with simbot-component-telegram.
 * If not, see <https://www.gnu.org/licenses/>.
 */

@file:JvmName("EventProcessors")
@file:JvmMultifileClass

package love.forte.simbot.telegram.stdlib.event

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.future.await
import kotlinx.coroutines.reactive.awaitLast
import kotlinx.coroutines.runInterruptible
import love.forte.simbot.annotations.Api4J
import org.reactivestreams.Publisher
import java.util.concurrent.CompletionStage
import java.util.function.BiConsumer
import java.util.function.BiFunction
import java.util.function.Consumer
import java.util.function.Function
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Create a block [EventProcessor].
 *
 * @param coroutineContext A [CoroutineContext] used in [runInterruptible]
 * of the [BlockEventProcessor.process][EventProcessor.process].
 * [Dispatchers.IO] is the default, set to `null` to use [EmptyCoroutineContext].
 */
@Api4J
@JvmOverloads
public fun block(
    coroutineContext: CoroutineContext? = Dispatchers.IO,
    processor: Consumer<Event>
): EventProcessor {
    if (coroutineContext == null) {
        return EventProcessor { runInterruptible { processor.accept(it) } }
    }

    return EventProcessor { runInterruptible(coroutineContext) { processor.accept(it) } }
}

/**
 * Create a block [EventProcessor].
 *
 * @param coroutineContext A [CoroutineContext] used in [runInterruptible]
 * of the [BlockEventProcessor.process][EventProcessor.process].
 * [Dispatchers.IO] is the default, set to `null` to use [EmptyCoroutineContext].
 *
 * @param processor Processor function.
 * `(Event sourceEvent, T eventContent) -> { ... }`
 */
@Api4J
@JvmOverloads
public fun <T : Any> block(
    contentType: Class<T>,
    name: String? = null,
    coroutineContext: CoroutineContext? = Dispatchers.IO,
    processor: BiConsumer<Event, T>
): EventProcessor {
    val context = coroutineContext ?: EmptyCoroutineContext
    if (name == null) {
        return EventProcessor {
            if (contentType.isInstance(it.content)) {
                runInterruptible(context) { processor.accept(it, contentType.cast(it.content)) }
            }
        }
    }

    return EventProcessor {
        if (name == it.name && contentType.isInstance(it.content)) {
            runInterruptible(context) { processor.accept(it, contentType.cast(it.content)) }
        }
    }
}

/**
 * Create an async [EventProcessor].
 */
@Api4J
public fun async(processor: Function<Event, CompletionStage<*>>): EventProcessor = EventProcessor {
    processor.apply(it).await()
}

/**
 * Create an async [EventProcessor].
 */
@Api4J
@JvmOverloads
public fun <T : Any> async(
    contentType: Class<T>,
    name: String? = null,
    processor: BiFunction<Event, T, CompletionStage<*>>
): EventProcessor {
    if (name == null) {
        return EventProcessor {
            if (contentType.isInstance(it.content)) {
                processor.apply(it, contentType.cast(it.content)).await()
            }
        }
    }

    return EventProcessor {
        if (name == it.name && contentType.isInstance(it.content)) {
            processor.apply(it, contentType.cast(it.content)).await()
        }
    }
}


/**
 * Create a reactor [EventProcessor].
 *
 * Note: if you want to use [reactive],
 * you also need to make sure the runtime contain
 * [kotlinx.coroutines.reactive](https://github.com/Kotlin/kotlinx.coroutines/tree/master/reactive).
 *
 */
@Api4J
public fun reactive(processor: Function<Event, Publisher<*>>): EventProcessor = EventProcessor {
    processor.apply(it).awaitLast()
}


/**
 * Create a reactor [EventProcessor].
 *
 * Note: if you want to use [reactive],
 * you also need to make sure the runtime contain
 * [kotlinx.coroutines.reactive](https://github.com/Kotlin/kotlinx.coroutines/tree/master/reactive).
 *
 */
@Api4J
public fun <T : Any> reactive(
    type: Class<T>,
    name: String? = null,
    processor: BiFunction<Event, T, Publisher<*>>
): EventProcessor {
    if (name == null) {
        return EventProcessor {
            if (type.isInstance(it.content)) {
                processor.apply(it, type.cast(it.content))
            }
        }
    }

    return EventProcessor {
        if (name == it.name && type.isInstance(it.content)) {
            processor.apply(it, type.cast(it.content))
        }
    }
}

