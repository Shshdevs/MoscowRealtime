package com.hotelka.moscowrealtime.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.hotelka.moscowrealtime.presentation.ui.Content.SignUpContent
import com.hotelka.moscowrealtime.presentation.viewmodel.AuthViewModel

@Composable
fun SignUpPage(authViewModel: AuthViewModel){

    val showLoad by authViewModel.showLoading.collectAsState()
    SignUpContent(showLoad,authViewModel::onEvent)
}