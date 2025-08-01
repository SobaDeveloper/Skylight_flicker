package com.example.domain.usecase

import com.example.domain.models.PhotosResponse
import com.example.domain.models.Photo
import com.example.domain.models.Photos
import com.example.domain.repo.PhotosRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SearchPhotosByTagTest {
    private lateinit var repository: PhotosRepository
    private lateinit var searchPhotosByTag: SearchPhotosByTag

    @Before
    fun setUp() {
        repository = mockk()
        searchPhotosByTag = SearchPhotosByTag(repository)
    }

    @Test
    fun testSearchPhotosSuccess() = runTest {
        val mockPhotos = listOf(mockk<Photo>())
        val photosResponse = PhotosResponse(
            photos = Photos(
                page = 1,
                pages = 1,
                perPage = 50,
                total = 1,
                photoList = mockPhotos
            )
        )

        coEvery { repository.searchPhotos("cats", "any", 50, 1) } returns Result.success(photosResponse)

        val result = searchPhotosByTag("cats")

        assertTrue(result.isSuccess)
        assertEquals(mockPhotos, result.getOrNull())
    }

    @Test
    fun testSearchPhotosFailure() = runTest {
        val exception = Exception("Network error")
        coEvery { repository.searchPhotos("dogs", "any", 50, 1) } returns Result.failure(exception)

        val result = searchPhotosByTag("dogs")

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}