package com.hotelka.moscowrealtime.presentation.ui.Content.forms

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.events.QuestPageEvents
import com.hotelka.moscowrealtime.presentation.model.QuestPageUiModel
import com.hotelka.moscowrealtime.presentation.state.QuestUiModelState
import com.hotelka.moscowrealtime.presentation.ui.Content.alerts.DeleteQuestAlert
import com.hotelka.moscowrealtime.presentation.ui.Content.frames.TabbedPager
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.bookmark
import moscowrealtime.composeapp.generated.resources.info
import moscowrealtime.composeapp.generated.resources.location
import moscowrealtime.composeapp.generated.resources.locations

@Composable
fun QuestPageMainContent(
    questPageUiModel: QuestPageUiModel,
    onEvent: (QuestPageEvents) -> Unit
) {
    val tabs = remember {
        listOf(
            Pair(Res.drawable.bookmark, Res.string.info),
            Pair(Res.drawable.location, Res.string.locations)
        )
    }
    TabbedPager(
        tabs
    ) { page ->
        when (page) {
            0 -> {
                QuestPageHomeContent(
                    questPageUiModel = questPageUiModel,
                    onRetry = { onEvent(QuestPageEvents.OnReload) },
                    onContinueQuest = {
                        val questProgress =
                            (questPageUiModel.questUiModelState as QuestUiModelState.Success).questDetailed.questProgress
                        if (questProgress != null) {
                            onEvent(QuestPageEvents.OnContinueQuest(questProgress.id))
                        }
                    },
                    onStartQuest = { onEvent(QuestPageEvents.OnChangeShowStartQuestCard) },
                    onOpenAuthorProfile = { onEvent(QuestPageEvents.OnOpenAuthorProfile(it.id)) },
                )
            }


            1 -> {
                UserQuestProgressInfo(
                    questPageUiModel = questPageUiModel,
                    onQuestLocationItemClick = { location ->

                    }
                )
            }

        }
        Spacer(Modifier.height(50.dp))
    }

}