package com.hotelka.moscowrealtime.presentation.ui.Content.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.state.TopWeeklyUsersUiState
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.WeeklyTopUsersHeader
import com.hotelka.moscowrealtime.presentation.ui.Content.items.WeeklyTopUserItem
import com.hotelka.moscowrealtime.presentation.ui.Custom.shimmerLoading
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.no_week_top
import moscowrealtime.composeapp.generated.resources.something_went_wrong
import org.jetbrains.compose.resources.stringResource

@Composable
fun WeeklyTopUsersCard(modifier: Modifier, topWeeklyUsersUiState: TopWeeklyUsersUiState) {
    DefaultAppCard(
        modifier = modifier,
        onClick = null
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            WeeklyTopUsersHeader(topWeeklyUsersUiState)
            Spacer(Modifier.height(15.dp))

            when (topWeeklyUsersUiState) {
                is TopWeeklyUsersUiState.Error -> {
                    Text(
                        stringResource(Res.string.something_went_wrong),
                        fontSize = 14.sp,
                        color = background
                    )
                }

                TopWeeklyUsersUiState.Loading -> {
                    Box(
                        Modifier.fillMaxSize().clip(RoundedCornerShape(10.dp)).shimmerLoading(
                            500,
                            secondaryTextColor.copy(0.2f)
                        )
                    )
                }

                TopWeeklyUsersUiState.None -> {}
                is TopWeeklyUsersUiState.Success -> {
                    if (topWeeklyUsersUiState.uiModel.topUsers.isEmpty()){
                        Text(
                            stringResource(Res.string.no_week_top),
                            fontSize = 16.sp,
                            color = secondaryTextColor
                        )
                    } else {
                        topWeeklyUsersUiState.uiModel.topUsers.take(3)
                            .forEachIndexed { index, topUser ->
                                WeeklyTopUserItem(
                                    modifier = Modifier,
                                    topWeeklyUser = topUser,
                                    place = index + 1
                                )
                            }
                    }
                }
            }
        }
    }
}