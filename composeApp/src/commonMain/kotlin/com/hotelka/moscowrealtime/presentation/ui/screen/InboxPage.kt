package com.hotelka.moscowrealtime.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.hotelka.moscowrealtime.presentation.ui.Content.InboxContent
import com.hotelka.moscowrealtime.presentation.viewmodel.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun InboxPage(
    authViewModel: AuthViewModel
) {
    authViewModel.observeEmailVerification()
    var timer by remember { mutableStateOf(0) }
    LaunchedEffect(timer) {
        delay(1000)
        if (timer <= 30) timer++
    }
    InboxContent(timer) {
        if (timer > 30) {
            authViewModel.sendEmailVerification(); timer = 0
        }
    }
}