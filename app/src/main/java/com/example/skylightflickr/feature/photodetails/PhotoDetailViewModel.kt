package com.example.skylightflickr.feature.photodetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.PhotoDetails
import com.example.domain.usecase.GetPhotoDetails
import com.example.skylightflickr.feature.common.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PhotoDetailViewModel(
    private val getPhotoDetails: GetPhotoDetails
) : ViewModel() {

    private val _viewState = MutableStateFlow<ViewState<PhotoDetails>>(ViewState.Loading)
    val viewState: StateFlow<ViewState<PhotoDetails>> = _viewState.asStateFlow()

    fun getDetails(photoId: String, secret: String? = null) {
        viewModelScope.launch {
            _viewState.value = ViewState.Loading

            getPhotoDetails(photoId, secret)
                .onSuccess { response ->
                    _viewState.value = ViewState.Success(response)
                }
                .onFailure { exception ->
                    _viewState.value = ViewState.Error(exception.message ?: "Unknown error")
                }
        }
    }
}