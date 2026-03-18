package com.hotelka.moscowrealtime.presentation.model

import com.hotelka.moscowrealtime.presentation.state.TopWeeklyUsersUiState
import com.hotelka.moscowrealtime.presentation.state.listState.QuestsListState

data class LibraryPageUiModel(
    val weeklyTopUsers: TopWeeklyUsersUiState = TopWeeklyUsersUiState.Loading,
    val questsListState: QuestsListState = QuestsListState.Loading,
    val searchQuery: String = ""
)