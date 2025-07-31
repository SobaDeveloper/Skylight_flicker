package com.example.domain.usecase

import com.example.domain.models.Photo
import com.example.domain.repo.PhotosRepository

class SearchPhotosByTag(
    private val repository: PhotosRepository
) {
    suspend operator fun invoke(
        tags: String,
        tagMode: String = "any",
        perPage: Int = 50,
        page: Int = 1
    ): Result<List<Photo>> =
        repository.searchPhotos(tags, tagMode, perPage, page)
            .map { response -> response.photos.photoList }
}