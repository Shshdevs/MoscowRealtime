package com.hotelka.moscowrealtime.presentation.model

import com.hotelka.moscowrealtime.domain.model.mapData.Route
import com.hotelka.moscowrealtime.presentation.state.EventState

data class EventGraphUiModel(
    val eventState: EventState = EventState.Loading,
    val eventId: Int? = null,
    val eventLink: String? = null,
    val routeInfo: Route? = null,
    val isUserLocationSet: Boolean = false
)