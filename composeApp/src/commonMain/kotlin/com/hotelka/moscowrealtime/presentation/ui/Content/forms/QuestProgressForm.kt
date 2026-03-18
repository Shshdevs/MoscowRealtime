package com.hotelka.moscowrealtime.presentation.ui.Content.forms

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.domain.model.detailed.DiscoverDetailed
import com.hotelka.moscowrealtime.presentation.state.QuestProgressUiState
import com.hotelka.moscowrealtime.presentation.ui.Content.infos.QuestProgressRouteInfo
import com.hotelka.moscowrealtime.presentation.utils.filterSuccessfulDiscovers
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.error_loading_curr_user_quest_progress
import moscowrealtime.composeapp.generated.resources.loading_curr_user_quest_progress
import org.jetbrains.compose.resources.stringResource

@Composable
fun QuestProgressForm(
    questProgress: QuestProgressUiState,
    onReachedLocationClicked: (Location, DiscoverDetailed) -> Unit,
    onCurrentLocationClicked: (Location) -> Unit,
    onRetryLoad: () -> Unit,
) {
    when (questProgress) {
        is QuestProgressUiState.Success -> {
            val successfulDiscovers by remember {
                derivedStateOf {
                    questProgress.questProgressDetailed.discovers.filterSuccessfulDiscovers(
                        questProgress.questProgressDetailed.questProgress
                    )
                }
            }
            QuestProgressRouteInfo(
                locations = questProgress.questProgressDetailed.locations,
                successfulDiscovers = successfulDiscovers,
                onReachedLocationClicked = onReachedLocationClicked,
                onCurrentLocationClicked = onCurrentLocationClicked
            )
        }

        is QuestProgressUiState.Error -> {
            ErrorForm(
                Modifier.Companion.fillMaxSize(),
                errorDescription = Res.string.error_loading_curr_user_quest_progress,
                onRetry = onRetryLoad
            )
        }

        else -> {
            LoadingForm(
                Modifier.Companion.fillMaxSize(),
                stringResource(Res.string.loading_curr_user_quest_progress)
            )
        }
    }

}