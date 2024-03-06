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

package love.forte.simbot.telegram.api.user

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.Serializable
import love.forte.simbot.telegram.api.SimpleBodyTelegramApi
import love.forte.simbot.telegram.api.TelegramApiResult
import love.forte.simbot.telegram.type.UserProfilePhotos
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic


/**
 * [getUserProfilePhotos](https://core.telegram.org/bots/api#getuserprofilephotos)
 *
 * Use this method to get a list of profile pictures for a user. Returns a [UserProfilePhotos] object.
 *
 * @author ForteScarlet
 */
public class GetUserProfilePhotosApi private constructor(body: Body) : SimpleBodyTelegramApi<UserProfilePhotos>() {
    public companion object Factory {
        private const val NAME = "getUserProfilePhotos"
        private val SER = TelegramApiResult.serializer(UserProfilePhotos.serializer())

        /**
         * Create [GetUserProfilePhotosApi]
         *
         * @param userId Unique identifier of the target user
         * @param offset Sequential number of the first photo to be returned. By default, all photos are returned.
         * @param limit Limits the number of photos to be retrieved. Values between 1-100 are accepted. Defaults to 100.
         *
         */
        @JvmStatic
        @JvmOverloads
        public fun create(userId: Int, offset: Int? = null, limit: Int? = null): GetUserProfilePhotosApi =
            GetUserProfilePhotosApi(Body(userId, offset, limit))

    }

    override val name: String
        get() = NAME

    override val body: Any = body

    override val responseDeserializer: DeserializationStrategy<UserProfilePhotos>
        get() = UserProfilePhotos.serializer()

    override val resultDeserializer: DeserializationStrategy<TelegramApiResult<UserProfilePhotos>>
        get() = SER

    @Serializable
    private data class Body(
        val userId: Int,
        val offset: Int? = null,
        val limit: Int? = null,
    )
}


public inline fun getUserProfilePhotosFlow(
    userId: Int,
    firstOffset: Int? = null,
    eachLimit: Int? = null,
    emitLastEmptyResult: Boolean = false,
    crossinline onEachResult: (UserProfilePhotos) -> UserProfilePhotos = { it },
    crossinline requestor: suspend (GetUserProfilePhotosApi) -> UserProfilePhotos
): Flow<UserProfilePhotos> {
    return flow {
        var api: GetUserProfilePhotosApi
        var offset: Int? = firstOffset

        while (true) {
            api = GetUserProfilePhotosApi.create(
                userId = userId,
                offset = offset,
                limit = eachLimit,
            )

            val photos = requestor(api).let(onEachResult)

            if (photos.photos.isEmpty()) {
                // done.
                if (emitLastEmptyResult) {
                    emit(photos)
                }
                break
            }

            emit(photos)

            val o = offset
            offset = if (o != null) {
                o + photos.photos.size
            } else {
                photos.photos.size
            }

        }
    }
}
