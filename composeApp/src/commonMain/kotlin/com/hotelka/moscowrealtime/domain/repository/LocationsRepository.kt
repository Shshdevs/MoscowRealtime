package com.hotelka.moscowrealtime.domain.repository

import com.hotelka.moscowrealtime.domain.model.Location

interface LocationsRepository {
    suspend fun getLocation(locationId: String): Result<Location>
    suspend fun getLocationsByIds(locationIds: List<String>): Result<List<Location>>
}