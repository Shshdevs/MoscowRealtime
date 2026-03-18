package com.hotelka.moscowrealtime.presentation.ui.Content.buttons

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource


@Composable
fun MenuRowCamButton(
    modifier: Modifier,
    scannerActive: Boolean,
    isOnScannerRoute: Boolean,
    icon: DrawableResource,
    onClick: () -> Unit
) {
    val scale = animateFloatAsState(
        targetValue = when {
            scannerActive && isOnScannerRoute -> 1f
            !scannerActive && !isOnScannerRoute -> 1f
            else -> 0f
        },
        animationSpec = tween(700)
    )

    val offset = animateFloatAsState(
        targetValue = when {
            scannerActive && isOnScannerRoute -> -50f
            !scannerActive && !isOnScannerRoute -> 50f
            else -> 0f
        },
        animationSpec = tween(700)
    )


    FloatingActionButton(
        onClick = onClick,
        shape = CircleShape,
        modifier = modifier
            .graphicsLayer {
                translationY = offset.value
                scaleX = scale.value
                scaleY = scale.value
            }
            .width(80.dp)
            .border(3.dp, blue, CircleShape)
            .padding(5.dp),
        containerColor = blue,
        contentColor = background,
    ) {
        Icon(
            painterResource(icon),
            contentDescription = "CameraScan"
        )
    }
}