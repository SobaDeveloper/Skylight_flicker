package com.example.data.di

import com.example.data.BuildConfig
import com.example.data.network.ApiKeyInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    single(named(Qualifiers.FLICKR_BASE_URL)) { provideFlickrBaseUrl() }
    single(named(Qualifiers.FLICKR_API_KEY)) { provideFlickrApiKey() }

    single { provideApiKeyInterceptor(apiKey = get(named(Qualifiers.FLICKR_API_KEY))) }
    singleOf(::provideMoshi)
    singleOf(::provideOkHttpClient)
    single {
        provideRetrofit(
            client = get(),
            moshi = get(),
            baseUrl = get(named(Qualifiers.FLICKR_BASE_URL))
        )
    }
}

private fun provideFlickrBaseUrl(): String = BuildConfig.FLICKR_BASE_URL

private fun provideFlickrApiKey(): String = BuildConfig.FLICKR_API_KEY

fun provideApiKeyInterceptor(apiKey: String): ApiKeyInterceptor = ApiKeyInterceptor(apiKey)

private fun provideMoshi(): Moshi =
    Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private fun provideOkHttpClient(
    apiKeyInterceptor: ApiKeyInterceptor
): OkHttpClient {
    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    return OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(apiKeyInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()
}

private fun provideRetrofit(
    client: OkHttpClient,
    moshi: Moshi,
    baseUrl: String
): Retrofit =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(client)
        .build()

fun <S> createService(retrofit: Retrofit, serviceClass: Class<S>): S = retrofit.create(serviceClass)

object Qualifiers {
    const val FLICKR_API_KEY = "FlickrApiKey"
    const val FLICKR_BASE_URL = "FlickrBaseUrl"
}