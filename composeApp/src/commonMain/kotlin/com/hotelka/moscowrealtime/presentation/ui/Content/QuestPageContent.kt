package com.hotelka.moscowrealtime.presentation.ui.Content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.events.QuestPageEvents
import com.hotelka.moscowrealtime.presentation.model.QuestPageUiModel
import com.hotelka.moscowrealtime.presentation.state.QuestUiModelState
import com.hotelka.moscowrealtime.presentation.ui.Content.alerts.DeleteQuestAlert
import com.hotelka.moscowrealtime.presentation.ui.Content.anim.SlideVerticallyCardAnim
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.StartQuestCard
import com.hotelka.moscowrealtime.presentation.ui.Content.frames.CollapsableHeaderContainer
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.PageHeader
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import kotlin.math.roundToInt

@Composable
fun QuestPageContent(
    questPageUiModel: QuestPageUiModel,
    onEvent: (QuestPageEvents) -> Unit
) {
    Box(Modifier.Companion.fillMaxSize().background(background)) {
        CollapsableHeaderContainer(
            headerContent = { currentHeaderHeight ->
                val showTags = currentHeaderHeight.value.roundToInt() > 165
                when (questPageUiModel.questUiModelState) {
                    is QuestUiModelState.Success -> {
                        PageHeader(
                            cover = questPageUiModel.questUiModelState.questDetailed.quest.cover,
                            tags = questPageUiModel.questUiModelState.questDetailed.quest.tags.sortedBy { it.length },
                            showTags = showTags,
                            onNavigateBack = { onEvent(QuestPageEvents.OnNavigateBack) },
                            modifier = Modifier
                        )
                    }

                    else -> {
                        PageHeader(
                            cover = "",
                            tags = listOf(),
                            showTags = showTags,
                            onNavigateBack = { onEvent(QuestPageEvents.OnNavigateBack) },
                            modifier = Modifier
                        )
                    }
                }

            },
            content = {
                QuestPageInfoContent(
                    questPageUiModel = questPageUiModel,
                    onEvent = onEvent
                )
            }
        )
        if (questPageUiModel.showDeleteQuestAlert) {
            DeleteQuestAlert(
                onSubmit = { onEvent(QuestPageEvents.OnDeleteQuest) },
                onDismiss = { onEvent(QuestPageEvents.OnChangeShowDeleteQuestAlert) }
            )
        }
        SlideVerticallyCardAnim(Modifier, questPageUiModel.showFriendsCard) {
            if (questPageUiModel.questUiModelState is QuestUiModelState.Success) {
                StartQuestCard(
                    modifier = Modifier.Companion.padding(top = 40.dp).fillMaxSize(),
                    quest = questPageUiModel.questUiModelState.questDetailed.quest,
                    friends = questPageUiModel.searchedFriends,
                    onSearch = { onEvent(QuestPageEvents.OnSearchFriends(it)) },
                    onStart = { onEvent(QuestPageEvents.OnStartQuest(it)) },
                    onDismiss = { onEvent(QuestPageEvents.OnChangeShowStartQuestCard) }
                )
            }
        }
    }
}