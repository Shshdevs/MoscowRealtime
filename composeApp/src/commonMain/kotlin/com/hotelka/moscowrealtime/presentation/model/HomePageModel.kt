package com.hotelka.moscowrealtime.presentation.model

import com.hotelka.moscowrealtime.domain.model.api.ClosestLocationResult
import com.hotelka.moscowrealtime.presentation.state.listState.EventListState

data class HomePageModel(
    val greeting: String = "",
    val closestLocation: ClosestLocationResult? = null,
    val events: EventListState = EventListState.Loading,
)