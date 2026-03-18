package com.hotelka.moscowrealtime.presentation.ui.Content.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.domain.model.Discover
import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.Content.covers.QuestLocationItemCover
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.QuestLocationItemHeader

@Composable
fun QuestLocationItem(
    modifier: Modifier = Modifier,
    location: Location,
    discover: Discover?,
    theFirstOne: Boolean = false,
    completed: Boolean = true,
    current: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        modifier
            .height(70.dp)
            .background(background)
            .clickable(onClick = onClick),
    ) {
        QuestLocationItemCover(
            modifier = Modifier.padding(15.dp)
                .size(50.dp),
            discover = discover,
            completed = completed,
        )
        QuestLocationItemHeader(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxHeight()
                .weight(1f),
            location = location,
            completed = completed,
            current = current
        )

        VerticalProgressItem(
            theFirstOne = theFirstOne,
            completed = completed
        )
    }
}