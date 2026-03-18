package com.hotelka.moscowrealtime.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.hotelka.moscowrealtime.presentation.ui.Content.QuestPageContent
import com.hotelka.moscowrealtime.presentation.viewmodel.QuestPageViewModel

@Composable
fun QuestPage(
    questPageViewModel: QuestPageViewModel,
) {
    val questPageUiModel by questPageViewModel.questUiModelState.collectAsState()

    QuestPageContent(
        questPageUiModel = questPageUiModel,
        onEvent = questPageViewModel::onEvent
    )
}