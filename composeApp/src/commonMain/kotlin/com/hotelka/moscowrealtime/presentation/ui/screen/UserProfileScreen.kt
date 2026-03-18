package com.hotelka.moscowrealtime.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.hotelka.moscowrealtime.presentation.events.UserProfileEvents
import com.hotelka.moscowrealtime.presentation.state.UserProfileUiState
import com.hotelka.moscowrealtime.presentation.ui.Content.UserProfileContent
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.ErrorForm
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.LoadingForm
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.viewmodel.UserProfilePageViewModel
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.loading_user
import moscowrealtime.composeapp.generated.resources.loading_user_error
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserProfileScreen(
    userProfileViewModel: UserProfilePageViewModel = koinViewModel()
) {
    val userProfilePageState by userProfileViewModel.userProfileUiState.collectAsState()

    Box(Modifier.fillMaxSize()) {
        when (userProfilePageState) {
            is UserProfileUiState.Error -> {
                ErrorForm(
                    modifier = Modifier.fillMaxSize(),
                    errorDescription = Res.string.loading_user_error
                ) {userProfileViewModel.onEvent(UserProfileEvents.OnReload) }
            }

            UserProfileUiState.Loading -> {
                LoadingForm(
                    Modifier.fillMaxSize().background(background),
                    stringResource(Res.string.loading_user)
                )
            }

            is UserProfileUiState.Success -> {
                UserProfileContent(
                    uiModel = (userProfilePageState as UserProfileUiState.Success).uiModel,
                    onEvent = userProfileViewModel::onEvent
                )

            }
        }
    }
}