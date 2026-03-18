package com.hotelka.moscowrealtime.domain.model.api


data class Event(
    val id: Int,
    val title: String,
    val slug: String
)

data class Trending(
    val id: Int,
    val publicationDate: Long,
    val title: String,
    val slug: String,
    val siteUrl: String
)

data class DetailedTrending(
    val id: Long,
    val ctype: String,
    val publicationDate: Long,
    val title: String,
    val items: List<TrendingItem>,
    val favoritesCount: Int,
    val commentsCount: Int,
    val images: List<Image>,
    val description: String,
    val bodyText: String,
    val siteUrl: String,
    val itemUrl: String,
    val disableComments: Boolean
)

data class TrendingItem(
    val id: Long,
    val slug: String?,
    val title: String,
    val favoritesCount: Int?,
    val commentsCount: Int?,
    val description: String,
    val bodyText: String?,
    val itemUrl: String,
    val disableComments: Boolean?,
    val ctype: String?,
    val address: String?,
    val location: String?,
    val coords: Coordinates?,
    val phone: String?,
    val isClosed: Boolean?,
    val isStub: Boolean?,
    val ageRestriction: String?
)

data class Coordinates(
    val latitude: Double?,
    val longitude: Double?
)

data class Image(
    val image: String,
    val source: Source
)

data class Source(
    val name: String,
    val link: String
)

data class DetailedEvent(
    val id: Int,
    val publicationDate: Long,
    val dates: List<EventDate>,
    val title: String,
    val slug: String,
    val place: Place?,
    val description: String,
    val bodyText: String,
    val location: LocationSlug,
    val categories: List<String>,
    val tagline: String,
    val ageRestriction: String,
    val price: String,
    val isFree: Boolean,
    val images: List<EventImage>,
    val favoritesCount: Int,
    val commentsCount: Int,
    val siteUrl: String,
    val shortTitle: String,
    val tags: List<String>,
    val disableComments: Boolean,
    val detailedPlace: PlaceDetailed?
)

data class EventDate(
    val start: Long,
    val end: Long
)

data class Place(
    val id: Int
)

data class LocationSlug(
    val slug: String
)

data class EventImage(
    val image: String,
    val source: ImageSource
)

data class ImageSource(
    val name: String,
    val link: String
)

data class PlaceDetailed(
    val id: Int,
    val title: String,
    val slug: String,
    val address: String,
    val phone: String,
    val siteUrl: String,
    val subway: String,
    val isClosed: Boolean,
    val location: String,
    val hasParkingLot: Boolean,
    val coords: Coordinates?
)