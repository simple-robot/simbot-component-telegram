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

package love.forte.simbot.telegram.stdlib.bot

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.future.await
import kotlinx.coroutines.reactive.awaitLast
import kotlinx.coroutines.runInterruptible
import love.forte.simbot.annotations.Api4J
import love.forte.simbot.telegram.api.update.Update
import org.reactivestreams.Publisher
import java.util.concurrent.CompletionStage
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
    processor: BlockEventProcessor
): EventProcessor = BlockEventProcessorImpl(processor, coroutineContext ?: EmptyCoroutineContext)

/**
 * Block [EventProcessor].
 */
public fun interface BlockEventProcessor {
    public fun process(update: Update, name: String, value: Any)
}

private class BlockEventProcessorImpl(
    private val processor: BlockEventProcessor,
    private val context: CoroutineContext
) : EventProcessor {
    override suspend fun process(update: Update, name: String, value: Any) {
        runInterruptible(context) { processor.process(update, name, value) }
    }
}

/**
 * Create an async [EventProcessor].
 */
@Api4J
public fun async(processor: AsyncEventProcessor): EventProcessor = processor

/**
 * Async [EventProcessor].
 */
public fun interface AsyncEventProcessor : EventProcessor {
    public fun processAsync(update: Update, name: String, value: Any): CompletionStage<*>

    @JvmSynthetic
    override suspend fun process(update: Update, name: String, value: Any) {
        processAsync(update, name, value).await()
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
public fun reactive(processor: ReactiveEventProcessor): EventProcessor = processor

/**
 * Reactive [EventProcessor].
 */
public fun interface ReactiveEventProcessor : EventProcessor {
    public fun processReactive(update: Update, name: String, value: Any): Publisher<*>

    @JvmSynthetic
    override suspend fun process(update: Update, name: String, value: Any) {
        processReactive(update, name, value).awaitLast()
    }
}
