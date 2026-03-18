package com.hotelka.moscowrealtime.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.hotelka.moscowrealtime.presentation.ui.Content.QuestConstructorContent
import com.hotelka.moscowrealtime.presentation.viewmodel.QuestConstructorPageViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun QuestConstructorScreen(questConstructorPageViewModel: QuestConstructorPageViewModel = koinViewModel()) {
    val questConstructorPageUiModel by questConstructorPageViewModel.questConstructorUiModel.collectAsState()
    QuestConstructorContent(questConstructorPageUiModel, questConstructorPageViewModel::onEvent)
}