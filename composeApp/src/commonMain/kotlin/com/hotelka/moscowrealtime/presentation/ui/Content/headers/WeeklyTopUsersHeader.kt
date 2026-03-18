package com.hotelka.moscowrealtime.presentation.ui.Content.headers

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.state.TopWeeklyUsersUiState
import com.hotelka.moscowrealtime.presentation.ui.Content.pills.PillContainer
import com.hotelka.moscowrealtime.presentation.ui.Custom.LoadingText
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.gold
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import com.hotelka.moscowrealtime.presentation.utils.currentWeekOfYear
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.medal
import moscowrealtime.composeapp.generated.resources.week
import moscowrealtime.composeapp.generated.resources.weekly_top_users
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun WeeklyTopUsersHeader(topWeeklyUsersUiState: TopWeeklyUsersUiState) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painterResource(Res.drawable.medal),
            "Score",
            Modifier.size(30.dp),
            tint = gold
        )
        Spacer(Modifier.width(10.dp))

        Text(
            stringResource(Res.string.weekly_top_users),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = titleTextColor.copy(0.8f),
            modifier = Modifier.weight(1f)
        )

        PillContainer(Modifier, blue) {
            when (topWeeklyUsersUiState) {
                is TopWeeklyUsersUiState.Error -> {
                    Text(
                        stringResource(Res.string.week),
                        fontSize = 10.sp,
                        color = background
                    )
                }
                TopWeeklyUsersUiState.Loading -> {
                    LoadingText(
                        modifier = Modifier.width(40.dp),
                        textSize = 10.sp,
                        color = background.copy(0.2f)
                    )
                }

                TopWeeklyUsersUiState.None -> {}
                is TopWeeklyUsersUiState.Success -> {
                    Text(
                        stringResource(Res.string.week) + currentWeekOfYear().toString(),
                        fontSize = 10.sp,
                        color = background,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

    }
}