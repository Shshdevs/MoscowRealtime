package com.hotelka.moscowrealtime.presentation.ui.Content.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.theme.red
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.quit_organization
import org.jetbrains.compose.resources.stringResource

@Composable
fun QuitOrganizationButton(onQuitOrganization:() -> Unit) {
    OutlinedButton(
        onClick = onQuitOrganization,
        modifier = Modifier.fillMaxWidth(),
        shape = CircleShape,
        border = BorderStroke(2.dp, red),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = red
        ),
    ) {
        Text(stringResource(Res.string.quit_organization))
    }
}