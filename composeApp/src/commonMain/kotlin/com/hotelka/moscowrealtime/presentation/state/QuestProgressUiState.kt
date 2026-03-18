package com.hotelka.moscowrealtime.presentation.state

import com.hotelka.moscowrealtime.domain.model.detailed.QuestProgressDetailed

sealed class QuestProgressUiState {
    object None: QuestProgressUiState()
    object Loading: QuestProgressUiState()
    data class Success(val questProgressDetailed: QuestProgressDetailed): QuestProgressUiState()
    data class Error(val error: String):QuestProgressUiState()
}