package com.hotelka.moscowrealtime.presentation.state

import com.hotelka.moscowrealtime.domain.model.detailed.QuestDetailed

sealed class QuestUiModelState {
    object Loading : QuestUiModelState()
    data class Success(val questDetailed: QuestDetailed) : QuestUiModelState()
    data class Error(val message: String) : QuestUiModelState()
}