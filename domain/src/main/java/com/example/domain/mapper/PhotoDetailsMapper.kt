package com.example.domain.mapper

import com.example.core.util.PhotoUtils
import com.example.data.service.flickr.dto.FlickrPhotoInfoResponseDto
import com.example.data.service.flickr.dto.PhotoInfoDto
import com.example.domain.models.PhotoDetails

fun FlickrPhotoInfoResponseDto.toModel(): PhotoDetails = photo.toModel()

fun PhotoInfoDto.toModel(): PhotoDetails =
    PhotoDetails(
        title = title.content,
        description = description.content,
        dateTaken = dates.taken,
        datePosted = dates.posted,
        imageUrl = PhotoUtils.buildImageUrl(farm = farm, server = server, id = id, secret = secret),
        backLink = urls?.url?.firstOrNull()?.content ?: ""
    )