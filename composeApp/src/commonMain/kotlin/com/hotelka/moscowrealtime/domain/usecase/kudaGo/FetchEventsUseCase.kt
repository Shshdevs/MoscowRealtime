package com.hotelka.moscowrealtime.domain.usecase.kudaGo

import com.hotelka.moscowrealtime.data.remote.constants.KudaGoDefaults
import com.hotelka.moscowrealtime.domain.model.api.DetailedEvent
import com.hotelka.moscowrealtime.domain.model.api.Event
import com.hotelka.moscowrealtime.domain.repository.KudaGoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit

class FetchEventsUseCase(
    private val repository: KudaGoRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    private val semaphore = Semaphore(permits = 4)

    suspend operator fun invoke(): Result<List<DetailedEvent>> = runCatching {
        val events = repository.fetchEvents(
            KudaGoDefaults.defaultLocation,
            KudaGoDefaults.defaultActualSince,
            KudaGoDefaults.defaultOrderBy
        ).getOrThrow()

        coroutineScope {
            events.map { event ->
                async(dispatcher) {
                    semaphore.withPermit {
                        runCatching { enrichEventWithDetails(event) }.getOrNull()
                    }
                }
            }.awaitAll().filterNotNull()
        }
    }

    private suspend fun enrichEventWithDetails(event: Event): DetailedEvent? {
        return repository.getEventDetailing(event.id).map { event ->
            val place = event.place?.id?.let { placeId ->
                repository.getPlaceDetailed(placeId).onFailure {
                }.getOrNull()
            }
            var finalEvent = event
            place?.let { finalEvent = finalEvent.copy(detailedPlace = it) }
            return@map finalEvent
        }.getOrNull()
    }
}