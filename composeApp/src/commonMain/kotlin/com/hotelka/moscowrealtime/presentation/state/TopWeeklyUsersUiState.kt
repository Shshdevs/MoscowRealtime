package com.hotelka.moscowrealtime.presentation.state

import com.hotelka.moscowrealtime.presentation.model.TopWeeklyUsersUiModel

sealed class TopWeeklyUsersUiState {
    object None : TopWeeklyUsersUiState()
    object Loading : TopWeeklyUsersUiState()
    data class Success(val uiModel: TopWeeklyUsersUiModel) : TopWeeklyUsersUiState()
    data class Error(val error: String) : TopWeeklyUsersUiState()
}