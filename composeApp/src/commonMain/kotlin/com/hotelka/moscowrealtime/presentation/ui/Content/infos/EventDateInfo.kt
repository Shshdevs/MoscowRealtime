package com.hotelka.moscowrealtime.presentation.ui.Content.infos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.api.EventDate
import com.hotelka.moscowrealtime.presentation.extensions.getTheNearestDate
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import com.hotelka.moscowrealtime.presentation.utils.DateFormatters.toDate
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.calendar
import moscowrealtime.composeapp.generated.resources.unspecified
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun EventDateInfo(dates: List<EventDate>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Icon(
            painterResource(Res.drawable.calendar),
            contentDescription = "Dates",
            tint = blue
        )
        val nearestDate = dates.getTheNearestDate()
        Text(
            text = if (nearestDate != null) {
                if (nearestDate.start != nearestDate.end) {
                    "${nearestDate.start.toDate(true)} — ${
                        nearestDate.end.toDate(
                            true
                        )
                    }"
                } else {
                    nearestDate.start.toDate(true)
                }
            } else stringResource(Res.string.unspecified),
            style = TextStyle.Default.copy(
                color = titleTextColor,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
            ),
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
    }
}