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
 * [ReactionType](https://core.telegram.org/bots/api#reactiontype)
 *
 * This object describes the type of a reaction. Currently, it can be one of
 * ReactionTypeEmoji ReactionTypeCustomEmoji
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public class ReactionType {
    init {
        TODO("Empty class?")
    }
}

/**
 * [ReactionTypeCustomEmoji](https://core.telegram.org/bots/api#reactiontypecustomemoji)
 *
 * The reaction is based on a custom emoji.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class ReactionTypeCustomEmoji(
    /**
     * Type of the reaction, always “custom_emoji”
     *
     * type: `String`
     */
    public val type: String,
    /**
     * Custom emoji identifier
     *
     * type: `String`
     */
    @SerialName("custom_emoji_id")
    public val customEmojiId: String,
)

/**
 * [ReactionTypeEmoji](https://core.telegram.org/bots/api#reactiontypeemoji)
 *
 * The reaction is based on an emoji.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class ReactionTypeEmoji(
    /**
     * Type of the reaction, always “emoji”
     *
     * type: `String`
     */
    public val type: String,
    /**
     * Reaction emoji. 
     * Currently, it can be one of "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
     * "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
     * "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
     * "", "", "", "", "", "", "", "", ""
     *
     * type: `String`
     */
    public val emoji: String,
)
