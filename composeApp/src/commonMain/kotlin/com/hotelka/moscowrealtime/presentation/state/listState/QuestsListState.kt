package com.hotelka.moscowrealtime.presentation.state.listState

import com.hotelka.moscowrealtime.domain.model.Quest

sealed class QuestsListState {
    object Loading: QuestsListState()
    data class Success(val quests: List<Quest>): QuestsListState()
    data class Error(val message: String): QuestsListState()
}