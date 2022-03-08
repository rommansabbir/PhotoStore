package com.rommansabbir.photostore.base.di

import android.content.Context
import com.rommansabbir.photostore.data.api.PhotoStoreService
import com.rommansabbir.photostore.data.repo.PhotoStoreRepository
import com.rommansabbir.photostore.data.repo.PhotoStoreRepositoryImpl
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