package com.hotelka.moscowrealtime.presentation.ui.Content.headers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.completed
import moscowrealtime.composeapp.generated.resources.in_progress
import moscowrealtime.composeapp.generated.resources.incoming
import org.jetbrains.compose.resources.stringResource

@Composable
fun QuestLocationItemHeader(modifier: Modifier, location: Location, completed: Boolean, current: Boolean) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        Text(
            if (!completed) stringResource(Res.string.incoming) else location.label,
            color = titleTextColor.copy(0.8f),
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            stringResource(if (completed) Res.string.completed else if (current) Res.string.in_progress else Res.string.incoming),
            color = titleTextColor.copy(0.8f),
            fontSize = 12.sp
        )
    }
}