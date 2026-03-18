package com.hotelka.moscowrealtime.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.hotelka.moscowrealtime.presentation.ui.Content.FindFriendsPageContent
import com.hotelka.moscowrealtime.presentation.viewmodel.FindFriendsPageViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FindFriendsPage(findFriendsPageViewModel: FindFriendsPageViewModel = koinViewModel()) {
    val findFriendsPageUiModel by findFriendsPageViewModel.findFriendsPageUiModel.collectAsState()
    FindFriendsPageContent(findFriendsPageUiModel, findFriendsPageViewModel::onEvent)
}
