package vn.com.bravesoft.androidapp.model

import com.google.gson.annotations.SerializedName

data class InstagramDTO (
    @SerializedName("id")
    val id: String,
    @SerializedName("caption")
    val caption: String,
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("media_url")
    val mediaUrl: String,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String?,
    @SerializedName("timestamp")
    val timestamp: String,
    @SerializedName("permalink")
    val permalink: String,
)