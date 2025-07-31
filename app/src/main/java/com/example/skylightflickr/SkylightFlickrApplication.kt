package com.example.skylightflickr

import android.app.Application
import com.example.data.di.dataModule
import com.example.data.di.networkModule
import com.example.domain.di.domainModule
import com.example.skylightflickr.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SkylightFlickrApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SkylightFlickrApplication)
            modules(
                listOf(
                    appModule,
                    networkModule,
                    dataModule,
                    domainModule
                )
            )
        }
    }
}