package com.hotelka.moscowrealtime.presentation.ui.Content.pills

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

@Composable
fun HazePillContainer(
    hazeState: HazeState?, modifier: Modifier = Modifier, content: @Composable (RowScope.() -> Unit)
) {
    Row(
        modifier.wrapContentHeight().clip(CircleShape)
            .then(
                if (hazeState != null) Modifier.hazeEffect(
                    hazeState,
                ) {
                    fallbackTint = HazeTint(blue)
                }
                else Modifier).padding(
                vertical = 10.dp, horizontal = 15.dp
            )) {
        content()
    }
}

@Composable
fun PillContainer(
    modifier: Modifier = Modifier,
    color: Color,
    onClick: (() -> Unit)? = null,
    paddingValues: PaddingValues = PaddingValues(vertical = 10.dp, horizontal = 15.dp),
    content: @Composable (RowScope.() -> Unit),
    ) {
    Row(
        modifier.wrapContentHeight().background(color, CircleShape).clip(CircleShape)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(paddingValues),
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}