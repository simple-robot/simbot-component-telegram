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
 * [ExternalReplyInfo](https://core.telegram.org/bots/api#externalreplyinfo)
 *
 * This object contains information about a message that is being replied to, which may come from
 * another chat or forum topic.
 *
 * (auto-generated)
 * @author ForteScarlet
 */
@Serializable
public data class ExternalReplyInfo(
    /**
     * Origin of the message replied to by the given message
     *
     * type: `MessageOrigin`
     */
    public val origin: MessageOrigin,
    /**
     * Optional.
     * Chat the original message belongs to.
     * Available only if the chat is a supergroup or a channel.
     *
     * type: `Chat`
     */
    public val chat: Chat? = null,
    /**
     * Optional.
     * Unique message identifier inside the original chat.
     * Available only if the original chat is a supergroup or a channel.
     *
     * type: `Integer`
     */
    @SerialName("message_id")
    public val messageId: Int? = null,
    /**
     * Optional.
     * Options used for link preview generation for the original message, if it is a text message
     *
     * type: `LinkPreviewOptions`
     */
    @SerialName("link_preview_options")
    public val linkPreviewOptions: LinkPreviewOptions? = null,
    /**
     * Optional.
     * Message is an animation, information about the animation
     *
     * type: `Animation`
     */
    public val animation: Animation? = null,
    /**
     * Optional.
     * Message is an audio file, information about the file
     *
     * type: `Audio`
     */
    public val audio: Audio? = null,
    /**
     * Optional.
     * Message is a general file, information about the file
     *
     * type: `Document`
     */
    public val document: Document? = null,
    /**
     * Optional.
     * Message is a photo, available sizes of the photo
     *
     * type: `Array of PhotoSize`
     */
    public val photo: List<PhotoSize>? = null,
    /**
     * Optional.
     * Message is a sticker, information about the sticker
     *
     * type: `Sticker`
     */
    public val sticker: love.forte.simbot.telegram.type.sticker.Sticker? = null,
    /**
     * Optional.
     * Message is a forwarded story
     *
     * type: `Story`
     */
    public val story: Story? = null,
    /**
     * Optional.
     * Message is a video, information about the video
     *
     * type: `Video`
     */
    public val video: Video? = null,
    /**
     * Optional.
     * Message is a video note, information about the video message
     *
     * type: `VideoNote`
     */
    @SerialName("video_note")
    public val videoNote: VideoNote? = null,
    /**
     * Optional.
     * Message is a voice message, information about the file
     *
     * type: `Voice`
     */
    public val voice: Voice? = null,
    /**
     * Optional.
     * True, if the message media is covered by a spoiler animation
     *
     * type: `True`
     */
    @SerialName("has_media_spoiler")
    public val hasMediaSpoiler: Boolean? = null,
    /**
     * Optional.
     * Message is a shared contact, information about the contact
     *
     * type: `Contact`
     */
    public val contact: Contact? = null,
    /**
     * Optional.
     * Message is a dice with random value
     *
     * type: `Dice`
     */
    public val dice: Dice? = null,
    /**
     * Optional.
     * Message is a game, information about the game.
     * More about games »
     *
     * type: `Game`
     */
    public val game: love.forte.simbot.telegram.type.game.Game? = null,
    /**
     * Optional.
     * Message is a scheduled giveaway, information about the giveaway
     *
     * type: `Giveaway`
     */
    public val giveaway: Giveaway? = null,
    /**
     * Optional.
     * A giveaway with public winners was completed
     *
     * type: `GiveawayWinners`
     */
    @SerialName("giveaway_winners")
    public val giveawayWinners: GiveawayWinners? = null,
    /**
     * Optional.
     * Message is an invoice for a payment, information about the invoice.
     * More about payments »
     *
     * type: `Invoice`
     */
    public val invoice: love.forte.simbot.telegram.type.payment.Invoice? = null,
    /**
     * Optional.
     * Message is a shared location, information about the location
     *
     * type: `Location`
     */
    public val location: Location? = null,
    /**
     * Optional.
     * Message is a native poll, information about the poll
     *
     * type: `Poll`
     */
    public val poll: Poll? = null,
    /**
     * Optional.
     * Message is a venue, information about the venue
     *
     * type: `Venue`
     */
    public val venue: Venue? = null,
)
