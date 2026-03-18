package com.hotelka.moscowrealtime.presentation.ui.Content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.events.QuestProgressGraphEvents
import com.hotelka.moscowrealtime.presentation.model.QuestProgressGraphUiModel
import com.hotelka.moscowrealtime.presentation.state.QuestProgressRouteOpenedCardState
import com.hotelka.moscowrealtime.presentation.state.QuestProgressUiState
import com.hotelka.moscowrealtime.presentation.ui.Content.anim.SlideVerticallyCardAnim
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.QuitMapsButton
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.CurrentLocationCard
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.CurrentQuestCard
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.QuestIsDoneCard
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.ReachedLocationCard
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.RestartingQuestCard
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.QuestProgressForm
import com.hotelka.moscowrealtime.presentation.ui.screen.DiscoverPage
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.back_to_quests

@Composable
fun QuestProgressPageContent(
    questProgressGraphUiModel: QuestProgressGraphUiModel,
    onEvent: (QuestProgressGraphEvents) -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        Box(
            Modifier.fillMaxSize().background(background).padding(bottom = 30.dp)
                .padding(horizontal = 20.dp), contentAlignment = Alignment.BottomCenter
        ) {
            QuestProgressForm(
                questProgress = questProgressGraphUiModel.questProgressUiState,
                onReachedLocationClicked = { location, discover ->
                    onEvent(
                        QuestProgressGraphEvents.OnSetQuestProgressRouteOpenedCard(
                            QuestProgressRouteOpenedCardState.ReachedLocation(location, discover)
                        )
                    )
                }, onCurrentLocationClicked = { location ->
                    onEvent(
                        QuestProgressGraphEvents.OnSetQuestProgressRouteOpenedCard(
                            QuestProgressRouteOpenedCardState.CurrentLocation(location)
                        )
                    )
                }, onRetryLoad = { onEvent(QuestProgressGraphEvents.OnRetryLoadQuestProgress) }
            )

            when (questProgressGraphUiModel.questProgressRouteOpenedCardState) {
                QuestProgressRouteOpenedCardState.Quest -> {
                    SlideVerticallyCardAnim(
                        visible = true,
                        content = {
                            if (questProgressGraphUiModel.questProgressUiState is QuestProgressUiState.Success)
                                CurrentQuestCard(
                                    questProgressDetailed = questProgressGraphUiModel.questProgressUiState.questProgressDetailed,
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = {})
                        })
                }

                is QuestProgressRouteOpenedCardState.CurrentLocation -> {
                    SlideVerticallyCardAnim(
                        visible = true,
                        content = {
                            CurrentLocationCard(
                                modifier = Modifier.fillMaxWidth(),
                                location = questProgressGraphUiModel.questProgressRouteOpenedCardState.location,
                                navigateToInteractiveMaps = {
                                    onEvent(
                                        QuestProgressGraphEvents.OnNavigateMap(
                                            questProgressGraphUiModel.questProgressRouteOpenedCardState.location
                                        )
                                    )
                                })
                        })
                }

                is QuestProgressRouteOpenedCardState.ReachedLocation -> {
                    SlideVerticallyCardAnim(
                        visible = true,
                        content = {
                            ReachedLocationCard(
                                modifier = Modifier.fillMaxWidth(),
                                location = questProgressGraphUiModel.questProgressRouteOpenedCardState.location,
                                onViewDiscover = {
                                    onEvent(
                                        QuestProgressGraphEvents.OnShowDiscover(
                                            questProgressGraphUiModel.questProgressRouteOpenedCardState.discover
                                        )
                                    )
                                })
                        })
                }
            }


        }
        QuitMapsButton(Modifier.align(Alignment.TopStart), Res.string.back_to_quests) {
            onEvent(
                QuestProgressGraphEvents.OnNavigateBack
            )
        }

        SlideVerticallyCardAnim(
            visible = questProgressGraphUiModel.showDiscover != null, content = {
                questProgressGraphUiModel.showDiscover?.let {
                    DiscoverPage(
                        enableShowWhatAround = false,
                        discoverDetailed = it,
                        onDismiss = { onEvent(QuestProgressGraphEvents.OnShowDiscover(null)) })
                }
            }
        )
        if  (questProgressGraphUiModel.questProgressUiState is QuestProgressUiState.Success) {
            QuestIsDoneCard(
                visible = questProgressGraphUiModel.showQuestIsDone,
                quest = questProgressGraphUiModel.questProgressUiState.questProgressDetailed.quest,
                onDismiss = {onEvent(QuestProgressGraphEvents.OnChangeShowQuestDone)},
                onRestartQuest = {onEvent(QuestProgressGraphEvents.OnRestartQuest(questProgressGraphUiModel.questProgressUiState.questProgressDetailed))}
            )
        }

        RestartingQuestCard(questProgressGraphUiModel.showRestartingProgress)
    }
}