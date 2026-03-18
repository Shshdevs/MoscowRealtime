package com.hotelka.moscowrealtime.data.mapper.api

import com.hotelka.moscowrealtime.data.dto.api.CoordinatesDto
import com.hotelka.moscowrealtime.data.dto.api.DetailedEventDto
import com.hotelka.moscowrealtime.data.dto.api.DetailedTrendingDto
import com.hotelka.moscowrealtime.data.dto.api.EventDateDto
import com.hotelka.moscowrealtime.data.dto.api.EventDto
import com.hotelka.moscowrealtime.data.dto.api.EventImageDto
import com.hotelka.moscowrealtime.data.dto.api.ImageDto
import com.hotelka.moscowrealtime.data.dto.api.ImageSourceDto
import com.hotelka.moscowrealtime.data.dto.api.LocationSlugDto
import com.hotelka.moscowrealtime.data.dto.api.PlaceDetailedDto
import com.hotelka.moscowrealtime.data.dto.api.PlaceDto
import com.hotelka.moscowrealtime.data.dto.api.SourceDto
import com.hotelka.moscowrealtime.data.dto.api.TrendingDto
import com.hotelka.moscowrealtime.data.dto.api.TrendingItemDto
import com.hotelka.moscowrealtime.domain.model.api.Coordinates
import com.hotelka.moscowrealtime.domain.model.api.DetailedEvent
import com.hotelka.moscowrealtime.domain.model.api.DetailedTrending
import com.hotelka.moscowrealtime.domain.model.api.Event
import com.hotelka.moscowrealtime.domain.model.api.EventDate
import com.hotelka.moscowrealtime.domain.model.api.EventImage
import com.hotelka.moscowrealtime.domain.model.api.Image
import com.hotelka.moscowrealtime.domain.model.api.ImageSource
import com.hotelka.moscowrealtime.domain.model.api.LocationSlug
import com.hotelka.moscowrealtime.domain.model.api.Place
import com.hotelka.moscowrealtime.domain.model.api.PlaceDetailed
import com.hotelka.moscowrealtime.domain.model.api.Source
import com.hotelka.moscowrealtime.domain.model.api.Trending
import com.hotelka.moscowrealtime.domain.model.api.TrendingItem


fun EventDto.toDomain(): Event = Event(
    id = id,
    title = title,
    slug = slug
)

fun Event.toDto(): EventDto = EventDto(
    id = id,
    title = title,
    slug = slug
)

fun TrendingDto.toDomain(): Trending = Trending(
    id = id,
    publicationDate = publication_date,
    title = title,
    slug = slug,
    siteUrl = site_url
)

fun Trending.toDto(): TrendingDto = TrendingDto(
    id = id,
    publication_date = publicationDate,
    title = title,
    slug = slug,
    site_url = siteUrl
)

fun CoordinatesDto.toDomain(): Coordinates = Coordinates(
    latitude = lat,
    longitude = lon
)

fun Coordinates.toDto(): CoordinatesDto = CoordinatesDto(
    lat = latitude,
    lon = longitude
)

fun SourceDto.toDomain(): Source = Source(
    name = name,
    link = link
)

fun Source.toDto(): SourceDto = SourceDto(
    name = name,
    link = link
)

fun ImageDto.toDomain(): Image = Image(
    image = image,
    source = source.toDomain()
)

fun Image.toDto(): ImageDto = ImageDto(
    image = image,
    source = source.toDto()
)

fun TrendingItemDto.toDomain(): TrendingItem = TrendingItem(
    id = id,
    slug = slug,
    title = title,
    favoritesCount = favorites_count,
    commentsCount = comments_count,
    description = description,
    bodyText = body_text,
    itemUrl = item_url,
    disableComments = disable_comments,
    ctype = ctype,
    address = address,
    location = location,
    coords = coords?.toDomain(),
    phone = phone,
    isClosed = is_closed,
    isStub = is_stub,
    ageRestriction = age_restriction
)

fun TrendingItem.toDto(): TrendingItemDto = TrendingItemDto(
    id = id,
    slug = slug,
    title = title,
    favorites_count = favoritesCount,
    comments_count = commentsCount,
    description = description,
    body_text = bodyText,
    item_url = itemUrl,
    disable_comments = disableComments,
    ctype = ctype,
    address = address,
    location = location,
    coords = coords?.toDto(),
    phone = phone,
    is_closed = isClosed,
    is_stub = isStub,
    age_restriction = ageRestriction
)

