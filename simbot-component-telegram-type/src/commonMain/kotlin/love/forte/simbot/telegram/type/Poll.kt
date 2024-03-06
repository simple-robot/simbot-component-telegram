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
 * [Poll](https://core.telegram.org/bots/api#poll)
 *
 * This object contains information about a poll.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class Poll(
    /**
     * Unique poll identifier
     *
     * type: `String`
     */
    public val id: String,
    /**
     * Poll question, 1-300 characters
     *
     * type: `String`
     */
    public val question: String,
    /**
     * List of poll options
     *
     * type: `Array of PollOption`
     */
    public val options: List<PollOption> = emptyList(),
    /**
     * Total number of users that voted in the poll
     *
     * type: `Integer`
     */
    @SerialName("total_voter_count")
    public val totalVoterCount: Int,
    /**
     * True, if the poll is closed
     *
     * type: `Boolean`
     */
    @SerialName("is_closed")
    public val isClosed: Boolean,
    /**
     * True, if the poll is anonymous
     *
     * type: `Boolean`
     */
    @SerialName("is_anonymous")
    public val isAnonymous: Boolean,
    /**
     * Poll type, currently can be “regular” or “quiz”
     *
     * type: `String`
     */
    public val type: String,
    /**
     * True, if the poll allows multiple answers
     *
     * type: `Boolean`
     */
    @SerialName("allows_multiple_answers")
    public val allowsMultipleAnswers: Boolean,
    /**
     * Optional.
     * 0-based identifier of the correct answer option.
     * Available only for polls in the quiz mode, which are closed, or was sent (not forwarded) by
     * the bot or to the private chat with the bot.
     *
     * type: `Integer`
     */
    @SerialName("correct_option_id")
    public val correctOptionId: Int? = null,
    /**
     * Optional.
     * Text that is shown when a user chooses an incorrect answer or taps on the lamp icon in a
     * quiz-style poll, 0-200 characters
     *
     * type: `String`
     */
    public val explanation: String? = null,
    /**
     * Optional.
     * Special entities like usernames, URLs, bot commands, etc.
     * that appear in the explanation
     *
     * type: `Array of MessageEntity`
     */
    @SerialName("explanation_entities")
    public val explanationEntities: List<MessageEntity>? = null,
    /**
     * Optional.
     * Amount of time in seconds the poll will be active after creation
     *
     * type: `Integer`
     */
    @SerialName("open_period")
    public val openPeriod: Int? = null,
    /**
     * Optional.
     * Point in time (Unix timestamp) when the poll will be automatically closed
     *
     * type: `Integer`
     */
    @SerialName("close_date")
    public val closeDate: Int? = null,
)

/**
 * [PollAnswer](https://core.telegram.org/bots/api#pollanswer)
 *
 * This object represents an answer of a user in a non-anonymous poll.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class PollAnswer(
    /**
     * Unique poll identifier
     *
     * type: `String`
     */
    @SerialName("poll_id")
    public val pollId: String,
    /**
     * Optional.
     * The chat that changed the answer to the poll, if the voter is anonymous
     *
     * type: `Chat`
     */
    @SerialName("voter_chat")
    public val voterChat: Chat? = null,
    /**
     * Optional.
     * The user that changed the answer to the poll, if the voter isn't anonymous
     *
     * type: `User`
     */
    public val user: User? = null,
    /**
     * 0-based identifiers of chosen answer options.
     * May be empty if the vote was retracted.
     *
     * type: `Array of Integer`
     */
    @SerialName("option_ids")
    public val optionIds: List<Int> = emptyList(),
)

/**
 * [PollOption](https://core.telegram.org/bots/api#polloption)
 *
 * This object contains information about one answer option in a poll.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class PollOption(
    /**
     * Option text, 1-100 characters
     *
     * type: `String`
     */
    public val text: String,
    /**
     * Number of users that voted for this option
     *
     * type: `Integer`
     */
    @SerialName("voter_count")
    public val voterCount: Int,
)
