package com.hotelka.moscowrealtime.presentation.ui.Content.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.domain.model.detailed.QuestProgressDetailed
import com.hotelka.moscowrealtime.presentation.ui.Content.covers.MiniCardItemCover
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.current_quest
import moscowrealtime.composeapp.generated.resources.question
import org.jetbrains.compose.resources.stringResource

@Composable
fun CurrentQuestCard(
    questProgressDetailed: QuestProgressDetailed,
    modifier: Modifier = Modifier, onClick: (Quest) -> Unit
) {
    DefaultAppCard(
        modifier.fillMaxWidth(),
        onClick = { onClick(questProgressDetailed.quest) },
        content = {
            Row(Modifier.padding(10.dp)) {
                MiniCardItemCover(questProgressDetailed.quest.cover, Res.drawable.question)
                Column(
                    Modifier
                        .padding(start = 10.dp)
                        .height(75.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = questProgressDetailed.quest.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = titleTextColor,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(Modifier.height(10.dp))
                        Text(
                            text = stringResource(Res.string.current_quest),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = secondaryTextColor
                        )
                    }
                    LinearProgressIndicator(
                        {
                            val successfulDiscovers =
                                questProgressDetailed.questProgress.results.filter { it.success }
                            successfulDiscovers.size.toFloat() / questProgressDetailed.locations.size.toFloat()
                        },
                        gapSize = 0.dp,
                        drawStopIndicator = {},
                        color = blue,
                        trackColor = blue.copy(0.5f),
                    )
                }
            }
        }
    )
}
