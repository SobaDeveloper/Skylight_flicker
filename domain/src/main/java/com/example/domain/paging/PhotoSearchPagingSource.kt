package com.example.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.domain.models.Photo
import com.example.domain.repo.PhotosRepository

class PhotoSearchPagingSource(
    private val repository: PhotosRepository,
    private val tags: String
) : PagingSource<Int, Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {
            val currentPage = params.key ?: 1

            val result = repository.searchPhotos(
                tags = tags,
                page = currentPage,
                perPage = PER_PAGE
            )

            if (result.isSuccess) {
                val flickrPhotos = result.getOrThrow()
                val photos = flickrPhotos.photos.photoList.distinctBy { it.id }

                LoadResult.Page(
                    data = photos,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (photos.isEmpty()) null else currentPage + 1
                )
            } else {
                LoadResult.Error(result.exceptionOrNull() ?: Exception("Unknown error"))
            }
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val PER_PAGE = 20
    }
}