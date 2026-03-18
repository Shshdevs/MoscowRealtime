package com.hotelka.moscowrealtime.data.repository

import com.hotelka.moscowrealtime.data.remote.OSRMDataSource
import com.hotelka.moscowrealtime.data.mapper.api.toDomain
import com.hotelka.moscowrealtime.domain.model.api.OSRMApiData
import com.hotelka.moscowrealtime.domain.model.mapData.GeoPoint
import com.hotelka.moscowrealtime.domain.repository.OSRMRepository

class OSRMRepositoryImpl(
    private val osrmDataSource: OSRMDataSource
) : OSRMRepository {
    override suspend fun setDirections(points: List<GeoPoint>): Result<OSRMApiData> {
        val coordinates = points.joinToString(";") { "${it.lon},${it.lat}" }
        return osrmDataSource.getDirections(coordinates).map { it.toDomain() }
    }

}