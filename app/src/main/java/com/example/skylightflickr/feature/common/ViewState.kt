package com.example.skylightflickr.feature.common

sealed class ViewState<out T> {
    data object Idle : ViewState<Nothing>()
    data object Loading : ViewState<Nothing>()
    data class Success<T>(val data: T) : ViewState<T>()
    data class Error(val message: String) : ViewState<Nothing>()
}