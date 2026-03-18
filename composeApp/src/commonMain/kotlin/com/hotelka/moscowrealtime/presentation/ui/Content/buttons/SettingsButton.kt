package com.hotelka.moscowrealtime.presentation.ui.Content.buttons

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.settings
import org.jetbrains.compose.resources.painterResource

@Composable
fun SettingsButton(modifier:Modifier, onOpenSettings: () -> Unit) {
    IconButton(
        onClick = onOpenSettings,
        modifier = modifier
            .padding(20.dp)
            .padding(top = 20.dp),
    ) {
        Icon(
            painterResource(Res.drawable.settings),
            "Edit profile settings",
            tint = background
        )
    }
}