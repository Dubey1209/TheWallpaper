package com.example.appwallpaper.data

import com.google.gson.annotations.SerializedName

data class PexelsResponse(
    @SerializedName("photos") val photos: List<PexelPhoto>
)

data class PexelPhoto(
    @SerializedName("id") val id: Int,
    @SerializedName("src") val src: ImageSrc
)

data class ImageSrc(
    @SerializedName("original") val original: String  // API se jo image URL aayega
)
