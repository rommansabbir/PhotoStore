package com.rommansabbir.photostore.base.data

import android.content.Context
import com.rommansabbir.photostore.BuildConfig
import com.rommansabbir.photostore.base.Either
import com.rommansabbir.photostore.base.Failure
import com.rommansabbir.photostore.base.NetworkConnectionError
import com.rommansabbir.photostore.base.neworking.executeAPIRequest
import com.rommansabbir.photostore.base.neworking.isInternetConnected
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface PhotoStoreRepository {
    fun searchPhotos(
        page: Int,
        query: String,
        perPage: Int
    ): Either<Failure, PhotoStoreResponseModel>
}

class PhotoStoreRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: PhotoStoreService
) : PhotoStoreRepository {
    override fun searchPhotos(
        page: Int,
        query: String,
        perPage: Int
    ): Either<Failure, PhotoStoreResponseModel> {
        return when (context.isInternetConnected()) {
            true -> {
                executeAPIRequest(
                    context,
                    apiService.searchPhotos(BuildConfig.API_KEY, page, query, perPage),
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