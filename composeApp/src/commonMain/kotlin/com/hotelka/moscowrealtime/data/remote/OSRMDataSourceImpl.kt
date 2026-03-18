package com.hotelka.moscowrealtime.data.remote

import co.touchlab.kermit.Logger
import com.hotelka.moscowrealtime.data.dto.api.OSRMApiDataDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class OSRMDataSourceImpl(
    private val client: HttpClient
): OSRMDataSource{
    override suspend fun getDirections(coordinates: String): Result<OSRMApiDataDto> {
        val url = "$BASE_URL$coordinates?overview=full&geometries=geojson"

        return try {
            val result = client.get(url).body<OSRMApiDataDto>()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(Exception("Error getting Directions OSRM", e))
        }
    }

    companion object{
        private const val BASE_URL = "https://routing.openstreetmap.de/routed-foot/route/v1/foot/"
    }
}