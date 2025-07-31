package com.example.skylightflickr.di

import com.example.skylightflickr.feature.photosearch.PhotoSearchViewModel
import com.example.skylightflickr.feature.photodetails.PhotoDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule: Module
    get() = module {
        includes(viewModelModules)
    }

val viewModelModules = module {
    viewModelOf(::PhotoSearchViewModel)
    viewModelOf(::PhotoDetailViewModel)
}