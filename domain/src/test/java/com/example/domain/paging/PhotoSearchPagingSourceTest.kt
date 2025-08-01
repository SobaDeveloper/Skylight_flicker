package com.example.domain.paging

import androidx.paging.PagingSource
import com.example.domain.models.Photo
import com.example.domain.models.Photos
import com.example.domain.models.PhotosResponse
import com.example.domain.repo.PhotosRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class PhotoSearchPagingSourceTest {

    private lateinit var repository: PhotosRepository
    private lateinit var pagingSource: PhotoSearchPagingSource

    @Before
    fun setup() {
        repository = mockk()
        pagingSource = PhotoSearchPagingSource(repository, tags = "skylight")
    }

    @Test
    fun testLoadResultSuccess() = runTest {
        val photoList = listOf(
            Photo(id = "1", title = "title1", imageUrl = "url1", secret = "secret1"),
            Photo(id = "2", title = "title2", imageUrl = "url2", secret = "secret2")
        )
        val response = PhotosResponse(
            photos = Photos(
                page = 1,
                pages = 10,
                perPage = 20,
                total = 200,
                photoList = photoList
            )
        )
        coEvery { repository.searchPhotos("skylight", "any", 20) } returns Result.success(response)

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        val expected = PagingSource.LoadResult.Page(
            data = photoList,
            prevKey = null,
            nextKey = 2
        )

        assertEquals(expected, result)
    }

    @Test
    fun testLoadResultFailure() = runTest {
        val exception = Exception("Network error")
        coEvery { repository.searchPhotos("skylight", "any", 20) } returns Result.failure(exception)

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        assertTrue(result is PagingSource.LoadResult.Error)
        assertEquals(exception, (result as PagingSource.LoadResult.Error).throwable)
    }
}