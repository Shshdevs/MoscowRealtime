package com.hotelka.moscowrealtime.data.repository

import com.hotelka.moscowrealtime.data.remote.LocationDataSource
import com.hotelka.moscowrealtime.data.mapper.LocationMapper
import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.domain.repository.LocationsRepository

class LocationsRepositoryImpl(
    private val locationMapper: LocationMapper,
    private val locationDataSource: LocationDataSource
) : LocationsRepository {
    override suspend fun getLocation(locationId: String): Result<Location> {
        return locationDataSource.getLocation(locationId)
            .map { locationMapper.toDomain(it) }
    }

    override suspend fun getLocationsByIds(locationIds: List<String>): Result<List<Location>> {
        return locationDataSource.getLocationsByIds(locationIds)
            .map { list ->
                list.map { locationMapper.toDomain(it) }
            }
    }
}