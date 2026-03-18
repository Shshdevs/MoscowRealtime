package com.hotelka.moscowrealtime.presentation.ui.Content.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.theme.purple
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.background

@Composable
fun GradientButton(
    colors: List<Color> = listOf(purple, blue),
    modifier: Modifier = Modifier,
    additionalModifier: Modifier = Modifier,
    shape: Shape,
    onClick: () -> Unit = { },
    enabled:Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .background(
                Brush.horizontalGradient(
                    colors
                ),
                shape
            )
            .clip(shape)
            .clickable(onClick = onClick, enabled = enabled)
            .then(additionalModifier)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,

        ) {
        CompositionLocalProvider(
            LocalContentColor provides background,

        ) {
            content()
        }
    }
}