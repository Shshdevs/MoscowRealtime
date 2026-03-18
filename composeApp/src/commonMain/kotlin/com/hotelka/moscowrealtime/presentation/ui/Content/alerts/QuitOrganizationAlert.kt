package com.hotelka.moscowrealtime.presentation.ui.Content.alerts

import androidx.compose.runtime.Composable
import com.hotelka.moscowrealtime.presentation.ui.Custom.CriticalAlert
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.cancel
import moscowrealtime.composeapp.generated.resources.quit_organization
import moscowrealtime.composeapp.generated.resources.quit_organization_alert
import moscowrealtime.composeapp.generated.resources.submit_quit_organization
import org.jetbrains.compose.resources.stringResource

@Composable
fun QuitOrganizationAlert(
    onSubmit:() -> Unit,
    onDismiss:() -> Unit
) {
    CriticalAlert(
        titleText = stringResource(Res.string.quit_organization),
        secondaryText = stringResource(Res.string.quit_organization_alert),
        cancelText = stringResource(Res.string.cancel),
        submitText = stringResource(Res.string.submit_quit_organization),
        onSubmit = onSubmit,
        onDismissRequest = onDismiss,
        timerOn = true,
    )
}