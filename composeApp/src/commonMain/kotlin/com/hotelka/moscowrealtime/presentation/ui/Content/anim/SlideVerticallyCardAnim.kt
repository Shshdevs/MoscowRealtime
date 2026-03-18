package com.hotelka.moscowrealtime.presentation.ui.Content.anim

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun SlideVerticallyCardAnim(modifier: Modifier = Modifier, visible: Boolean, content: @Composable AnimatedVisibilityScope.() -> Unit) {
    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = slideInVertically{it/2},
        exit = slideOutVertically{it/2},
        content = content
    )
}