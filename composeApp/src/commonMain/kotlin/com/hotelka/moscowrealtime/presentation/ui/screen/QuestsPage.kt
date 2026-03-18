package com.hotelka.moscowrealtime.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.hotelka.moscowrealtime.presentation.ui.Content.QuestsPageContent
import com.hotelka.moscowrealtime.presentation.viewmodel.QuestsPageViewModel

@Composable
fun QuestsPage(
    questsPageViewModel: QuestsPageViewModel,
) {
    val questsPageUiModelState by questsPageViewModel.questsPageUiModelState.collectAsState()

    QuestsPageContent(
        questsPageUiModelState = questsPageUiModelState,
        questsPageViewModel::onEvent
    )
}