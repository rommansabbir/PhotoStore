package com.rommansabbir.photostore.usecases

import com.rommansabbir.photostore.base.Either
import com.rommansabbir.photostore.base.Failure
import com.rommansabbir.photostore.base.UseCase
import com.rommansabbir.photostore.base.data.PhotoSearchRequestModel
import com.rommansabbir.photostore.base.data.PhotoStoreRepository
import com.rommansabbir.photostore.base.data.PhotoStoreResponseModel
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class SearchPhotosUseCase @Inject constructor(private val repository: PhotoStoreRepository) :
    UseCase<PhotoStoreResponseModel, PhotoSearchRequestModel>() {
    override suspend fun run(params: PhotoSearchRequestModel): Either<Failure, PhotoStoreResponseModel> =
        repository.searchPhotos(params.page, params.query, params.perPage)

    companion object {
        fun execute(
            useCase: SearchPhotosUseCase,
            scope: CoroutineScope,
            requestModel: PhotoSearchRequestModel,
            onSuccess: (PhotoStoreResponseModel) -> Unit,
            onError: (Failure) -> Unit
        ) {
            useCase(scope, requestModel) { it.either(onError, onSuccess) }
        }
    }
}