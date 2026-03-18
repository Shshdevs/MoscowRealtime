package com.hotelka.moscowrealtime.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hotelka.moscowrealtime.domain.usecase.kudaGo.FetchEventsInRadiusUseCase
import com.hotelka.moscowrealtime.presentation.controllers.MapManager
import com.hotelka.moscowrealtime.presentation.events.DiscoverPageEvents
import com.hotelka.moscowrealtime.presentation.model.DiscoverPageUiModel
import com.hotelka.moscowrealtime.domain.model.detailed.DiscoverDetailed
import com.hotelka.moscowrealtime.presentation.navigation.Destination
import com.hotelka.moscowrealtime.presentation.navigation.Navigator
import com.hotelka.moscowrealtime.presentation.state.listState.EventListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DiscoverPageViewModel(
    private val navigator: Navigator,
    private val mapManager: MapManager,
    private val fetchEventsInRadiusUseCase: FetchEventsInRadiusUseCase,
) : ViewModel() {
    private val _discoverPageUiState = MutableStateFlow(DiscoverPageUiModel())
    val discoverUiState: StateFlow<DiscoverPageUiModel> = _discoverPageUiState.asStateFlow()

    fun onEvent(event: DiscoverPageEvents) {
        when (event) {
            DiscoverPageEvents.OnChangeAroundEventsOpen -> {
                _discoverPageUiState.value =
                    _discoverPageUiState.value.copy(showAroundEvent = !_discoverPageUiState.value.showAroundEvent)
            }

            is DiscoverPageEvents.OnNavigateEvent -> {
                navigator.navigate(Destination.EventGraph.create(event.eventId))
            }

            DiscoverPageEvents.OnRetryLoadEvents -> {
                fetchEventsInLocationRadius()
            }

            is DiscoverPageEvents.OnAddMarker -> {
                mapManager.addMarker(event.geoPoint)
            }

            DiscoverPageEvents.OnMoveToCurrentUserLocation -> {
                mapManager.moveToUserCurrentLocation()
            }

            is DiscoverPageEvents.OnSetMapView -> {
                mapManager.attachMap(event.mapView){event.onSetMapView()}
            }

            is DiscoverPageEvents.OnMoveTo -> {
                mapManager.moveTo(event.geoPoint)
            }

            DiscoverPageEvents.OnDetachMapView -> {
                mapManager.detachMap()
            }

        }
    }

    fun setDiscover(discover: DiscoverDetailed) {
        _discoverPageUiState.value = _discoverPageUiState.value.copy(discover = discover)
        fetchEventsInLocationRadius()
    }

    fun fetchEventsInLocationRadius() {
        _discoverPageUiState.value =
            _discoverPageUiState.value.copy(aroundEvents = EventListState.Loading)
        viewModelScope.launch {
            _discoverPageUiState.value.discover?.location?.let { location ->
                fetchEventsInRadiusUseCase(location.lat, location.lon, 1000).fold(
                    onSuccess = { list ->
                        _discoverPageUiState.value =
                            _discoverPageUiState.value.copy(
                                aroundEvents = EventListState.Success(
                                    list
                                )
                            )
                    },
                    onFailure = { e ->
                        _discoverPageUiState.value =
                            _discoverPageUiState.value.copy(aroundEvents = EventListState.Error(e.message.toString()))
                    }
                )
            }
        }
    }
}