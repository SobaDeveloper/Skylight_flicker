package com.example.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.domain.models.Photo
import com.example.domain.paging.PhotoSearchPagingSource
import com.example.domain.repo.PhotosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.paging.filter

class SearchPagedPhotosByTag(
    private val repository: PhotosRepository
) {

    operator fun invoke(tags: String): Flow<PagingData<Photo>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            PhotoSearchPagingSource(repository, tags)
        }
    ).flow.map { pagingData ->
        val seen = mutableSetOf<String>()
        pagingData.filter { seen.add(it.id) }
    }
}