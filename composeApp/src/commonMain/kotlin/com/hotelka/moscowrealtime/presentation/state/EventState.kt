package com.hotelka.moscowrealtime.presentation.state

import com.hotelka.moscowrealtime.domain.model.api.DetailedEvent

sealed class EventState {
    object Loading: EventState()
    object None: EventState()
    data class Success(val event: DetailedEvent): EventState()
    data class Error(val message: String): EventState()
}