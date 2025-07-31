package com.example.domain.repo

import com.example.data.service.flickr.FlickrService
import com.example.data.service.flickr.dto.FlickrPhotosResponseDto
import com.example.data.service.flickr.dto.PhotoDto
import com.example.data.service.flickr.dto.PhotosDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class PhotosRepositoryTest {
    private lateinit var service: FlickrService
    private lateinit var repository: PhotosRepository

    @Before
    fun setup() {
        service = mockk()
        repository = PhotosRepository(service)
    }

    @Test
    fun testSearchPhotosByTagReturnsSuccess() = runTest {
        val dummyResponseDto = createDummyFlickrPhotosResponseDto()

        coEvery {
            service.searchPhotosByTag(
                tags = "kittens",
                tagMode = "any",
                perPage = 20,
                page = 1
            )
        } returns Response.success(dummyResponseDto)

        val result = repository.searchPhotos("kittens")

        assertTrue(result.isSuccess)
        val model = result.getOrNull()
        assertEquals("123", model?.photos?.photoList?.firstOrNull()?.id)
    }

    @Test
    fun testGetPhotoDetailsFailure() = runTest {
        coEvery {
            service.getPhotoInfo(
                photoId = "123",
                secret = null
            )
        } returns Response.error(404, "".toResponseBody(null))

        val result = repository.getPhotoDetails("123")

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("API call failed") == true)
    }

    private fun createDummyFlickrPhotosResponseDto() = FlickrPhotosResponseDto(
        photosDto = createDummyPhotosDto(),
        stat = "ok"
    )

    private fun createDummyPhotosDto() = PhotosDto(
        page = 1,
        pages = 1,
        perPage = 1,
        total = 1,
        photoDto = listOf(createDummyPhotoDto())
    )

    private fun createDummyPhotoDto() = PhotoDto(
        id = "123",
        owner = "owner",
        secret = "abc",
        server = "1",
        farm = 1,
        title = "title",
        isFamily = 1,
        isFriend = 1,
        isPublic = 1
    )
}