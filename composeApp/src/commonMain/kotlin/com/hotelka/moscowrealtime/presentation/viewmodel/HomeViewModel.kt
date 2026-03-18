package com.hotelka.moscowrealtime.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hotelka.moscowrealtime.domain.repository.GeoLocationHandler
import com.hotelka.moscowrealtime.domain.usecase.kudaGo.FetchEventsUseCase
import com.hotelka.moscowrealtime.domain.usecase.mrtApi.GetClosestLocationUseCase
import com.hotelka.moscowrealtime.presentation.events.HomePageEvents
import com.hotelka.moscowrealtime.presentation.model.HomePageModel
import com.hotelka.moscowrealtime.presentation.navigation.Destination
import com.hotelka.moscowrealtime.presentation.navigation.Navigator
import com.hotelka.moscowrealtime.presentation.state.listState.EventListState
import com.hotelka.moscowrealtime.presentation.utils.DateFormatters
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val navigator: Navigator,
    private val geoLocationHandler: GeoLocationHandler,
    private val fetchEventsUseCase: FetchEventsUseCase,
    private val getClosestLocationUseCase: GetClosestLocationUseCase,
) : ViewModel() {

    private val _homePageModel = MutableStateFlow(HomePageModel())
    val homePageModel: StateFlow<HomePageModel> = _homePageModel.asStateFlow()

    fun onEvent(event: HomePageEvents) {
        when (event) {
            HomePageEvents.OnNavigateClosestLocationMap -> {}
            is HomePageEvents.OnNavigateEvent -> {
                navigator.navigate(Destination.EventGraph.create(event.eventId))
            }

            HomePageEvents.OnRetryLoad -> {
                viewModelScope.launch { fetchEvents() }
            }
        }
    }

    init {
        setGreeting()
        observeLocation()
        fetchEvents()
    }


    private fun setGreeting() {
        viewModelScope.launch {
            val greeting = DateFormatters.getGreeting()
            _homePageModel.update { homeUiState ->
                homeUiState.copy(
                    greeting = greeting
                )
            }
        }
    }


    fun fetchEvents() {
        viewModelScope.launch {
            _homePageModel.update { homeUiState ->
                homeUiState.copy(
                    events = EventListState.Loading
                )
            }
            fetchEventsUseCase().fold(
                onSuccess = { list ->
                    _homePageModel.update { homeUiState ->
                        homeUiState.copy(
                            events = EventListState.Success(list)
                        )
                    }
                },
                onFailure = { e ->
                    _homePageModel.update { homeUiState ->
                        homeUiState.copy(
                            events = EventListState.Error(e.message.toString())
                        )
                    }
                }
            )
        }
    }

    private fun observeLocation() {
        viewModelScope.launch {
            geoLocationHandler.locationUpdates(60_000L).collect { location ->
                _homePageModel.update { uiModel ->
                    uiModel.copy(
                        closestLocation = getClosestLocationUseCase(
                            location.latitude,
                            location.longitude
                        ).data
                    )
                }
            }
        }
    }

    fun openEvent(eventId: Int) {
        navigator.navigate(Destination.EventGraph.create(eventId))
    }

}