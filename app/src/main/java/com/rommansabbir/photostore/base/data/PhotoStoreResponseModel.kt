package com.rommansabbir.photostore.base.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PhotoStoreResponseModel {
    @SerializedName("page")
    @Expose
    var page: Int? = null
        get() = if (field == null) -1 else field

    @SerializedName("per_page")
    @Expose
    var perPage: Int? = null
        get() = if (field == null) -1 else field

    @SerializedName("total_results")
    @Expose
    var totalResult: Int? = null
        get() = if (field == null) -1 else field

    @SerializedName("next_page")
    @Expose
    var nextPage: String? = null

    @SerializedName("photos")
    @Expose
    var photos: MutableList<PhotoModel> = mutableListOf()
}

class PhotoModel {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("src")
    @Expose
    var src: Src? = null

}

class Src {
    @SerializedName("medium")
    @Expose
    var medium: String? = null
}