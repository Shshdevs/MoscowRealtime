package com.hotelka.moscowrealtime.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.hotelka.moscowrealtime.presentation.events.CurrentUserProfileEvents
import com.hotelka.moscowrealtime.presentation.state.CurrentUserProfileUiState
import com.hotelka.moscowrealtime.presentation.ui.Content.CurrUserProfileContent
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.ErrorForm
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.LoadingForm
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.viewmodel.CurrentUserProfileViewModel
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.loading_user
import org.jetbrains.compose.resources.stringResource

@Composable
fun CurrUserProfile(
    currentUserProfileViewModel: CurrentUserProfileViewModel
) {
    val userProfileState by currentUserProfileViewModel.currentUserProfileUiState.collectAsState()
    Box(Modifier.fillMaxSize()) {
        when (userProfileState) {
            is CurrentUserProfileUiState.Error -> {
                ErrorForm(
                    modifier = Modifier.fillMaxSize(),
                    errorDescription = Res.string.loading_user
                ) { currentUserProfileViewModel.onEvent(CurrentUserProfileEvents.OnReload) }
            }

            CurrentUserProfileUiState.Loading -> {
                LoadingForm(
                    Modifier.fillMaxSize().background(background),
                    stringResource(Res.string.loading_user)
                )
            }

            CurrentUserProfileUiState.None -> {}

            is CurrentUserProfileUiState.Success -> {
                CurrUserProfileContent(
                    uiModel = (userProfileState as CurrentUserProfileUiState.Success).uiModel,
                    onEvent = currentUserProfileViewModel::onEvent
                )
            }
        }
    }
}


