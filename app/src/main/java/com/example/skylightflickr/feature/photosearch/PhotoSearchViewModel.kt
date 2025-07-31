package com.example.skylightflickr.feature.photosearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Photo
import com.example.domain.usecase.SearchPhotosByTag
import com.example.skylightflickr.feature.common.ViewState
import com.example.skylightflickr.util.SearchPrefsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PhotoSearchViewModel(
    private val searchPhotosByTag: SearchPhotosByTag,
    private val searchPrefsManager: SearchPrefsManager
) : ViewModel() {

    private val _viewState = MutableStateFlow<ViewState<List<Photo>>>(ViewState.Idle)
    val viewState: StateFlow<ViewState<List<Photo>>> = _viewState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        viewModelScope.launch {
            searchPrefsManager.searchQueryFlow.collect {
                _searchQuery.value = it
                if (it.isNotBlank()) {
                    searchPhotos(it)
                }
            }
        }
    }

    fun searchPhotos(tags: String) {
        viewModelScope.launch {
            searchPrefsManager.saveSearchQuery(tags)
            _viewState.value = ViewState.Loading

            searchPhotosByTag(tags = tags)
                .onSuccess { response ->
                    _viewState.value = ViewState.Success(response)
                }
                .onFailure { exception ->
                    _viewState.value = ViewState.Error(exception.message ?: "Unknown error")
                }
        }
    }

    fun updateSearchQuery(newQuery: String) {
        _searchQuery.value = newQuery
    }
}