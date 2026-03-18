package com.hotelka.moscowrealtime.presentation.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.hotelka.moscowrealtime.presentation.ui.Content.HistoryPageContent
import com.hotelka.moscowrealtime.presentation.viewmodel.HistoryPageViewModel

@Composable
fun DiscoversPage(
    historyPageViewModel: HistoryPageViewModel
) {
    val historyPageUiModel by historyPageViewModel.historyPageUiModel.collectAsState()

    Box(Modifier.fillMaxSize()) {
        HistoryPageContent(historyPageUiModel, historyPageViewModel::onEvent)
    }

}