package com.example.data.service.flickr.dto

import com.example.core.model.PhotoSize
import com.example.core.util.PhotoUtils
import com.squareup.moshi.Json

data class PhotosResponseDto(
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
) {

    fun getPhotoUrl(): String = PhotoUtils.buildImageUrl(
        farm = farm,
        server = server,
        id = id,
        secret = secret,
        size = PhotoSize.MEDIUM
    )
}