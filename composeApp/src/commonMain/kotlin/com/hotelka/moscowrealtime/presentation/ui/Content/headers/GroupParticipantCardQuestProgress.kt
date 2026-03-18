package com.hotelka.moscowrealtime.presentation.ui.Content.headers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.current_quest
import org.jetbrains.compose.resources.stringResource
import kotlin.math.roundToInt

@Composable
fun GroupParticipantCardQuestProgress(
    questProgress: Float?,
    quest: Quest,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier.wrapContentHeight().padding(horizontal = 10.dp).padding(bottom = 10.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Column(Modifier.weight(1f)) {
                Text(
                    text = quest.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = titleTextColor,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    text = stringResource(Res.string.current_quest),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    color = secondaryTextColor
                )
            }
            Spacer(Modifier.width(10.dp))
            Badge(
                Modifier.clip(CircleShape),
                contentColor = background,
                containerColor = blue
            ) {
                Text(
                    "${(((questProgress ?: 0f) * 100)).roundToInt()}%"
                )

            }
        }
        LinearProgressIndicator(
            { 0f },
            modifier = Modifier.fillMaxWidth(),
            gapSize = 0.dp,
            drawStopIndicator = {},
            color = blue,
            trackColor = blue.copy(0.5f),
        )
    }
}
