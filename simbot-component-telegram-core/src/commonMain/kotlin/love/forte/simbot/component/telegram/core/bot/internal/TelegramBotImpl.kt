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

package love.forte.simbot.component.telegram.core.bot.internal

import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import love.forte.simbot.annotations.FragileSimbotAPI
import love.forte.simbot.bot.JobBasedBot
import love.forte.simbot.common.id.ID
import love.forte.simbot.common.id.literal
import love.forte.simbot.component.telegram.core.bot.StdlibBot
import love.forte.simbot.component.telegram.core.bot.TelegramBot
import love.forte.simbot.component.telegram.core.bot.TelegramBotConfiguration
import love.forte.simbot.component.telegram.core.component.TelegramComponent
import love.forte.simbot.component.telegram.core.event.TelegramUnsupportedEvent
import love.forte.simbot.component.telegram.core.event.internal.TelegramChannelMessageEventImpl
import love.forte.simbot.component.telegram.core.event.internal.TelegramChatGroupMessageEventImpl
import love.forte.simbot.component.telegram.core.event.internal.TelegramPrivateMessageEventImpl
import love.forte.simbot.component.telegram.core.event.internal.TelegramSuperGroupMessageEventImpl
import love.forte.simbot.event.Event
import love.forte.simbot.event.EventDispatcher
import love.forte.simbot.event.onEachError
import love.forte.simbot.logger.LoggerFactory
import love.forte.simbot.telegram.api.bot.GetMeApi
import love.forte.simbot.telegram.api.update.SuspendableUpdateDivider
import love.forte.simbot.telegram.api.update.Update
import love.forte.simbot.telegram.stdlib.bot.SubscribeSequence
import love.forte.simbot.telegram.stdlib.bot.requestDataBy
import love.forte.simbot.telegram.type.*
import love.forte.simbot.telegram.type.ChatType.Companion.chatTypeOrNull
import love.forte.simbot.telegram.type.inline.ChosenInlineResult
import love.forte.simbot.telegram.type.inline.InlineQuery
import love.forte.simbot.telegram.type.payment.PreCheckoutQuery
import love.forte.simbot.telegram.type.payment.ShippingQuery
import kotlin.coroutines.CoroutineContext
import love.forte.simbot.telegram.stdlib.event.Event as StdlibEvent


/**
 *
 * @author ForteScarlet
 */
internal class TelegramBotImpl(
    override val job: Job,
    override val coroutineContext: CoroutineContext,
    override val component: TelegramComponent,
    override val source: StdlibBot,
    private val configuration: TelegramBotConfiguration,
    private val eventDispatcher: EventDispatcher,
) : TelegramBot, JobBasedBot() {
    internal val subContext = coroutineContext.minusKey(Job)
    internal val logger = LoggerFactory.getLogger("love.forte.simbot.component.telegram.core.bot")
    internal val eventLogger = LoggerFactory.getLogger("love.forte.simbot.component.telegram.stdlib.bot.event")

    private var _userInfo: User? = null

    override val userInfo: User
        get() = _userInfo ?: error("`userInfo` has not been initialized yet.")

    override suspend fun queryUserInfo(): User {
        return GetMeApi.create().requestDataBy(source).also {
            _userInfo = it
        }
    }

    override fun isMe(id: ID): Boolean {
        // TODO
        return source.ticket.token == id.literal
    }


    private val startLock = Mutex()

    override suspend fun start() {
        job.ensureActive()
        startLock.withLock {
            if (isStarted) {
                // already started.
                return
            }

            source.start()
            subscribeInternalProcessor(this, source, eventDispatcher)

            // mark started
            isStarted = true
        }
    }
}


