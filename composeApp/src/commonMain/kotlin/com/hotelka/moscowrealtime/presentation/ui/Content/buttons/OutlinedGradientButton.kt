package com.hotelka.moscowrealtime.presentation.ui.Content.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.purple

@Composable
fun OutlinedGradientButton(
    modifier: Modifier = Modifier,
    shape: Shape,
    onClick: () -> Unit = { },
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .border(
                2.dp,
                Brush.horizontalGradient(listOf(purple, blue.copy(0.8f))),
                shape
            )
            .background(background)
            .clip(shape)
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides TextStyle.Default.copy(
                brush = Brush.horizontalGradient(
                    listOf(purple, blue.copy(0.8f))
                )
            )
        ) {
            content()
        }

    }
}