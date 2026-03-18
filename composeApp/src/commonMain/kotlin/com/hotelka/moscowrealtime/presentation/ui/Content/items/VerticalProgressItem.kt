package com.hotelka.moscowrealtime.presentation.ui.Content.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.theme.blue

@Composable
fun VerticalProgressItem(
    theFirstOne: Boolean,
    completed: Boolean
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .fillMaxHeight()
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (theFirstOne) {
            Badge(
                modifier = Modifier.size(10.dp),
                containerColor = blue
            )
        }
        VerticalDivider(
            modifier = Modifier.weight(1f).padding(5.dp).clip(CircleShape),
            color = if (completed) blue else Gray,
            thickness = 5.dp
        )
        Badge(
            modifier = Modifier.size(10.dp),
            containerColor = if (completed) blue else Gray
        )
    }
}