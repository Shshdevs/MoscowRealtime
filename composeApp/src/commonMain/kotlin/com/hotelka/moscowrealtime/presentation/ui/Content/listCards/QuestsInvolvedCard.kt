package com.hotelka.moscowrealtime.presentation.ui.Content.listCards

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.presentation.ui.Content.frames.DismissableCardFrame
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.DismissableCardHeader
import com.hotelka.moscowrealtime.presentation.ui.Content.items.QuestItemCard
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.participant_in

@Composable
fun QuestsInvolvedCard(
    modifier: Modifier,
    quests: List<Quest>,
    onOpenQuest: (Quest) -> Unit,
    onDismiss: () -> Unit,
) {
    DismissableCardFrame(
        modifier = modifier,
        onDismissRequest = onDismiss,
        content = {
            DismissableCardHeader(Res.string.participant_in)
            Spacer(Modifier.height(20.dp))

            LazyColumn(Modifier.padding(horizontal = 10.dp)) {
                items(quests, key = { it.id }) { quest ->
                    QuestItemCard(
                        quest = quest,
                        onOpenQuest = { onOpenQuest(quest) }
                    )
                    Spacer(Modifier.height(10.dp))
                }
                item {
                    Spacer(Modifier.height(120.dp))
                }
            }
        }
    )
}