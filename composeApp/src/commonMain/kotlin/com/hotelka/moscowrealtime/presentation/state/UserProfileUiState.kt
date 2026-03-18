package com.hotelka.moscowrealtime.presentation.state

import com.hotelka.moscowrealtime.presentation.model.UserProfileUiModel

sealed class UserProfileUiState {
    object Loading : UserProfileUiState()
    data class Success(val uiModel: UserProfileUiModel) : UserProfileUiState()
    data class Error(val error: String) : UserProfileUiState()
}