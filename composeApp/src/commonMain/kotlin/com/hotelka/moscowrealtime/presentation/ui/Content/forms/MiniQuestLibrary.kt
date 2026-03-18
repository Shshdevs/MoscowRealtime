package com.hotelka.moscowrealtime.presentation.ui.Content.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.presentation.state.listState.QuestsListState
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.MiniQuestsLibraryHeader
import com.hotelka.moscowrealtime.presentation.ui.Content.items.QuestItemCard
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.error_loading_quests
import moscowrealtime.composeapp.generated.resources.loading_quests
import org.jetbrains.compose.resources.stringResource

@Composable
fun MiniQuestLibrary(
    questsListState: QuestsListState,
    onOpenQuest: (Quest) -> Unit,
    onNavigateLibrary: () -> Unit,
    onRetry: () -> Unit
) {
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(5.dp)) {
        MiniQuestsLibraryHeader(Modifier, onNavigateLibrary)
        Spacer(Modifier.height(10.dp))
        when (questsListState) {
            is QuestsListState.Error -> {
                ErrorForm(
                    modifier = Modifier.fillMaxWidth(),
                    errorDescription = Res.string.error_loading_quests,
                    onRetry = onRetry
                )
            }

            QuestsListState.Loading -> {
                LoadingForm(
                    modifier = Modifier.fillMaxWidth(),
                    loadingAction = stringResource(Res.string.loading_quests)
                )
            }

            is QuestsListState.Success -> {
                questsListState.quests.take(3).forEach { quest ->
                    QuestItemCard(
                        quest = quest,
                        onOpenQuest = { onOpenQuest(quest) }
                    )
                }

            }
        }
    }
}