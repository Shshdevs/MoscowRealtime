package com.hotelka.moscowrealtime.presentation.ui.Content.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.api.DetailedEvent
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.DefaultAppCard
import com.hotelka.moscowrealtime.presentation.ui.Content.descriptions.EventDescription
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.CardHeader
import com.hotelka.moscowrealtime.presentation.ui.Content.infos.EventDateInfo
import com.hotelka.moscowrealtime.presentation.ui.Content.infos.EventPlaceInfo
import com.hotelka.moscowrealtime.presentation.ui.Content.infos.PriceInfo
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor

@Composable
fun EventItem(modifier: Modifier = Modifier.Companion, event: DetailedEvent, handleEventClick: () -> Unit) {

    DefaultAppCard(
        modifier = modifier,
        cornerRadius = 30.dp,
        onClick = handleEventClick
    ) {
        Column {
            CardHeader(
                event.images[0].image,
                event.tags.sortedBy { it.length }
            )
            Column(
                Modifier.Companion.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = event.title.replaceFirstChar { it.uppercaseChar() },
                    style = TextStyle.Default.copy(
                        color = titleTextColor,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                )
                Spacer(Modifier.Companion.height(0.dp))
                EventDescription(event.description)
                EventDateInfo(event.dates)
                PriceInfo(event.price)
                EventPlaceInfo(event.detailedPlace)
            }

        }
    }
}