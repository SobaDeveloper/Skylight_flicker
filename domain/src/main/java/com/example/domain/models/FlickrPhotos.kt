package com.example.domain.models

data class FlickrPhotos(
    val photos: Photos
)

data class Photos(
    val page: Int,
    val pages: Int,
    val perPage: Int,
    val total: Int,
    val photoList: List<Photo>
)

data class Photo(
    val id: String,
    val title: String,
    val imageUrl: String,
    val secret: String
)