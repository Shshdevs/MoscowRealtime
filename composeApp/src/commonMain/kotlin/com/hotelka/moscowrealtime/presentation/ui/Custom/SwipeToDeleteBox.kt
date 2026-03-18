package com.hotelka.moscowrealtime.presentation.ui.Custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.red
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.trash
import org.jetbrains.compose.resources.painterResource

@Composable
fun SwipeToDeleteBox(key: String, onDelete:(SwipeToDismissBoxValue) -> Unit, content: @Composable RowScope.() -> Unit) {
    val swipeToDismissBoxState = remember(key) {
        SwipeToDismissBoxState(
            initialValue = SwipeToDismissBoxValue.Settled,
            positionalThreshold = { totalDistance -> totalDistance / 2 },
        )
    }
    SwipeToDismissBox(
        modifier = Modifier.padding(10.dp).clip(RoundedCornerShape(15.dp)),
        state = swipeToDismissBoxState,
        backgroundContent = {
            Box(
                Modifier.fillMaxSize().background(red.copy(0.4f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painterResource(Res.drawable.trash),
                    tint = background,
                    contentDescription = "Delete"
                )
            }
        },
        onDismiss = onDelete,
        content = content
    )
}