package com.hotelka.moscowrealtime.presentation.ui.Content.headers

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.state.QuestProgressUiState
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.error_loading_curr_user_quest_progress
import moscowrealtime.composeapp.generated.resources.loading_curr_user_quest_progress
import moscowrealtime.composeapp.generated.resources.no_current_quest
import org.jetbrains.compose.resources.stringResource

@Composable
fun CurrentQuestHeader (userCurrentQuestProgressUiState: QuestProgressUiState){
    Text(
        when (userCurrentQuestProgressUiState) {
            is QuestProgressUiState.Error -> stringResource(Res.string.error_loading_curr_user_quest_progress)
            is QuestProgressUiState.Loading -> stringResource(Res.string.loading_curr_user_quest_progress)
            is QuestProgressUiState.None -> stringResource(Res.string.no_current_quest)
            is QuestProgressUiState.Success -> userCurrentQuestProgressUiState.questProgressDetailed.quest.title
        },
        color = titleTextColor.copy(0.5f),
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.fillMaxWidth()
    )
}