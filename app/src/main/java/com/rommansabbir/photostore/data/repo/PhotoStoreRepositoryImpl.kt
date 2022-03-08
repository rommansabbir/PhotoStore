package com.rommansabbir.photostore.data.repo

import android.content.Context
import com.rommansabbir.photostore.BuildConfig
import com.rommansabbir.photostore.base.either.Either
import com.rommansabbir.photostore.base.failure.Failure
import com.rommansabbir.photostore.base.failure.NetworkConnectionError
import com.rommansabbir.photostore.base.neworking.executeAPIRequest
import com.rommansabbir.photostore.base.neworking.isInternetConnected
import com.rommansabbir.photostore.data.api.PhotoStoreService
import com.rommansabbir.photostore.data.models.PhotoStoreResponseModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PhotoStoreRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: PhotoStoreService
) : PhotoStoreRepository {
    /**
     * Load new images from the server side based on [query].
     * Check for interment connection first, then execute the API request.
     *
     * @param page [Int].
     * @param query [String].
     * @param perPage [Int].
     */
    override fun searchPhotos(
        page: Int,
        query: String,
        perPage: Int
    ): Either<Failure, PhotoStoreResponseModel> {
        return when (context.isInternetConnected()) {
            true -> {
                executeAPIRequest(
                    context,
                    apiService.searchPhotos(
                        BuildConfig.API_KEY /*Secret keys should be defined in gradle.properties*/,
                        page,
                        query,
                        perPage
                    ),
                    { it },
                    PhotoStoreResponseModel()
                )
            }
            else -> {
                Either.Left(NetworkConnectionError())
            }
        }
    }

}