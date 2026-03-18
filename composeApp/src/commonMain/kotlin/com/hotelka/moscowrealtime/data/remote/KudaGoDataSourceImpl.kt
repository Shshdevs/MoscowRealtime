package com.hotelka.moscowrealtime.data.remote

import co.touchlab.kermit.Logger
import com.hotelka.moscowrealtime.data.dto.api.DetailedEventDto
import com.hotelka.moscowrealtime.data.dto.api.EventDto
import com.hotelka.moscowrealtime.data.dto.api.KudaGoApiResults
import com.hotelka.moscowrealtime.data.dto.api.PlaceDetailedDto
import com.hotelka.moscowrealtime.domain.repository.LocaleProvider
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class KudaGoDataSourceImpl(
    private val client: HttpClient,
    private val localeProvider: LocaleProvider
) : KudaGoDataSource {
    companion object {
        private const val BASE_URL = "https://kudago.com/public-api/v1.4/"
    }

    override suspend fun fetchEvents(
        location: String,
        actualSince: Long,
        orderBy: String
    ): Result<List<EventDto>> = try {
        Logger.withTag("LocationDataLocale").d { localeProvider.getLocale().take(2) }
        val result = client.get(
            BASE_URL + "events/?lang=${localeProvider.getLocale().take(2)}&order_by=$orderBy&location=$location&actual_since=$actualSince".trimIndent()
        ).body<KudaGoApiResults<EventDto>>()
        Result.success(result.results)
    } catch (e: Exception) {
        Result.failure(Exception("Error fetching events", e))
    }

    override suspend fun fetchEventsInRadius(
        location: String,
        actualSince: Long,
        orderBy: String,
        lat: Double,
        lon: Double,
        radius: Int
    ): Result<List<EventDto>> = try {
        val result = client.get(
            BASE_URL + "events/?lang=ru&order_by=$orderBy&location=$location&actual_since=$actualSince&lat=$lat&lon=$lon&radius=$radius".trimIndent()
        ).body<KudaGoApiResults<EventDto>>()
        Result.success(result.results)
    } catch (e: Exception) {
        Result.failure(Exception("Error fetching events", e))
    }

    override suspend fun getEventDetailing(id: Int): Result<DetailedEventDto> = try {
        val result = client.get(BASE_URL + "events/$id/?lang=&fields=").body<DetailedEventDto>()
        Result.success(result)
    } catch (e: Exception) {
        Result.failure(Exception("Error fetching event detalization", e))
    }

    override suspend fun getPlaceDetailed(id: Int): Result<PlaceDetailedDto> = try {
        val result = client.get(BASE_URL + "places/$id/?lang=&fields=").body<PlaceDetailedDto>()
        Result.success(result)
    } catch (e: Exception) {
        Result.failure(Exception("Error fetching place detalization", e))
    }
}

