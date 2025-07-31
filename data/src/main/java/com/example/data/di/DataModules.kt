package com.example.data.di

import com.example.data.service.flickr.FlickrService
import org.koin.core.module.Module
import org.koin.dsl.module

val dataModule: Module
    get() = module {
        includes(serviceModule)
    }

val serviceModule = module {
    single { createService(get(), FlickrService::class.java) }
}