package com.hotelka.moscowrealtime.data.mapper

import com.hotelka.moscowrealtime.data.dto.LocationDto
import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.domain.repository.LocaleProvider


class LocationMapper(
    private val localeProvider: LocaleProvider
) {
    fun toDomain(dto: LocationDto?): Location = Location(
        id = dto?.id!!,
        label = when (localeProvider.getLocale()) {
            "ru" -> dto.label_ru.toString()
            else -> dto.label_en.toString()
        },
        lat = dto.lat,
        lon = dto.lon,
        picture = dto.picture
    )
}