package com.example.domain.usecase

import com.example.domain.models.PhotoDetails
import com.example.domain.repo.PhotosRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetPhotoDetailsTest {
    private lateinit var repository: PhotosRepository
    private lateinit var getPhotoDetails: GetPhotoDetails

    @Before
    fun setUp() {
        repository = mockk()
        getPhotoDetails = GetPhotoDetails(repository)
    }

    @Test
    fun testGetPhotoDetailsSuccess() = runTest {
        val expectedDetails = mockk<PhotoDetails>()
        coEvery { repository.getPhotoDetails("123", "abcd") } returns Result.success(expectedDetails)

        val result = getPhotoDetails("123", "abcd")

        assertTrue(result.isSuccess)
        assertEquals(expectedDetails, result.getOrNull())
    }

    @Test
    fun testGetPhotoDetailsFailure() = runTest {
        val exception = Exception("Not found")
        coEvery { repository.getPhotoDetails("123", null) } returns Result.failure(exception)

        val result = getPhotoDetails("123")

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}