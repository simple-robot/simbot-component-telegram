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

package love.forte.simbot.telegram.type

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * [ForumTopic](https://core.telegram.org/bots/api#forumtopic)
 *
 * This object represents a forum topic.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class ForumTopic(
    /**
     * Unique identifier of the forum topic
     *
     * type: `Integer`
     */
    @SerialName("message_thread_id")
    public val messageThreadId: Int,
    /**
     * Name of the topic
     *
     * type: `String`
     */
    public val name: String,
    /**
     * Color of the topic icon in RGB format
     *
     * type: `Integer`
     */
    @SerialName("icon_color")
    public val iconColor: Int,
    /**
     * Optional.
     * Unique identifier of the custom emoji shown as the topic icon
     *
     * type: `String`
     */
    @SerialName("icon_custom_emoji_id")
    public val iconCustomEmojiId: String? = null,
)

/**
 * [ForumTopicClosed](https://core.telegram.org/bots/api#forumtopicclosed)
 *
 * This object represents a service message about a forum topic closed in the chat. Currently holds
 * no information.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public class ForumTopicClosed

/**
 * [ForumTopicCreated](https://core.telegram.org/bots/api#forumtopiccreated)
 *
 * This object represents a service message about a new forum topic created in the chat.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class ForumTopicCreated(
    /**
     * Name of the topic
     *
     * type: `String`
     */
    public val name: String,
    /**
     * Color of the topic icon in RGB format
     *
     * type: `Integer`
     */
    @SerialName("icon_color")
    public val iconColor: Int,
    /**
     * Optional.
     * Unique identifier of the custom emoji shown as the topic icon
     *
     * type: `String`
     */
    @SerialName("icon_custom_emoji_id")
    public val iconCustomEmojiId: String? = null,
)

/**
 * [ForumTopicEdited](https://core.telegram.org/bots/api#forumtopicedited)
 *
 * This object represents a service message about an edited forum topic.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class ForumTopicEdited(
    /**
     * Optional.
     * New name of the topic, if it was edited
     *
     * type: `String`
     */
    public val name: String? = null,
    /**
     * Optional.
     * New identifier of the custom emoji shown as the topic icon, if it was edited; an empty string
     * if the icon was removed
     *
     * type: `String`
     */
    @SerialName("icon_custom_emoji_id")
    public val iconCustomEmojiId: String? = null,
)

/**
 * [ForumTopicReopened](https://core.telegram.org/bots/api#forumtopicreopened)
 *
 * This object represents a service message about a forum topic reopened in the chat. Currently
 * holds no information.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public class ForumTopicReopened
