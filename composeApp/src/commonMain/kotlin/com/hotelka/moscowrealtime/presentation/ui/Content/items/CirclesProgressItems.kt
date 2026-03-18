package com.hotelka.moscowrealtime.presentation.ui.Content.items

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.theme.background

@Composable
fun CirclesProgressItem(
    modifier: Modifier,
    circleSize: Dp,
    maxProgress: Int,
    progress: Int = 0,

    ) {
    Row(modifier) {
        for (i in 1..maxProgress) {
            if (i-1 == progress) {
                Box(
                    Modifier
                        .padding(5.dp)
                        .scale(1.3f)
                        .size(circleSize)
                        .aspectRatio(1f)
                        .background(background, CircleShape)
                )
            } else {
                Box(
                    Modifier
                        .padding(5.dp)
                        .size(circleSize)
                        .aspectRatio(1f)
                        .border(2.dp, background, CircleShape)
                )
            }
        }
    }
}