@OptIn(FragileSimbotAPI::class)
internal fun subscribeInternalProcessor(
    bot: TelegramBotImpl,
    source: StdlibBot,
    eventDispatcher: EventDispatcher,
) {
    val divider = object : SuspendableUpdateDivider<StdlibEvent>() {
        private suspend fun pushEvent(event: Event) {
            bot.logger.debug("Bot {} on event: {}", bot, event)
            eventDispatcher.push(event)
                .onEachError { result ->
                    bot.eventLogger.error("Bot {} on event dispatch error result: {}", bot, result, result.content)
                }
                .onCompletion {
                    bot.eventLogger.trace("Bot {} event publish completed", bot)
                }
                .collect()
        }

        override suspend fun onMismatchUpdateEvent(name: String, value: Any, update: Update?, context: StdlibEvent) {
            bot.eventLogger.warn("onMismatchUpdateEvent(name={}, value={}, update={})", name, value, update)
            pushEvent(TelegramUnsupportedEvent(bot, context))
        }

        override suspend fun onMessage(
            name: String,
            value: Message,
            update: Update?,
            context: StdlibEvent
        ) {
            val chat = value.chat
            when (chat.chatTypeOrNull) {
                ChatType.GROUP -> {
                    pushEvent(
                        TelegramChatGroupMessageEventImpl(
                            bot = bot,
                            sourceEvent = context,
                            sourceContent = value
                        )
                    )
                }

                ChatType.PRIVATE -> {
                    pushEvent(
                        TelegramPrivateMessageEventImpl(
                            bot = bot,
                            sourceEvent = context,
                            sourceContent = value
                        )
                    )
                }

                ChatType.CHANNEL -> {
                    pushEvent(
                        TelegramChannelMessageEventImpl(
                            bot = bot,
                            sourceEvent = context,
                            sourceContent = value
                        )
                    )
                }

                // supergroup?
                ChatType.SUPERGROUP -> {
                    pushEvent(
                        TelegramSuperGroupMessageEventImpl(
                            bot = bot,
                            sourceEvent = context,
                            sourceContent = value
                        )
                    )
                }

                else -> onMismatchUpdateEvent(name, value, update, context)
            }
        }

        override suspend fun onEditedMessage(
            name: String,
            value: Message,
            update: Update?,
            context: StdlibEvent
        ) {
            super.onEditedMessage(name, value, update, context)
        }

        override suspend fun onChannelPost(
            name: String,
            value: Message,
            update: Update?,
            context: StdlibEvent
        ) {
            super.onChannelPost(name, value, update, context)
        }

        override suspend fun onEditedChannelPost(
            name: String,
            value: Message,
            update: Update?,
            context: StdlibEvent
        ) {
            super.onEditedChannelPost(name, value, update, context)
        }

        override suspend fun onMessageReaction(
            name: String,
            value: MessageReactionUpdated,
            update: Update?,
            context: StdlibEvent
        ) {
            super.onMessageReaction(name, value, update, context)
        }

        override suspend fun onMessageReactionCount(
            name: String,
            value: MessageReactionCountUpdated,
            update: Update?,
            context: StdlibEvent
        ) {
            super.onMessageReactionCount(name, value, update, context)
        }

        override suspend fun onInlineQuery(
            name: String,
            value: InlineQuery,
            update: Update?,
            context: StdlibEvent
        ) {
            super.onInlineQuery(name, value, update, context)
        }

        override suspend fun onChosenInlineResult(
            name: String,
            value: ChosenInlineResult,
            update: Update?,
            context: StdlibEvent
        ) {
            super.onChosenInlineResult(name, value, update, context)
        }

        override suspend fun onCallbackQuery(
            name: String,
            value: CallbackQuery,
            update: Update?,
            context: StdlibEvent
        ) {
            super.onCallbackQuery(name, value, update, context)
        }

        override suspend fun onShippingQuery(
            name: String,
            value: ShippingQuery,
            update: Update?,
            context: StdlibEvent
        ) {
            super.onShippingQuery(name, value, update, context)
        }

        override suspend fun onPreCheckoutQuery(
            name: String,
            value: PreCheckoutQuery,
            update: Update?,
            context: StdlibEvent
        ) {
            super.onPreCheckoutQuery(name, value, update, context)
        }

        override suspend fun onPoll(
            name: String,
            value: Poll,
            update: Update?,
            context: StdlibEvent
        ) {
            super.onPoll(name, value, update, context)
        }

        override suspend fun onPollAnswer(
            name: String,
            value: PollAnswer,
            update: Update?,
            context: StdlibEvent
        ) {
            super.onPollAnswer(name, value, update, context)
        }

        override suspend fun onMyChatMember(
            name: String,
            value: ChatMemberUpdated,
            update: Update?,
            context: StdlibEvent
        ) {
            super.onMyChatMember(name, value, update, context)
        }

        override suspend fun onChatMember(
            name: String,
            value: ChatMemberUpdated,
            update: Update?,
            context: StdlibEvent
        ) {
            super.onChatMember(name, value, update, context)
        }

        override suspend fun onChatJoinRequest(
            name: String,
            value: ChatJoinRequest,
            update: Update?,
            context: StdlibEvent
        ) {
            super.onChatJoinRequest(name, value, update, context)
        }

        override suspend fun onChatBoost(
            name: String,
            value: ChatBoostUpdated,
            update: Update?,
            context: StdlibEvent
        ) {
            super.onChatBoost(name, value, update, context)
        }

        override suspend fun onRemovedChatBoost(
            name: String,
            value: ChatBoostRemoved,
            update: Update?,
            context: StdlibEvent
        ) {
            super.onRemovedChatBoost(name, value, update, context)
        }
    }

    source.subscribe(SubscribeSequence.NORMAL) { event ->
        bot.eventLogger.debug("Accept event: {}", event)
        divider.accept(event.name, event.content, event.update, event)
    }
}

