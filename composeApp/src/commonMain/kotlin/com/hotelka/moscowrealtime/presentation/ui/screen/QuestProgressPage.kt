package com.hotelka.moscowrealtime.presentation.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.hotelka.moscowrealtime.presentation.ui.Content.QuestProgressPageContent
import com.hotelka.moscowrealtime.presentation.viewmodel.QuestProgressGraphViewModel

@Composable
fun QuestProgressPage(
    questProgressGraphViewModel: QuestProgressGraphViewModel
) {
    val questProgressGraphState by questProgressGraphViewModel.questProgressGraphState.collectAsState()

    Box {
        QuestProgressPageContent(
            questProgressGraphUiModel = questProgressGraphState,
            onEvent = questProgressGraphViewModel::onEvent
        )
    }
}