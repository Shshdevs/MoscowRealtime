package com.hotelka.moscowrealtime.presentation.ui.Content.buttons

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.camera
import org.jetbrains.compose.resources.painterResource

@Composable
fun CameraButton(opOpenCamera:() -> Unit) {
    IconButton(
        onClick = opOpenCamera,
        modifier = Modifier.size(50.dp),
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = background,
            containerColor = blue
        ),
        shape = CircleShape
    ) {
        Icon(
            painter = painterResource(Res.drawable.camera),
            contentDescription = "User Location"
        )
    }
}