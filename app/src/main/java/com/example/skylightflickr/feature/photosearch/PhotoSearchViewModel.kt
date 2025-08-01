package com.example.skylightflickr.feature.photosearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.models.Photo
import com.example.domain.usecase.SearchPagedPhotosByTag
import com.example.skylightflickr.util.SearchPrefsManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class PhotoSearchViewModel(
    private val searchPrefsManager: SearchPrefsManager,
    private val searchPagedPhotosByTag: SearchPagedPhotosByTag
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchAction = MutableStateFlow("")

    val photosFlow: Flow<PagingData<Photo>> = _searchAction
        .filter { it.isNotBlank() }
        .distinctUntilChanged()
        .flatMapLatest { query ->
            searchPagedPhotosByTag(query)
        }
        .cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            searchPrefsManager.searchQueryFlow.collect {
                _searchQuery.value = it
                if (it.isNotBlank()) {
                    _searchAction.value = it
                }
            }
        }
    }

    fun updateSearchQuery(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun startSearch() {
        val query = _searchQuery.value.trim()
        if (query.isNotBlank()) {
            viewModelScope.launch {
                searchPrefsManager.saveSearchQuery(query)
                _searchAction.value = query
            }
        }
    }
}