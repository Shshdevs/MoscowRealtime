package com.hotelka.moscowrealtime.presentation.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.hotelka.moscowrealtime.presentation.ui.Content.QuestMapContent
import com.hotelka.moscowrealtime.presentation.viewmodel.QuestProgressGraphViewModel

@Composable
fun CurrQuestInteractiveMapPage(
    questProgressGraphViewModel: QuestProgressGraphViewModel
) {
    val questProgressGraphState by questProgressGraphViewModel.questProgressGraphState.collectAsState()
    Box(Modifier.fillMaxSize()) {
        QuestMapContent(
            questProgressGraphUiModel = questProgressGraphState,
            onEvent = questProgressGraphViewModel::onEvent
        )
    }

}