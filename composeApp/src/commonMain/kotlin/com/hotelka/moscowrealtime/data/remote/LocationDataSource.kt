package com.hotelka.moscowrealtime.data.remote

import com.hotelka.moscowrealtime.data.dto.LocationDto

interface LocationDataSource {
    suspend fun getLocation(locationId: String): Result<LocationDto>
    suspend fun getLocationsByIds(locationIds: List<String>): Result<List<LocationDto>>
}