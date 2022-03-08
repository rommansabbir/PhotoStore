package com.rommansabbir.photostore.base.data

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.*
import javax.inject.Inject

interface PhotoStoreEndpoints {
    @Headers("Content-Type: application/json")
    @GET("/v1/search")
    fun searchPhotos(
        @Header("Authorization") header: String,
        @Query("page") page: Int,
        @Query("query") query: String,
        @Query("per_page") perPage: Int,
    ): Call<PhotoStoreResponseModel>
}

class PhotoStoreService @Inject constructor(private val retrofit: Retrofit) : PhotoStoreEndpoints {
    private val photoStore: PhotoStoreEndpoints by lazy { retrofit.create(PhotoStoreEndpoints::class.java) }
    override fun searchPhotos(
        header: String,
        page: Int,
        query: String,
        perPage: Int
    ): Call<PhotoStoreResponseModel> = photoStore.searchPhotos(header, page, query, perPage)

}