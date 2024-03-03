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
 * [Giveaway](https://core.telegram.org/bots/api#giveaway)
 *
 * This object represents a message about a scheduled giveaway.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class Giveaway(
    /**
     * The list of chats which the user must join to participate in the giveaway
     *
     * type: `Array of Chat`
     */
    public val chats: List<love.forte.simbot.telegram.type.Chat> = emptyList(),
    /**
     * Point in time (Unix timestamp) when winners of the giveaway will be selected
     *
     * type: `Integer`
     */
    @SerialName("winners_selection_date")
    public val winnersSelectionDate: Int,
    /**
     * The number of users which are supposed to be selected as winners of the giveaway
     *
     * type: `Integer`
     */
    @SerialName("winner_count")
    public val winnerCount: Int,
    /**
     * Optional. 
     * True, if only users who join the chats after the giveaway started should be eligible to win
     *
     * type: `True`
     */
    @SerialName("only_new_members")
    public val onlyNewMembers: Boolean? = true,
    /**
     * Optional. 
     * True, if the list of giveaway winners will be visible to everyone
     *
     * type: `True`
     */
    @SerialName("has_public_winners")
    public val hasPublicWinners: Boolean? = true,
    /**
     * Optional. 
     * Description of additional giveaway prize
     *
     * type: `String`
     */
    @SerialName("prize_description")
    public val prizeDescription: String? = null,
    /**
     * Optional. 
     * A list of two-letter ISO 3166-1 alpha-2 country codes indicating the countries from which
     * eligible users for the giveaway must come. 
     * If empty, then all users can participate in the giveaway. 
     * Users with a phone number that was bought on Fragment can always participate in giveaways.
     *
     * type: `Array of String`
     */
    @SerialName("country_codes")
    public val countryCodes: List<String>? = null,
    /**
     * Optional. 
     * The number of months the Telegram Premium subscription won from the giveaway will be active
     * for
     *
     * type: `Integer`
     */
    @SerialName("premium_subscription_month_count")
    public val premiumSubscriptionMonthCount: Int? = null,
)

/**
 * [GiveawayCompleted](https://core.telegram.org/bots/api#giveawaycompleted)
 *
 * This object represents a service message about the completion of a giveaway without public
 * winners.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class GiveawayCompleted(
    /**
     * Number of winners in the giveaway
     *
     * type: `Integer`
     */
    @SerialName("winner_count")
    public val winnerCount: Int,
    /**
     * Optional. 
     * Number of undistributed prizes
     *
     * type: `Integer`
     */
    @SerialName("unclaimed_prize_count")
    public val unclaimedPrizeCount: Int? = null,
    /**
     * Optional. 
     * Message with the giveaway that was completed, if it wasn't deleted
     *
     * type: `Message`
     */
    @SerialName("giveaway_message")
    public val giveawayMessage: love.forte.simbot.telegram.type.Message? = null,
)

/**
 * [GiveawayCreated](https://core.telegram.org/bots/api#giveawaycreated)
 *
 * This object represents a service message about the creation of a scheduled giveaway. Currently
 * holds no information.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public class GiveawayCreated {
    // TODO Empty class?

}

/**
 * [GiveawayWinners](https://core.telegram.org/bots/api#giveawaywinners)
 *
 * This object represents a message about the completion of a giveaway with public winners.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class GiveawayWinners(
    /**
     * The chat that created the giveaway
     *
     * type: `Chat`
     */
    public val chat: love.forte.simbot.telegram.type.Chat,
    /**
     * Identifier of the message with the giveaway in the chat
     *
     * type: `Integer`
     */
    @SerialName("giveaway_message_id")
    public val giveawayMessageId: Int,
    /**
     * Point in time (Unix timestamp) when winners of the giveaway were selected
     *
     * type: `Integer`
     */
    @SerialName("winners_selection_date")
    public val winnersSelectionDate: Int,
    /**
     * Total number of winners in the giveaway
     *
     * type: `Integer`
     */
    @SerialName("winner_count")
    public val winnerCount: Int,
    /**
     * List of up to 100 winners of the giveaway
     *
     * type: `Array of User`
     */
    public val winners: List<love.forte.simbot.telegram.type.User> = emptyList(),
    /**
     * Optional. 
     * The number of other chats the user had to join in order to be eligible for the giveaway
     *
     * type: `Integer`
     */
    @SerialName("additional_chat_count")
    public val additionalChatCount: Int? = null,
    /**
     * Optional. 
     * The number of months the Telegram Premium subscription won from the giveaway will be active
     * for
     *
     * type: `Integer`
     */
    @SerialName("premium_subscription_month_count")
    public val premiumSubscriptionMonthCount: Int? = null,
    /**
     * Optional. 
     * Number of undistributed prizes
     *
     * type: `Integer`
     */
    @SerialName("unclaimed_prize_count")
    public val unclaimedPrizeCount: Int? = null,
    /**
     * Optional. 
     * True, if only users who had joined the chats after the giveaway started were eligible to win
     *
     * type: `True`
     */
    @SerialName("only_new_members")
    public val onlyNewMembers: Boolean? = true,
    /**
     * Optional. 
     * True, if the giveaway was canceled because the payment for it was refunded
     *
     * type: `True`
     */
    @SerialName("was_refunded")
    public val wasRefunded: Boolean? = true,
    /**
     * Optional. 
     * Description of additional giveaway prize
     *
     * type: `String`
     */
    @SerialName("prize_description")
    public val prizeDescription: String? = null,
)
