package com.hotelka.moscowrealtime.presentation.ui.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.hotelka.moscowrealtime.presentation.ui.Content.HomeContent
import com.hotelka.moscowrealtime.presentation.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    homeViewModel: HomeViewModel
) {
    val homeUiState by homeViewModel.homePageModel.collectAsState()
    HomeContent(
        homeUiState,
        onEvent = homeViewModel::onEvent
    )
}