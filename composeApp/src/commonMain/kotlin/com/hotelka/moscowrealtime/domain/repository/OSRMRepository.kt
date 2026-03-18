package com.hotelka.moscowrealtime.domain.repository

import com.hotelka.moscowrealtime.domain.model.api.OSRMApiData
import com.hotelka.moscowrealtime.domain.model.mapData.GeoPoint

interface OSRMRepository {
    suspend fun setDirections(
        points: List<GeoPoint>
    ): Result<OSRMApiData>
}