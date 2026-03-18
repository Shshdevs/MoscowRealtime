package com.hotelka.moscowrealtime.presentation.state.listState

import com.hotelka.moscowrealtime.domain.model.api.DetailedEvent

sealed class EventListState {
    object Loading: EventListState()
    data class Success(val events: List<DetailedEvent>): EventListState()
    data class Error(val message: String): EventListState()
}