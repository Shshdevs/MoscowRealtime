package com.hotelka.moscowrealtime.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hotelka.moscowrealtime.domain.usecase.discovers.GetDiscoversDetailsUseCase
import com.hotelka.moscowrealtime.domain.usecase.discovers.ObserveUserDiscoversUseCase
import com.hotelka.moscowrealtime.presentation.events.HistoryPageEvents
import com.hotelka.moscowrealtime.presentation.model.HistoryPageUiModel
import com.hotelka.moscowrealtime.presentation.navigation.Destination
import com.hotelka.moscowrealtime.presentation.navigation.Navigator
import com.hotelka.moscowrealtime.presentation.state.listState.DiscoversListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistoryPageViewModel(
    private val navigator: Navigator,
    private val getDiscoversDetailsUseCase: GetDiscoversDetailsUseCase,
    private val observeUserDiscoversUseCase: ObserveUserDiscoversUseCase,
) : ViewModel() {
    private val _historyPageUiModel = MutableStateFlow(HistoryPageUiModel())
    val historyPageUiModel: StateFlow<HistoryPageUiModel> = _historyPageUiModel.asStateFlow()

    init {
        observeUserDiscoveries()
    }

    fun onEvent(event: HistoryPageEvents) {
        when (event) {
            is HistoryPageEvents.OnChangeDiscoverOpened -> {
                _historyPageUiModel.update { uiModel -> uiModel.copy(discoverOpened = event.discover) }
            }

            HistoryPageEvents.OnNavigateQuestsPage -> {
                navigator.navigate(Destination.Quests.route)
            }

            HistoryPageEvents.OnRetryLoad -> {
                observeUserDiscoveries()
            }
        }
    }

    fun observeUserDiscoveries() {
        viewModelScope.launch {
            observeUserDiscoversUseCase().collect { result ->
                val discovers = result.fold(
                    onSuccess = { discovers ->
                        val details = getDiscoversDetailsUseCase(discovers)
                        DiscoversListState.Success(details)
                    },
                    onFailure = { error ->
                        DiscoversListState.Error(error.message.toString())
                    }
                )
                _historyPageUiModel.update { uiModel -> uiModel.copy(discoversListState = discovers) }
            }
        }
    }
}