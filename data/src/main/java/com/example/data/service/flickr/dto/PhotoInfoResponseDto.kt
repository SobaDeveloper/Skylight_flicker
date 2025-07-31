package com.example.data.service.flickr.dto

import com.squareup.moshi.Json

data class PhotoInfoResponseDto(
    val photo: PhotoInfoDto,
    val stat: String
)

data class PhotoInfoDto(
    val id: String,
    val secret: String,
    val server: String,
    val farm: Int,
    @Json(name = "dateuploaded") val dateUploaded: Long,
    val owner: OwnerDto?,
    val title: ContentDto,
    val description: ContentDto,
    val visibility: VisibilityDto,
    val dates: DatesDto,
    val views: String,
    val comments: ContentDto?,
    val tags: TagsDto,
    val urls: UrlsDto?
)

data class OwnerDto(
    val nsid: String,
    val username: String,
    val realname: String,
    val location: String?,
    val iconserver: String,
    val iconfarm: Int,
    @Json(name = "path_alias") val pathAlias: String?
)

data class ContentDto(
    @Json(name = "_content") val content: String
)

data class VisibilityDto(
    @Json(name = "ispublic") val isPublic: Int,
    @Json(name = "isfriend") val isFriend: Int,
    @Json(name = "isfamily") val isFamily: Int
)

data class DatesDto(
    val posted: String,
    val taken: String,
    val takengranularity: String,
    val takenunknown: String,
    @Json(name = "lastupdate") val lastUpdate: String
)

data class TagsDto(
    @Json(name = "tag") val tags: List<TagDto> = emptyList()
)

data class TagDto(
    val id: String,
    val author: String,
    val authorname: String,
    val raw: String,
    @Json(name = "_content") val content: String,
)

data class UrlsDto(
    val url: List<UrlDto> = emptyList()
)

data class UrlDto(
    val type: String,
    @Json(name = "_content") val content: String
)