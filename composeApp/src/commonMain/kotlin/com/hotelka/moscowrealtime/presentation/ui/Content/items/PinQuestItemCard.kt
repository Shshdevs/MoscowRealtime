package com.hotelka.moscowrealtime.presentation.ui.Content.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.GradientButton
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.OutlinedGradientButton
import com.hotelka.moscowrealtime.presentation.ui.Content.frames.ExpandableCardFrame
import com.hotelka.moscowrealtime.presentation.ui.Content.infos.QuestItemContent
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.pin_quest
import moscowrealtime.composeapp.generated.resources.view_the_quest
import org.jetbrains.compose.resources.stringResource

@Composable
fun PinQuestItemCard(quest: Quest, pinQuest: (Quest) -> Unit, navigateQuestPage: (Quest) -> Unit) {
    ExpandableCardFrame(
        modifier = Modifier.fillMaxWidth(),
        showHorizDiver = false,
        topContent = {
            QuestItemContent(quest)
        },
        bottomContent = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedGradientButton(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    onClick = { navigateQuestPage(quest) },
                    content = {
                        Text(
                            stringResource(Res.string.view_the_quest),
                        )
                    }
                )
                GradientButton(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    onClick = { pinQuest(quest) },
                    content = {
                        Text(stringResource(Res.string.pin_quest))
                    }
                )
            }
        }
    )
}