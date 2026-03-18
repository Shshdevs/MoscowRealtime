package com.hotelka.moscowrealtime.presentation.ui.Content.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.api.DetailedEvent
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.DefaultAppCard
import com.hotelka.moscowrealtime.presentation.ui.Content.covers.MiniCardItemCover
import com.hotelka.moscowrealtime.presentation.ui.Content.infos.MiniEventInfo
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.location
import moscowrealtime.composeapp.generated.resources.not_found
import moscowrealtime.composeapp.generated.resources.online
import moscowrealtime.composeapp.generated.resources.subway
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun MiniEventItem(modifier: Modifier, event: DetailedEvent, onClick: () -> Unit) {
    DefaultAppCard(
        modifier,
        20.dp,
        onClick = onClick
    ) {
        Row(Modifier.padding(20.dp)) {
            MiniCardItemCover(url = event.images[0].image, Res.drawable.not_found)
            Column(
                Modifier
                    .padding(start = 10.dp)
                    .height(75.dp),
            ) {
                Text(
                    text = event.shortTitle.replaceFirstChar { it.uppercaseChar() },
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Companion.SemiBold,
                    color = titleTextColor,
                    maxLines = 1,
                    overflow = TextOverflow.Companion.Ellipsis
                )
                Spacer(Modifier.weight(1f))
                event.detailedPlace?.title?.let { MiniEventInfo(Res.drawable.location, it) }
                Spacer(Modifier.height(5.dp))
                if (event.detailedPlace?.subway?.isNotEmpty() == true) {
                    MiniEventInfo(Res.drawable.subway, event.detailedPlace.subway)
                }
            }
        }
    }
}