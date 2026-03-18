package com.hotelka.moscowrealtime.presentation.model

import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.domain.model.VerificationQuestResult
import com.hotelka.moscowrealtime.domain.model.detailed.DiscoverDetailed
import com.hotelka.moscowrealtime.domain.model.mapData.Route
import com.hotelka.moscowrealtime.presentation.state.QuestProgressRouteOpenedCardState
import com.hotelka.moscowrealtime.presentation.state.QuestProgressUiState

data class QuestProgressGraphUiModel(
    val questProgressUiState: QuestProgressUiState = QuestProgressUiState.Loading,
    val questProgressRouteOpenedCardState: QuestProgressRouteOpenedCardState = QuestProgressRouteOpenedCardState.Quest,
    val verificationQuestResult: VerificationQuestResult? = null,
    val questProgressValue: Float = 0.0f,
    val pointIsReachedByUser: User? = null,
    val awaitedLocation: Location? = null,
    val routeInfo: Route? = null,
    val cameraModeOn: Boolean = false,
    val isMapSet: Boolean = false,
    val showQuestIsDone: Boolean = false,
    val showRestartingProgress: Boolean = false,
    val showDiscover: DiscoverDetailed? = null,
)