package com.hotelka.moscowrealtime.domain.usecase.kudaGo

import com.hotelka.moscowrealtime.domain.model.api.DetailedEvent
import com.hotelka.moscowrealtime.domain.repository.KudaGoRepository

class FetchEventUseCase(
    private val repository: KudaGoRepository
) {
    suspend operator fun invoke(id: Int): Result<DetailedEvent> {
        return repository.getEventDetailing(id).map { event ->
            val place = event.place?.id?.let { placeId ->
                repository.getPlaceDetailed(placeId).onFailure {
                }.getOrNull()
            }
            var finalEvent = event
            place?.let { finalEvent = finalEvent.copy(detailedPlace = it) }
            return@map finalEvent
        }
    }
}