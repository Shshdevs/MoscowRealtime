package com.hotelka.moscowrealtime.presentation.state.listState

import com.hotelka.moscowrealtime.domain.model.Location

sealed class LocationsListState {
    object Loading: LocationsListState()
    data class Success(val locations: List<Location>): LocationsListState()
    data class Error(val message: String): LocationsListState()
}