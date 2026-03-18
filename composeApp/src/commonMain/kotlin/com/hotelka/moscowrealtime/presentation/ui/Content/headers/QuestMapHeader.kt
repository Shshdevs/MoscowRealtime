package com.hotelka.moscowrealtime.presentation.ui.Content.headers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.state.QuestProgressUiState
import com.hotelka.moscowrealtime.presentation.ui.Content.covers.MiniCardItemCover
import com.hotelka.moscowrealtime.presentation.ui.Custom.LoadingText
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.current_quest
import moscowrealtime.composeapp.generated.resources.something_went_wrong
import moscowrealtime.composeapp.generated.resources.question
import org.jetbrains.compose.resources.stringResource

@Composable
fun QuestMapHeader(questProgressUiState: QuestProgressUiState, progress: Float) {
    Row {
        MiniCardItemCover(
            when (questProgressUiState){
                is QuestProgressUiState.Error -> null
                QuestProgressUiState.Loading -> ""
                QuestProgressUiState.None -> ""
                is QuestProgressUiState.Success -> questProgressUiState.questProgressDetailed.quest.cover
            },
            Res.drawable.question
        )
        Column(
            Modifier
                .padding(start = 10.dp)
                .height(75.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Column(
                    Modifier
                        .height(65.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    when (questProgressUiState) {
                        is QuestProgressUiState.Error -> {
                            Text(
                                text = stringResource(Res.string.something_went_wrong),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = titleTextColor,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        }

                        QuestProgressUiState.Loading -> {
                            LoadingText(
                                modifier = Modifier.height(24.dp).width(100.dp),
                                textSize = 18.sp,
                                color = blue.copy(0.5f),
                            )

                        }

                        QuestProgressUiState.None -> {}
                        is QuestProgressUiState.Success -> {
                            Text(
                                text = questProgressUiState.questProgressDetailed.quest.title,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = titleTextColor,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 2
                            )

                        }
                    }
                    Text(
                        text = stringResource(Res.string.current_quest),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = secondaryTextColor,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )
                }
            }
            Spacer(Modifier.weight(1f))
            LinearProgressIndicator(
                { progress },
                gapSize = 0.dp,
                drawStopIndicator = {},
                color = blue,
                trackColor = blue.copy(0.5f),
            )
        }
    }
}