fun DetailedTrendingDto.toDomain(): DetailedTrending = DetailedTrending(
    id = id,
    ctype = ctype,
    publicationDate = publication_date,
    title = title,
    items = items.map { it.toDomain() },
    favoritesCount = favorites_count,
    commentsCount = comments_count,
    images = images.map { it.toDomain() },
    description = description,
    bodyText = body_text,
    siteUrl = site_url,
    itemUrl = item_url,
    disableComments = disable_comments
)

fun DetailedTrending.toDto(): DetailedTrendingDto = DetailedTrendingDto(
    id = id,
    ctype = ctype,
    publication_date = publicationDate,
    title = title,
    items = items.map { it.toDto() },
    favorites_count = favoritesCount,
    comments_count = commentsCount,
    images = images.map { it.toDto() },
    description = description,
    body_text = bodyText,
    site_url = siteUrl,
    item_url = itemUrl,
    disable_comments = disableComments
)

fun EventDateDto.toDomain(): EventDate = EventDate(
    start = start,
    end = end
)

fun EventDate.toDto(): EventDateDto = EventDateDto(
    start = start,
    end = end
)

fun PlaceDto?.toDomain(): Place? = this?.let {
    Place(id = it.id)
}

fun Place?.toDto(): PlaceDto? = this?.let {
    PlaceDto(id = it.id)
}

fun LocationSlugDto.toDomain(): LocationSlug = LocationSlug(
    slug = slug
)

fun LocationSlug.toDto(): LocationSlugDto = LocationSlugDto(
    slug = slug
)

fun EventImageDto.toDomain(): EventImage = EventImage(
    image = image,
    source = ImageSource(
        name = source.name,
        link = source.link
    )
)

fun EventImage.toDto(): EventImageDto = EventImageDto(
    image = image,
    source = ImageSourceDto(
        name = source.name,
        link = source.link
    )
)

fun ImageSourceDto.toDomain(): ImageSource = ImageSource(
    name = name,
    link = link
)

fun ImageSource.toDto(): ImageSourceDto = ImageSourceDto(
    name = name,
    link = link
)

fun PlaceDetailedDto.toDomain(): PlaceDetailed =
    PlaceDetailed(
        id = id,
        title = title,
        slug = slug,
        address = address,
        phone = phone,
        siteUrl = site_url,
        subway = subway,
        isClosed = is_closed,
        location = location,
        hasParkingLot = has_parking_lot,
        coords = coords?.toDomain()
    )


fun PlaceDetailed?.toDto(): PlaceDetailedDto? = this?.let {
    PlaceDetailedDto(
        id = it.id,
        title = it.title,
        slug = it.slug,
        address = it.address,
        phone = it.phone,
        site_url = it.siteUrl,
        subway = it.subway,
        is_closed = it.isClosed,
        location = it.location,
        has_parking_lot = it.hasParkingLot,
        coords = it.coords?.toDto()
    )
}

fun DetailedEventDto.toDomain(): DetailedEvent = DetailedEvent(
    id = id,
    publicationDate = publication_date,
    dates = dates.map { it.toDomain() },
    title = title,
    slug = slug,
    place = place.toDomain(),
    description = description,
    bodyText = body_text,
    location = location.toDomain(),
    categories = categories,
    tagline = tagline,
    ageRestriction = age_restriction,
    price = price,
    isFree = is_free,
    images = images.map { it.toDomain() },
    favoritesCount = favorites_count,
    commentsCount = comments_count,
    siteUrl = site_url,
    shortTitle = short_title,
    tags = tags,
    disableComments = disable_comments,
    detailedPlace = detailedPlace?.toDomain()
)

fun DetailedEvent.toDto(): DetailedEventDto = DetailedEventDto(
    id = id,
    publication_date = publicationDate,
    dates = dates.map { it.toDto() },
    title = title,
    slug = slug,
    place = place.toDto(),
    description = description,
    body_text = bodyText,
    location = location.toDto(),
    categories = categories,
    tagline = tagline,
    age_restriction = ageRestriction,
    price = price,
    is_free = isFree,
    images = images.map { it.toDto() },
    favorites_count = favoritesCount,
    comments_count = commentsCount,
    site_url = siteUrl,
    short_title = shortTitle,
    tags = tags,
    disable_comments = disableComments,
    detailedPlace = detailedPlace.toDto()
)