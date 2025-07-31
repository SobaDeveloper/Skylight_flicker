package com.example.skylightflickr.di

import com.example.skylightflickr.feature.photosearch.PhotoSearchViewModel
import com.example.skylightflickr.feature.photodetails.PhotoDetailViewModel
import com.example.skylightflickr.util.SearchPrefsManager
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule: Module
    get() = module {
        includes(dataStoreModule, viewModelModules)
    }

val dataStoreModule = module {
    singleOf(::SearchPrefsManager)
}

val viewModelModules = module {
    viewModelOf(::PhotoSearchViewModel)
    viewModelOf(::PhotoDetailViewModel)
}