package com.example.domain.repo

import com.example.data.service.flickr.FlickrService
import com.example.domain.mapper.toModel
import com.example.domain.models.PhotosResponse
import com.example.domain.models.PhotoDetails

class PhotosRepository(private val service: FlickrService) {

    suspend fun searchPhotos(
        tags: String,
        tagMode: String = "any",
        perPage: Int = 20,
        page: Int = 1
    ): Result<PhotosResponse> = runCatching {
        val response = service.searchPhotosByTag(
            tags = tags,
            tagMode = tagMode,
            perPage = perPage,
            page = page
        )
        val body = response.body()

        if (response.isSuccessful && body != null) {
            body.toModel()
        } else {
            throw Exception("API call failed: ${response.code()}")
        }
    }

    suspend fun getPhotoDetails(
        photoId: String,
        secret: String? = null
    ): Result<PhotoDetails> = runCatching {
        val response = service.getPhotoInfo(
            photoId = photoId,
            secret = secret
        )
        val body = response.body()

        if (response.isSuccessful && body != null) {
            body.toModel()
        } else {
            throw Exception("API call failed: ${response.code()}")
        }
    }
}