package com.hotelka.moscowrealtime.data.dto.api


import kotlinx.serialization.Serializable
@Serializable
data class KudaGoApiResults<T>(
    val count: Int = 0,
    val next: String? = null,
    val previous: String? = null,
    val results: List<T> = listOf()
)

@Serializable
data class EventDto(
    val id: Int = 0,
    val title: String = "",
    val slug: String = ""
)

@Serializable
data class TrendingDto(
    val id: Int = 0,
    val publication_date: Long = 0L,
    val title: String = "",
    val slug: String = "",
    val site_url: String = ""
)

@Serializable
data class DetailedTrendingDto(
    val id: Long = 0L,
    val ctype: String = "",
    val publication_date: Long = 0L,
    val title: String = "",
    val items: List<TrendingItemDto> = emptyList(),
    val favorites_count: Int = 0,
    val comments_count: Int = 0,
    val images: List<ImageDto> = emptyList(),
    val description: String = "",
    val body_text: String = "",
    val site_url: String = "",
    val item_url: String = "",
    val disable_comments: Boolean = false
)

@Serializable
data class TrendingItemDto(
    val id: Long = 0L,
    val slug: String? = null,
    val title: String = "",
    val favorites_count: Int? = null,
    val comments_count: Int? = null,
    val description: String = "",
    val body_text: String? = null,
    val item_url: String = "",
    val disable_comments: Boolean? = null,
    val ctype: String? = null,
    val address: String? = null,
    val location: String? = null,
    val coords: CoordinatesDto? = null,
    val phone: String? = null,
    val is_closed: Boolean? = null,
    val is_stub: Boolean? = null,
    val age_restriction: String? = null
)

@Serializable
data class CoordinatesDto(
    val lat: Double? = null,
    val lon: Double? = null
)

@Serializable
data class ImageDto(
    val image: String = "",
    val source: SourceDto = SourceDto()
)

@Serializable
data class SourceDto(
    val name: String = "",
    val link: String = ""
)

@Serializable
data class DetailedEventDto(
    val id: Int = 0,
    val publication_date: Long = 0L,
    val dates: List<EventDateDto> = emptyList(),
    val title: String = "",
    val slug: String = "",
    val place: PlaceDto? = null,
    val description: String = "",
    val body_text: String = "",
    val location: LocationSlugDto = LocationSlugDto(),
    val categories: List<String> = emptyList(),
    val tagline: String = "",
    val age_restriction: String = "",
    val price: String = "",
    val is_free: Boolean = false,
    val images: List<EventImageDto> = emptyList(),
    val favorites_count: Int = 0,
    val comments_count: Int = 0,
    val site_url: String = "",
    val short_title: String = "",
    val tags: List<String> = emptyList(),
    val disable_comments: Boolean = false,
    val detailedPlace: PlaceDetailedDto? = null
)

@Serializable
data class EventDateDto(
    val start: Long = 0L,
    val end: Long = 0L
)

@Serializable
data class PlaceDto(
    val id: Int = 0
)

@Serializable
data class LocationSlugDto(
    val slug: String = ""
)

@Serializable
data class EventImageDto(
    val image: String = "",
    val source: ImageSourceDto = ImageSourceDto()
)

@Serializable
data class ImageSourceDto(
    val name: String = "",
    val link: String = ""
)

@Serializable
data class PlaceDetailedDto(
    val id: Int = 0,
    val title: String = "",
    val slug: String = "",
    val address: String = "",
    val phone: String = "",
    val site_url: String = "",
    val subway: String = "",
    val is_closed: Boolean = false,
    val location: String = "",
    val has_parking_lot: Boolean = false,
    val coords: CoordinatesDto? = null
)