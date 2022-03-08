package com.rommansabbir.photostore.base.di

import com.rommansabbir.photostore.base.neworking.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit = RetrofitClient.getRetrofitInstance
}