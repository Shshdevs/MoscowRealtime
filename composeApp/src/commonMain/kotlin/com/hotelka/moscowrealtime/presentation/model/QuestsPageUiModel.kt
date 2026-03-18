package com.hotelka.moscowrealtime.presentation.model

import com.hotelka.moscowrealtime.presentation.state.QuestProgressUiState
import com.hotelka.moscowrealtime.presentation.state.listState.QuestsListState

data class QuestsPageUiModel(
    val questsListState: QuestsListState = QuestsListState.Loading,
    val questProgressUiState: QuestProgressUiState = QuestProgressUiState.Loading,
    val proceedButtonVisible: Boolean = false,
    val mapIsSet: Boolean = false,
    val mapIsExpanded: Boolean = false
)