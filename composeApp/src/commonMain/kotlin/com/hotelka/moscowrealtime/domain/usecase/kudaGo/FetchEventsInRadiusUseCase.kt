package com.hotelka.moscowrealtime.domain.usecase.kudaGo

import com.hotelka.moscowrealtime.data.remote.constants.KudaGoDefaults
import com.hotelka.moscowrealtime.domain.model.api.DetailedEvent
import com.hotelka.moscowrealtime.domain.model.api.Event
import com.hotelka.moscowrealtime.domain.repository.KudaGoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class FetchEventsInRadiusUseCase(
    private val repository: KudaGoRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(lat: Double, lon: Double, radius: Int): Result<List<DetailedEvent>> {
        return runCatching {
            val events = repository.fetchEventInRadius(
                KudaGoDefaults.defaultLocation,
                KudaGoDefaults.defaultActualSince,
                KudaGoDefaults.defaultOrderBy,
                lat,
                lon,
                radius
            ).getOrThrow()
            coroutineScope {
                events.map { event ->
                    async(dispatcher) {
                        try {
                            enrichEventWithDetails(event)
                        } catch (e: Exception) {
                            null
                        }
                    }
                }.awaitAll().filterNotNull()
            }
        }
    }
    private suspend fun enrichEventWithDetails(event: Event): DetailedEvent {
        val detailedEvent = repository.getEventDetailing(event.id)
            .getOrThrow()

        val placeWithDetails = detailedEvent.place?.id?.let { placeId ->
            repository.getPlaceDetailed(placeId)
                .getOrDefault(null)
        }

        return placeWithDetails?.let { detailedEvent.copy(detailedPlace = it) }
            ?: detailedEvent
    }
}