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

    @SerializedName("width")
    @Expose
    var width: Int? = null

    @SerializedName("height")
    @Expose
    var height: Int? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

    @SerializedName("photographer")
    @Expose
    var photographer: String? = null

    @SerializedName("photographer_url")
    @Expose
    var photographerUrl: String? = null

    @SerializedName("photographer_id")
    @Expose
    var photographerId: Int? = null

    @SerializedName("avg_color")
    @Expose
    var avgColor: String? = null

    @SerializedName("src")
    @Expose
    var src: Src? = null

    @SerializedName("liked")
    @Expose
    var liked: Boolean? = null

    @SerializedName("alt")
    @Expose
    var alt: String? = null
}

class Src {
    @SerializedName("original")
    @Expose
    var original: String? = null

    @SerializedName("large2x")
    @Expose
    var large2x: String? = null

    @SerializedName("large")
    @Expose
    var large: String? = null

    @SerializedName("medium")
    @Expose
    var medium: String? = null

    @SerializedName("small")
    @Expose
    var small: String? = null

    @SerializedName("portrait")
    @Expose
    var portrait: String? = null

    @SerializedName("landscape")
    @Expose
    var landscape: String? = null

    @SerializedName("tiny")
    @Expose
    var tiny: String? = null
}