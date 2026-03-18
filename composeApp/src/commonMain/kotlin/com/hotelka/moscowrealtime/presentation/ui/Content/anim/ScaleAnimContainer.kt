package com.hotelka.moscowrealtime.presentation.ui.Content.anim

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ScaleAnimContainer(modifier: Modifier = Modifier, visible:Boolean, content: @Composable AnimatedVisibilityScope.() -> Unit) {
    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = slideInVertically { it / 2 } + scaleIn(),
        exit = slideOutVertically { it / 2 } + scaleOut(),
        content = content
    )
}