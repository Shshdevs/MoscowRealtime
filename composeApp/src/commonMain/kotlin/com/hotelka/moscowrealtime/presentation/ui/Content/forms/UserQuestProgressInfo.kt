package com.hotelka.moscowrealtime.presentation.ui.Content.forms

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.presentation.model.QuestPageUiModel
import com.hotelka.moscowrealtime.presentation.state.QuestUiModelState
import com.hotelka.moscowrealtime.presentation.state.QuestProgressUiState
import com.hotelka.moscowrealtime.presentation.ui.Content.widgets.LottieImage
import com.hotelka.moscowrealtime.presentation.ui.Content.infos.UserCurrentQuestProgressInfoSuccess

@Composable
fun UserQuestProgressInfo(
    showRange: Boolean = false,
    questPageUiModel: QuestPageUiModel,
    onQuestLocationItemClick: (Location) -> Unit,
) {

    if (questPageUiModel.questUiModelState is QuestUiModelState.Loading) {
        LottieImage(
            Modifier.fillMaxWidth(),
            imageSize = 200.dp,
            jsonFileName = "loading_anim"
        )

    } else if (questPageUiModel.questUiModelState is QuestUiModelState.Success) {
        UserCurrentQuestProgressInfoSuccess(
            questLocations = questPageUiModel.questUiModelState.questDetailed.locations,
            questProgressDiscovers = questPageUiModel.questUiModelState.questDetailed.discovers,
            questProgress = questPageUiModel.questUiModelState.questDetailed.questProgress,
            showRange = showRange,
            onQuestLocationItemClick = onQuestLocationItemClick
        )
    }
}

@Composable
fun UserQuestProgressInfo(
    showRange: Boolean = false,
    questProgressUiState: QuestProgressUiState,
    onQuestLocationItemClick: (Location) -> Unit,
) {

    if (questProgressUiState is QuestProgressUiState.Loading) {
        LottieImage(
            Modifier.fillMaxWidth(),
            imageSize = 200.dp,
            jsonFileName = "loading_anim"
        )

    } else if (questProgressUiState is QuestProgressUiState.Success) {
        val questProgressUi = questProgressUiState.questProgressDetailed
        UserCurrentQuestProgressInfoSuccess(
            questLocations = questProgressUi.locations,
            questProgressDiscovers = questProgressUi.discovers,
            questProgress = questProgressUi.questProgress,
            showRange = showRange,
            onQuestLocationItemClick = onQuestLocationItemClick
        )
    }
}