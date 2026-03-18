package com.hotelka.moscowrealtime.presentation.state

import com.hotelka.moscowrealtime.presentation.model.CurrUserProfileUiModel

sealed class CurrentUserProfileUiState {
    object None: CurrentUserProfileUiState()
    object Loading: CurrentUserProfileUiState()
    data class Success(val uiModel: CurrUserProfileUiModel): CurrentUserProfileUiState()
    data class Error(val error: String): CurrentUserProfileUiState()
}