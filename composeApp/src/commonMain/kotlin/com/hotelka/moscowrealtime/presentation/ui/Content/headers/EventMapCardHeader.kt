package com.hotelka.moscowrealtime.presentation.ui.Content.headers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.hotelka.moscowrealtime.presentation.state.EventState
import com.hotelka.moscowrealtime.presentation.ui.Content.covers.MiniCardItemCover
import com.hotelka.moscowrealtime.presentation.ui.Content.infos.EventPlaceInfo
import com.hotelka.moscowrealtime.presentation.ui.Custom.LoadingText
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.something_went_wrong
import moscowrealtime.composeapp.generated.resources.not_found
import org.jetbrains.compose.resources.stringResource

@Composable
fun EventMapCardHeader(eventState: EventState) {
    Row {
        MiniCardItemCover(
            when (eventState ){
                is EventState.Error -> null
                EventState.Loading -> ""
                EventState.None -> ""
                is EventState.Success -> eventState.event.images[0].image
            },
            Res.drawable.not_found
        )
        Column(
            Modifier
                .padding(start = 10.dp)
                .height(75.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            when (eventState) {
                is EventState.Error -> {
                    Text(
                        text = stringResource(Res.string.something_went_wrong),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = titleTextColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                EventState.Loading -> {
                    LoadingText(
                        modifier = Modifier.height(24.dp).width(100.dp),
                        textSize = 18.sp,
                        color = blue.copy(0.5f),
                    )
                }

                EventState.None -> {}
                is EventState.Success -> {
                    Text(
                        text = eventState.event.shortTitle,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = titleTextColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            if (eventState is EventState.Success) {
                EventPlaceInfo(eventState.event.detailedPlace, textSize = 12.sp)
            }

            LinearProgressIndicator(
                { 1f },
                gapSize = 0.dp,
                drawStopIndicator = {},
                color = blue,
                trackColor = blue.copy(0.5f),
            )
        }
    }
}