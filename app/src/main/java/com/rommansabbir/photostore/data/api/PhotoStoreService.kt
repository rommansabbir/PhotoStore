package com.rommansabbir.photostore.data.api

import com.rommansabbir.photostore.data.models.PhotoStoreResponseModel
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject

class PhotoStoreService @Inject constructor(private val retrofit: Retrofit) : PhotoStoreEndpoints {
    private val photoStore: PhotoStoreEndpoints by lazy { retrofit.create(PhotoStoreEndpoints::class.java) }
    override fun searchPhotos(
        header: String,
        page: Int,
        query: String,
        perPage: Int
    ): Call<PhotoStoreResponseModel> = photoStore.searchPhotos(header, page, query, perPage)

}