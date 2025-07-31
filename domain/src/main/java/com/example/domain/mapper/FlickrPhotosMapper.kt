package com.example.domain.mapper

import com.example.data.service.flickr.dto.PhotosResponseDto
import com.example.data.service.flickr.dto.PhotoDto
import com.example.data.service.flickr.dto.PhotosDto
import com.example.domain.models.FlickrPhotos
import com.example.domain.models.Photo
import com.example.domain.models.Photos

fun PhotosResponseDto.toModel(): FlickrPhotos =
    FlickrPhotos(
        photos = photosDto.toModel()
    )

fun PhotosDto.toModel(): Photos =
    Photos(
        page = page,
        pages = pages,
        perPage = perPage,
        total = total,
        photoList = photoDto.map { it.toModel() }
    )

fun PhotoDto.toModel(): Photo =
    Photo(
        id = id,
        title = title,
        imageUrl = getPhotoUrl(),
        secret = secret
    )