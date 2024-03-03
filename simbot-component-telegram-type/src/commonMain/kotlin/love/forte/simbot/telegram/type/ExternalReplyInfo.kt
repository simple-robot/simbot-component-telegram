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
    public val origin: love.forte.simbot.telegram.type.MessageOrigin,
    /**
     * Optional. 
     * Chat the original message belongs to. 
     * Available only if the chat is a supergroup or a channel.
     *
     * type: `Chat`
     */
    public val chat: love.forte.simbot.telegram.type.Chat? = null,
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
    public val linkPreviewOptions: love.forte.simbot.telegram.type.LinkPreviewOptions? = null,
    /**
     * Optional. 
     * Message is an animation, information about the animation
     *
     * type: `Animation`
     */
    public val animation: love.forte.simbot.telegram.type.Animation? = null,
    /**
     * Optional. 
     * Message is an audio file, information about the file
     *
     * type: `Audio`
     */
    public val audio: love.forte.simbot.telegram.type.Audio? = null,
    /**
     * Optional. 
     * Message is a general file, information about the file
     *
     * type: `Document`
     */
    public val document: love.forte.simbot.telegram.type.Document? = null,
    /**
     * Optional. 
     * Message is a photo, available sizes of the photo
     *
     * type: `Array of PhotoSize`
     */
    public val photo: List<love.forte.simbot.telegram.type.PhotoSize>? = null,
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
    public val story: love.forte.simbot.telegram.type.Story? = null,
    /**
     * Optional. 
     * Message is a video, information about the video
     *
     * type: `Video`
     */
    public val video: love.forte.simbot.telegram.type.Video? = null,
    /**
     * Optional. 
     * Message is a video note, information about the video message
     *
     * type: `VideoNote`
     */
    @SerialName("video_note")
    public val videoNote: love.forte.simbot.telegram.type.VideoNote? = null,
    /**
     * Optional. 
     * Message is a voice message, information about the file
     *
     * type: `Voice`
     */
    public val voice: love.forte.simbot.telegram.type.Voice? = null,
    /**
     * Optional. 
     * True, if the message media is covered by a spoiler animation
     *
     * type: `True`
     */
    @SerialName("has_media_spoiler")
    public val hasMediaSpoiler: Boolean? = true,
    /**
     * Optional. 
     * Message is a shared contact, information about the contact
     *
     * type: `Contact`
     */
    public val contact: love.forte.simbot.telegram.type.Contact? = null,
    /**
     * Optional. 
     * Message is a dice with random value
     *
     * type: `Dice`
     */
    public val dice: love.forte.simbot.telegram.type.Dice? = null,
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
    public val giveaway: love.forte.simbot.telegram.type.Giveaway? = null,
    /**
     * Optional. 
     * A giveaway with public winners was completed
     *
     * type: `GiveawayWinners`
     */
    @SerialName("giveaway_winners")
    public val giveawayWinners: love.forte.simbot.telegram.type.GiveawayWinners? = null,
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
    public val location: love.forte.simbot.telegram.type.Location? = null,
    /**
     * Optional. 
     * Message is a native poll, information about the poll
     *
     * type: `Poll`
     */
    public val poll: love.forte.simbot.telegram.type.Poll? = null,
    /**
     * Optional. 
     * Message is a venue, information about the venue
     *
     * type: `Venue`
     */
    public val venue: love.forte.simbot.telegram.type.Venue? = null,
)
