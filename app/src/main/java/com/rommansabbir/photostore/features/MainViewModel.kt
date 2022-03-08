package com.rommansabbir.photostore.features

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rommansabbir.photostore.base.Failure
import com.rommansabbir.photostore.base.data.PhotoSearchRequestModel
import com.rommansabbir.photostore.base.data.PhotoStoreResponseModel
import com.rommansabbir.photostore.usecases.SearchPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val useCase: SearchPhotosUseCase) : ViewModel() {
    private var mLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = mLoading

    fun setLoading(value: Boolean) {
        mLoading.value = value
    }

    fun searchPhotos(
        requestModel: PhotoSearchRequestModel,
        onSuccess: (PhotoStoreResponseModel) -> Unit,
        onError: (Failure) -> Unit
    ) {
        SearchPhotosUseCase.execute(useCase, viewModelScope, requestModel, onSuccess, onError)
    }
}