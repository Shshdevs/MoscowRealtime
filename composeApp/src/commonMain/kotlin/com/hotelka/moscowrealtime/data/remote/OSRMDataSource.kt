package com.hotelka.moscowrealtime.data.remote

import com.hotelka.moscowrealtime.data.dto.api.OSRMApiDataDto

interface OSRMDataSource {
    suspend fun getDirections(coordinates: String): Result<OSRMApiDataDto>
}