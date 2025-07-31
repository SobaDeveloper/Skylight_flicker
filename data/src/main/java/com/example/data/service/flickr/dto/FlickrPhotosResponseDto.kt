package com.example.data.service.flickr.dto

import com.example.core.model.PhotoSize
import com.example.core.util.PhotoUtils
import com.squareup.moshi.Json

data class FlickrPhotosResponseDto(
    @Json(name = "photos") val photosDto: PhotosDto,
    val stat: String
)

data class PhotosDto(
    val page: Int,
    val pages: Int,
    @Json(name = "perpage") val perPage: Int,
    val total: Int,
    @Json(name = "photo") val photoDto: List<PhotoDto>
)

data class PhotoDto(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val title: String,
    @Json(name = "ispublic") val isPublic: Int,
    @Json(name = "isfriend") val isFriend: Int,
    @Json(name = "isfamily") val isFamily: Int
) {

    fun getPhotoUrl(): String = PhotoUtils.buildImageUrl(
        farm = farm,
        server = server,
        id = id,
        secret = secret,
        size = PhotoSize.MEDIUM
    )
}