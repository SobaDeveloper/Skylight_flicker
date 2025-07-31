package com.example.data.service.flickr.dto

import com.squareup.moshi.Json

data class FlickrPhotoInfoResponseDto(
    val photo: PhotoInfoDto,
    val stat: String
)

data class PhotoInfoDto(
    val id: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val dateuploaded: String,
    val isfavorite: Int,
    val license: String,
    val safety_level: String,
    val rotation: Int,
    val owner: OwnerDto,
    val title: ContentDto,
    val description: ContentDto,
    val visibility: VisibilityDto,
    val dates: DatesDto,
    val views: String,
    val comments: ContentDto,
    val tags: TagsDto,
    val location: LocationDto?,
    val geoperms: GeoPermsDto?,
    val urls: UrlsDto?
)

data class OwnerDto(
    val nsid: String,
    val username: String,
    val realname: String,
    val location: String,
    val iconserver: String,
    val iconfarm: Int,
    val path_alias: String?
)

data class ContentDto(
    @Json(name = "_content") val content: String
)

data class VisibilityDto(
    val ispublic: Int,
    val isfriend: Int,
    val isfamily: Int
)

data class DatesDto(
    val posted: String,
    val taken: String,
    val takengranularity: String,
    val takenunknown: String,
    val lastupdate: String
)

data class TagsDto(
    @Json(name = "tag") val tags: List<TagDto>
)

data class TagDto(
    val id: String,
    val author: String,
    val authorname: String,
    val raw: String,
    @Json(name = "_content") val content: String,
    @Json(name = "machine_tag") val machineTag: Int
)

data class LocationDto(
    val latitude: Double,
    val longitude: Double,
    val accuracy: String,
    val context: String,
    val locality: LocalityDto?,
    val county: CountyDto?,
    val region: RegionDto?,
    val country: CountryDto?
)

data class LocalityDto(
    @Json(name = "_content") val content: String,
    @Json(name = "place_id") val placeId: String,
    val woeid: String
)

data class CountyDto(
    @Json(name = "_content") val content: String,
    @Json(name = "place_id") val placeId: String,
    val woeid: String
)

data class RegionDto(
    @Json(name = "_content") val content: String,
    @Json(name = "place_id") val placeId: String,
    val woeid: String
)

data class CountryDto(
    @Json(name = "_content") val content: String,
    @Json(name = "place_id") val placeId: String,
    val woeid: String
)

data class GeoPermsDto(
    val ispublic: Int,
    val iscontact: Int,
    val isfriend: Int,
    val isfamily: Int
)

data class UrlsDto(
    val url: List<UrlDto>
)

data class UrlDto(
    val type: String,
    @Json(name = "_content") val content: String
)