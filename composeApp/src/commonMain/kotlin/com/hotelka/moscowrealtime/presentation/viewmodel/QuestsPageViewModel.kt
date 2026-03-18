package com.hotelka.moscowrealtime.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hotelka.moscowrealtime.domain.usecase.questProgress.GetQuestProgressDetailsUseCase
import com.hotelka.moscowrealtime.domain.usecase.questProgress.ObserveUserQuestProgressUseCase
import com.hotelka.moscowrealtime.domain.usecase.quests.GetQuestsUseCase
import com.hotelka.moscowrealtime.presentation.controllers.MapManager
import com.hotelka.moscowrealtime.presentation.events.QuestsPageEvents
import com.hotelka.moscowrealtime.presentation.model.QuestsPageUiModel
import com.hotelka.moscowrealtime.presentation.navigation.Destination
import com.hotelka.moscowrealtime.presentation.navigation.Navigator
import com.hotelka.moscowrealtime.presentation.state.QuestProgressUiState
import com.hotelka.moscowrealtime.presentation.state.listState.QuestsListState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuestsPageViewModel(
    private val navigator: Navigator,
    private val mapManager: MapManager,
    private val observeUserQuestProgressUseCase: ObserveUserQuestProgressUseCase,
    private val getQuestProgressDetailsUseCase: GetQuestProgressDetailsUseCase,
    private val getQuestsUseCase: GetQuestsUseCase,
) : ViewModel() {
    private var addPlacemarkJob: Job? = null
    private val _questsPageUiModelState = MutableStateFlow(QuestsPageUiModel())
    val questsPageUiModelState: StateFlow<QuestsPageUiModel> = _questsPageUiModelState.asStateFlow()

    init {
        loadQuests()
        observeCurrentQuestProgress()
    }

    fun onEvent(event: QuestsPageEvents) {
        when (event) {
            is QuestsPageEvents.OnAddPlacemark -> {
                if (addPlacemarkJob?.isActive == true) return
                addPlacemarkJob = viewModelScope.launch {
                    while (!_questsPageUiModelState.value.mapIsSet) {
                        delay(500)
                    }
                    mapManager.clearRoute()
                    mapManager.addMarker(event.geoPoint)
                    mapManager.addRoutePoint(event.geoPoint)
                }
                viewModelScope.launch {
                    _questsPageUiModelState.update { uiModel ->
                        uiModel.copy(proceedButtonVisible = true)
                    }
                    delay(15_000L)
                    _questsPageUiModelState.update { uiModel ->
                        uiModel.copy(proceedButtonVisible = false)
                    }
                }
            }

            is QuestsPageEvents.OnAttachMap -> {
                mapManager.attachMap(event.mapView) {
                    _questsPageUiModelState.update { uiModel ->
                        uiModel.copy(mapIsSet = true)
                    }
                    event.onSetUserLocationMarker()
                }
            }

            QuestsPageEvents.OnMapExpandedChange -> {
                _questsPageUiModelState.update { uiModel ->
                    uiModel.copy(mapIsExpanded = !uiModel.mapIsExpanded)
                }
            }

            QuestsPageEvents.OnMoveToUserLocation -> {
                mapManager.moveToUserCurrentLocation()
            }

            QuestsPageEvents.OnDetachMap -> {
                _questsPageUiModelState.update { uiModel ->
                    uiModel.copy(mapIsSet = false)
                }
                mapManager.detachMap()
            }

            is QuestsPageEvents.OnNavigateQuestPage -> {
                navigator.navigate(Destination.QuestPage.create(event.questId))
            }

            QuestsPageEvents.OnNavigateQuestRoute -> {
                navigator.navigate(Destination.CurrQuestProgressPage.route)
            }

            QuestsPageEvents.OnNavigateQuestsLibrary -> {
                navigator.navigate(Destination.QuestsLibrary.route)
            }

            QuestsPageEvents.OnRetryLoad -> {
                loadQuests()
                observeCurrentQuestProgress()
            }
        }
    }

    fun loadQuests() {
        viewModelScope.launch {
            getQuestsUseCase().fold(
                onSuccess = { quests ->
                    _questsPageUiModelState.update { uiModel ->
                        _questsPageUiModelState.value.copy(
                            questsListState = QuestsListState.Success(
                                quests
                            )
                        )
                    }
                },
                onFailure = { e ->
                    _questsPageUiModelState.update { uiModel ->
                        _questsPageUiModelState.value.copy(questsListState = QuestsListState.Error(e.message.toString()))
                    }
                }
            )
        }
    }

    fun observeCurrentQuestProgress() {
        viewModelScope.launch {
            observeUserQuestProgressUseCase().collect { result ->
                val questProgress = result.fold(
                    onSuccess = { questProgress ->
                        if (questProgress != null) {
                            val questProgress = getQuestProgressDetailsUseCase(questProgress)
                            if (questProgress != null) {
                                QuestProgressUiState.Success(questProgress)
                            } else {
                                QuestProgressUiState.Error("Error load Quest Progress")
                            }
                        } else {
                            QuestProgressUiState.None
                        }
                    },
                    onFailure = { e ->
                        QuestProgressUiState.Error(e.message.toString())
                    }
                )
                _questsPageUiModelState.update { uiModel ->
                    _questsPageUiModelState.value.copy(questProgressUiState = questProgress)
                }
            }
        }
    }
}