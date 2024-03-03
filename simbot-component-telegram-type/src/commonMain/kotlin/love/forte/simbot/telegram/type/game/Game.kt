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

package love.forte.simbot.telegram.type.game

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * [Game](https://core.telegram.org/bots/api#game)
 *
 * This object represents a game. Use BotFather to create and edit games, their short names will act as unique identifiers.
 *
 * @author ForteScarlet
 */
@Serializable
public data class Game(
    /**
     * Title of the game
     */
    val title: String,

    /**
     * Description of the game
     */
    val description: String,
    /**
     * Photo that will be displayed in the game message in chats.
     */
    val photo: List<love.forte.simbot.telegram.type.PhotoSize> = emptyList(),
    /**
     * Optional.
     * Brief description of the game or high scores included in the game message. Can be automatically edited to include current high scores for the game when the bot calls setGameScore, or manually edited using editMessageText. 0-4096 characters.
     */
    val text: String? = null,

    /**
     * Optional.
     * Special entities that appear in text, such as usernames, URLs, bot commands, etc.
     */
    @SerialName("text_entities")
    val textEntities: List<love.forte.simbot.telegram.type.MessageEntity>? = null,

    /**
     * Optional.
     * Animation that will be displayed in the game message in chats.
     * Upload via BotFather
     */
    val animation: love.forte.simbot.telegram.type.Animation? = null,
)
