package com.rommansabbir.photostore.base.di

import android.content.Context
import com.rommansabbir.photostore.base.data.PhotoStoreRepository
import com.rommansabbir.photostore.base.data.PhotoStoreRepositoryImpl
import com.rommansabbir.photostore.base.data.PhotoStoreService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object APIModule {
    @Provides
    fun providePhotoStoreRepository(
        @ApplicationContext context: Context,
        apiService: PhotoStoreService
    ): PhotoStoreRepository = PhotoStoreRepositoryImpl(context, apiService)
}