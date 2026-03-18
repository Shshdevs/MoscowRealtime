package com.hotelka.moscowrealtime.presentation.ui.Custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

@Composable
fun LoadingText(
    modifier: Modifier,
    textSize: TextUnit,
    color: Color,
) {
    Text(
        "",
        fontSize = textSize,
        modifier = modifier.background(color.copy(0.4f))
            .shimmerLoading(500, color.copy(0.5f))
    )
}