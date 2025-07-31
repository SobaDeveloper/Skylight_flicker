package com.example.domain.usecase

import com.example.domain.models.PhotoDetails
import com.example.domain.repo.PhotosRepository

class GetPhotoDetails(
    private val repository: PhotosRepository
) {
    suspend operator fun invoke(
        photoId: String,
        secret: String? = null
    ): Result<PhotoDetails> {
        return repository.getPhotoDetails(photoId, secret)
    }
}