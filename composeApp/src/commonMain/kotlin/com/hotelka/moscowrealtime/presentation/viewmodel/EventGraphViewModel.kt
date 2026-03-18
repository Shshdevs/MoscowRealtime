package com.hotelka.moscowrealtime.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hotelka.moscowrealtime.domain.repository.LinkHandler
import com.hotelka.moscowrealtime.domain.usecase.kudaGo.FetchEventUseCase
import com.hotelka.moscowrealtime.presentation.controllers.MapManager
import com.hotelka.moscowrealtime.presentation.events.EventGraphEvents
import com.hotelka.moscowrealtime.presentation.model.EventGraphUiModel
import com.hotelka.moscowrealtime.presentation.navigation.Destination
import com.hotelka.moscowrealtime.presentation.navigation.Navigator
import com.hotelka.moscowrealtime.presentation.state.EventState
import com.hotelka.moscowrealtime.presentation.controllers.MenuHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EventGraphViewModel(
    private val navigator: Navigator,
    private val mapManager: MapManager,
    private val menuHandler: MenuHandler,
    private val fetchEventUseCase: FetchEventUseCase,
    private val linkHandler: LinkHandler,
) : ViewModel() {
    private val _eventState = MutableStateFlow(EventGraphUiModel())
    val eventState: StateFlow<EventGraphUiModel> = _eventState.asStateFlow()

    fun onEvent(event: EventGraphEvents) {
        when (event) {
            is EventGraphEvents.OnAddPlacemark -> {
                mapManager.clearRoute()
                mapManager.addMarker(event.geoPoint)
                viewModelScope.launch {
                    mapManager.addRoutePoint(event.geoPoint).onSuccess { route ->
                        _eventState.value = _eventState.value.copy(routeInfo = route)
                    }
                }
            }

            is EventGraphEvents.OnAttachMap -> {
                mapManager.attachMap(event.mapView) {
                    _eventState.value = _eventState.value.copy(
                        isUserLocationSet = true
                    )
                }
            }

            EventGraphEvents.OnDetachMap -> {
                mapManager.detachMap()
            }

            EventGraphEvents.OnGoBack -> {
                navigator.back()
            }

            EventGraphEvents.OnMoveToUserLocation -> {
                mapManager.moveToUserCurrentLocation()
            }

            EventGraphEvents.OnNavigateEventMap -> {
                navigator.navigate(Destination.CurrEventMapRoute.route)
                menuHandler.updateMenuVisible(false)
            }

            EventGraphEvents.OnOpenEventLink -> {
                _eventState.value.eventLink?.let {
                    linkHandler.openUrl(it)
                }
            }

            EventGraphEvents.OnRetryLoadEvent -> {
                _eventState.value.eventId?.let {
                    _eventState.value = _eventState.value.copy(eventState = EventState.Loading)
                    getEvent(it)
                }
            }
        }
    }

    fun getEvent(id: Int) {
        _eventState.value = _eventState.value.copy(
            eventId = id
        )
        viewModelScope.launch {
            fetchEventUseCase(id).fold(
                onSuccess = { event ->
                    _eventState.value = _eventState.value.copy(
                        eventState = EventState.Success(event),
                        eventLink = event.siteUrl
                    )
                },
                onFailure = { e ->
                    _eventState.value = _eventState.value.copy(
                        eventState = EventState.Error(e.message.toString())
                    )
                }
            )
        }
    }

}