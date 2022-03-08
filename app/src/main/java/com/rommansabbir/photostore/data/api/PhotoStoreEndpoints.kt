package com.rommansabbir.photostore.data.api

import com.rommansabbir.photostore.data.models.PhotoStoreResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

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

