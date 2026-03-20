package com.hotelka.moscowrealtime.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hotelka.moscowrealtime.domain.model.Discover
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.domain.model.VerificationQuestResult
import com.hotelka.moscowrealtime.domain.usecase.auth.GetCurrentUserIdUseCase
import com.hotelka.moscowrealtime.domain.usecase.questProgress.AddVerificationQuestResultUseCase
import com.hotelka.moscowrealtime.domain.usecase.questProgress.GetQuestProgressDetailsUseCase
import com.hotelka.moscowrealtime.domain.usecase.questProgress.ObserveUserQuestProgressUseCase
import com.hotelka.moscowrealtime.domain.usecase.questProgress.RestartQuestProgressUseCase
import com.hotelka.moscowrealtime.domain.usecase.scores.AddUserScoreUseCase
import com.hotelka.moscowrealtime.presentation.controllers.MapManager
import com.hotelka.moscowrealtime.presentation.events.QuestProgressGraphEvents
import com.hotelka.moscowrealtime.presentation.model.QuestProgressGraphUiModel
import com.hotelka.moscowrealtime.presentation.state.QuestProgressRouteOpenedCardState
import com.hotelka.moscowrealtime.domain.model.detailed.QuestProgressDetailed
import com.hotelka.moscowrealtime.presentation.navigation.Destination
import com.hotelka.moscowrealtime.presentation.navigation.Navigator
import com.hotelka.moscowrealtime.presentation.state.QuestProgressUiState
import com.hotelka.moscowrealtime.presentation.utils.filterSuccessfulDiscovers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuestProgressGraphViewModel(
    private val navigator: Navigator,
    private val mapManager: MapManager,
    private val getQuestProgressDetailsUseCase: GetQuestProgressDetailsUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val observeUserQuestProgressUseCase: ObserveUserQuestProgressUseCase,
    private val addUserScoreUseCase: AddUserScoreUseCase,
    private val addVerificationQuestResultUseCase: AddVerificationQuestResultUseCase,
    private val restartQuestUseCase: RestartQuestProgressUseCase
) : ViewModel() {

    private var verificationJob: Job? = null
    private val currUserId: String? get() = getCurrentUserIdUseCase()
    private val _questProgressGraphState = MutableStateFlow(QuestProgressGraphUiModel())
    val questProgressGraphState: StateFlow<QuestProgressGraphUiModel> =
        _questProgressGraphState.asStateFlow()

    fun onEvent(event: QuestProgressGraphEvents) {
        when (event) {
            QuestProgressGraphEvents.OnChangeCameraMode -> {
                _questProgressGraphState.value =
                    _questProgressGraphState.value.copy(cameraModeOn = !_questProgressGraphState.value.cameraModeOn)
            }

            QuestProgressGraphEvents.OnNavigateBack -> {
                navigator.back()
            }

            QuestProgressGraphEvents.OnRetryLoadQuestProgress -> {
                observeCurrentQuestProgress()
            }

            QuestProgressGraphEvents.OnResetVerificationResult -> {
                _questProgressGraphState.value = _questProgressGraphState.value.copy(
                    verificationQuestResult = null
                )
            }

            is QuestProgressGraphEvents.OnVerifyResult -> {
                val verificationResult = verifyResult(event.discover, event.questId, event.score)
                _questProgressGraphState.value = _questProgressGraphState.value.copy(
                    verificationQuestResult = verificationResult,
                    questProgressRouteOpenedCardState = QuestProgressRouteOpenedCardState.Quest
                )
            }

            is QuestProgressGraphEvents.OnNavigateMap -> {
                _questProgressGraphState.value =
                    _questProgressGraphState.value.copy(awaitedLocation = event.location)
                navigator.navigate(Destination.CurrQuestInteractiveMap.route)
            }

            is QuestProgressGraphEvents.OnSetQuestProgressRouteOpenedCard -> {
                _questProgressGraphState.value =
                    _questProgressGraphState.value.copy(questProgressRouteOpenedCardState = event.questProgressRouteOpenedCardState)
            }

            is QuestProgressGraphEvents.OnShowDiscover -> {
                _questProgressGraphState.value =
                    _questProgressGraphState.value.copy(showDiscover = event.discover)
            }

            is QuestProgressGraphEvents.OnAttachMap -> {
                mapManager.attachMap(event.mapView) {
                    _questProgressGraphState.value =
                        _questProgressGraphState.value.copy(isMapSet = true)
                    event.onSet()
                }
            }

            QuestProgressGraphEvents.OnDetachMap -> {
                _questProgressGraphState.value =
                    _questProgressGraphState.value.copy(isMapSet = false)
                mapManager.detachMap()
            }

            QuestProgressGraphEvents.OnMoveToUserLocation -> {
                mapManager.moveToUserCurrentLocation()
            }

            is QuestProgressGraphEvents.OnSetPlacemark -> {
                viewModelScope.launch {
                    while (!_questProgressGraphState.value.isMapSet) {
                        delay(500)
                    }
                    mapManager.addMarker(event.geoPoint)
                    mapManager.addRoutePoint(event.geoPoint).onSuccess { route ->
                        _questProgressGraphState.value =
                            _questProgressGraphState.value.copy(routeInfo = route)
                    }
                }
            }

            QuestProgressGraphEvents.OnChangeShowQuestDone -> {
                _questProgressGraphState.update { uiModel ->
                    uiModel.copy(showQuestIsDone = !uiModel.showQuestIsDone)
                }
            }

            is QuestProgressGraphEvents.OnRestartQuest -> {
                _questProgressGraphState.value =
                    _questProgressGraphState.value.copy(showRestartingProgress = true)
                viewModelScope.launch {
                    restartQuestUseCase(event.questProgressDetailed).onSuccess {
                        observeCurrentQuestProgress()
                        _questProgressGraphState.value =
                            _questProgressGraphState.value.copy(showRestartingProgress = false)
                    }
                }
            }
        }
    }

    init {
        observeCurrentQuestProgress()
    }

    private fun verifyResult(
        discover: Discover,
        questId: String,
        score: Int
    ): VerificationQuestResult? {
        val locationId = _questProgressGraphState.value.awaitedLocation?.id ?: return null
        val verificationResult = VerificationQuestResult(
            awaitedLocation = locationId,
            receivedLocation = discover.detections[0].locationId,
            result = discover.id,
            userId = discover.userAuthor,
            success = locationId == discover.detections[0].locationId
        )
        if (verificationJob == null){
            verificationJob =  viewModelScope.launch {
                if (verificationResult.success) {
                    if (score <= 5) {
                        addUserScoreUseCase(score, discover.id)
                    } else {
                        addUserScoreUseCase(score, questId)
                    }
                }
                addVerificationQuestResultUseCase(
                    verificationQuestResult = verificationResult,
                    questId = questId
                )
            }
            verificationJob?.start()
            verificationJob?.invokeOnCompletion { verificationJob = null }
        }
        return verificationResult
    }

    fun observeCurrentQuestProgress() {
        viewModelScope.launch {
            observeUserQuestProgressUseCase().collect { result ->
                val questProgressState = result.fold(
                    onSuccess = { questProgress ->
                        if (questProgress != null) {
                            val questProgressUi = getQuestProgressDetailsUseCase(questProgress)!!
                            val thePointNotReached = checkThePointNotReached(questProgressUi)
                            val questProgressValue = questProgressUi.discovers.filterSuccessfulDiscovers(questProgress).size.toFloat() / questProgressUi.locations.size.toFloat()
                            _questProgressGraphState.update { uiModule ->
                                uiModule.copy(
                                    showQuestIsDone = questProgressValue == 1f,
                                    pointIsReachedByUser = thePointNotReached,
                                    questProgressValue = questProgressValue
                                )
                            }
                            QuestProgressUiState.Success(questProgressUi)
                        } else {
                            QuestProgressUiState.None
                        }
                    },
                    onFailure = { e ->
                        QuestProgressUiState.Error(e.message.toString())
                    }
                )
                _questProgressGraphState.update { uiModule ->
                    uiModule.copy(
                        questProgressUiState = questProgressState,
                    )
                }
            }
        }
    }

    private fun checkThePointNotReached(
        questProgress: QuestProgressDetailed,
    ): User? {
        val successfulDiscovers =
            questProgress.discovers.filterSuccessfulDiscovers(questProgress.questProgress)

        if (successfulDiscovers.isNotEmpty()) {
            val currentLocationDiscovers = successfulDiscovers.filter { discover ->
                discover.location?.id == _questProgressGraphState.value.awaitedLocation?.id
            }
            return if (currentLocationDiscovers.isNotEmpty()) {
                val userAuthor = currentLocationDiscovers.getOrNull(0)?.userAuthor
                if (userAuthor?.id != currUserId) userAuthor
                else null
            } else {
                null
            }
        } else {
            return null
        }
    }
}