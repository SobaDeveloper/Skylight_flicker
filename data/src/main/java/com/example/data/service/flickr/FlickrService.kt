package com.example.data.service.flickr

import com.example.data.service.flickr.dto.FlickrPhotoInfoResponseDto
import com.example.data.service.flickr.dto.FlickrPhotosResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrService {
    /**
     * Search photos by tag.
     * Docs: https://www.flickr.com/services/api/flickr.photos.search.html
     */
    @GET("services/rest/")
    suspend fun searchPhotosByTag(
        @Query("method") method: String = SEARCH_METHOD,
        @Query("tags") tags: String,
        @Query("tag_mode") tagMode: String = "any",
        @Query("per_page") perPage: Int = 20,
        @Query("page") page: Int = 1,
        @Query("safe_search") safeSearch: Int = 1,
        @Query("format") format: String = FORMAT_JSON,
        @Query("nojsoncallback") noJsonCallback: Int = NO_JSON_CALLBACK
    ): Response<FlickrPhotosResponseDto>

    /**
     * Get detailed photo info.
     * Docs: https://www.flickr.com/services/api/flickr.photos.getInfo.html
     */
    @GET("services/rest/")
    suspend fun getPhotoInfo(
        @Query("method") method: String = INFO_METHOD,
        @Query("photo_id") photoId: String,
        @Query("secret") secret: String? = null,
        @Query("format") format: String = FORMAT_JSON,
        @Query("nojsoncallback") noJsonCallback: Int = NO_JSON_CALLBACK
    ): Response<FlickrPhotoInfoResponseDto>

    companion object {
        private const val SEARCH_METHOD = "flickr.photos.search"
        private const val INFO_METHOD = "flickr.photos.getInfo"
        private const val FORMAT_JSON = "json"
        private const val NO_JSON_CALLBACK = 1
    }
}