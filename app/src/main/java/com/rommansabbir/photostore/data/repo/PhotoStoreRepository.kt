package com.rommansabbir.photostore.data.repo

import com.rommansabbir.photostore.base.either.Either
import com.rommansabbir.photostore.data.models.PhotoStoreResponseModel
import com.rommansabbir.photostore.base.failure.Failure

interface PhotoStoreRepository {
    fun searchPhotos(
        page: Int,
        query: String,
        perPage: Int
    ): Either<Failure, PhotoStoreResponseModel>
}

