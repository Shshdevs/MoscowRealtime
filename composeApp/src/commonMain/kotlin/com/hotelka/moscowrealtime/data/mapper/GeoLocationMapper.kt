package com.hotelka.moscowrealtime.data.mapper

import com.hotelka.moscowrealtime.data.dto.GeoLocationDto
import com.hotelka.moscowrealtime.domain.model.GeoLocation

fun GeoLocation.toDto(): GeoLocationDto {
    return GeoLocationDto(
        latitude = latitude,
        longitude = longitude,
        accuracy = accuracy,
        timestamp = timestamp
    )
}

fun GeoLocationDto.toDomain(): GeoLocation {
    return GeoLocation(
        latitude = latitude,
        longitude = longitude,
        accuracy = accuracy,
        timestamp = timestamp
    )
}