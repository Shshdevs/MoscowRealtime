package com.hotelka.moscowrealtime.presentation.ui.Content.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.arrow_previous
import org.jetbrains.compose.resources.painterResource

@Composable
fun BackButton(onClick:() -> Unit){
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .padding(20.dp)
            .padding(top = 40.dp)
            .size(40.dp)
            .background(
                brush = Brush.radialGradient(
                    listOf(
                        Black.copy(0.2f),
                        Transparent,
                    )
                ),
                CircleShape
            ),
        shape = CircleShape
    ) {
        Icon(
            painterResource(Res.drawable.arrow_previous),
            contentDescription = "Go back",
            tint = background,
            modifier = Modifier.fillMaxSize()
        )
    }
}