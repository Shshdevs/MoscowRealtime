package com.hotelka.moscowrealtime.presentation.ui.Content.alerts

import androidx.compose.runtime.Composable
import com.hotelka.moscowrealtime.presentation.ui.Custom.CriticalAlert
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.cancel
import moscowrealtime.composeapp.generated.resources.change_org_alert
import moscowrealtime.composeapp.generated.resources.change_org_alert_description
import moscowrealtime.composeapp.generated.resources.change_org_submit
import org.jetbrains.compose.resources.stringResource

@Composable
fun ChangeOrganizationAlert(
    onSubmit: () -> Unit,
    onDismiss: () -> Unit
){
    CriticalAlert(
        titleText = stringResource(Res.string.change_org_alert),
        secondaryText = stringResource(Res.string.change_org_alert_description),
        cancelText = stringResource(Res.string.cancel),
        submitText = stringResource(Res.string.change_org_submit),
        onSubmit = {onSubmit(); onDismiss()},
        onDismissRequest = onDismiss,
        timerOn = false,
    )
}