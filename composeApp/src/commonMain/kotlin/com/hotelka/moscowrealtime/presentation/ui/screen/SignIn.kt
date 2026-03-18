package com.hotelka.moscowrealtime.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.hotelka.moscowrealtime.presentation.ui.Content.SignInContent
import com.hotelka.moscowrealtime.presentation.viewmodel.AuthViewModel

@Composable
fun SignInPage(
    authViewModel: AuthViewModel,
) {
    val showLoad by authViewModel.showLoading.collectAsState()

    SignInContent(showLoad, authViewModel::onEvent)
}