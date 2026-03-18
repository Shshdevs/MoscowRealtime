package com.hotelka.moscowrealtime.presentation.ui.Content.alerts

import androidx.compose.runtime.Composable
import com.hotelka.moscowrealtime.presentation.ui.Custom.CriticalAlert
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.cancel
import moscowrealtime.composeapp.generated.resources.delete_group_alert
import moscowrealtime.composeapp.generated.resources.delete_group_alert_description_sub
import moscowrealtime.composeapp.generated.resources.delete_group_alert_description_sup
import moscowrealtime.composeapp.generated.resources.delete_group_alert_submit
import org.jetbrains.compose.resources.stringResource

@Composable
fun DeleteGroupAlert(groupName: String, onSubmit: () -> Unit, onDismiss: () -> Unit) {
    CriticalAlert(
        titleText = stringResource(Res.string.delete_group_alert),
        secondaryText = stringResource(Res.string.delete_group_alert_description_sup) + groupName + stringResource(Res.string.delete_group_alert_description_sub),
        cancelText = stringResource(Res.string.cancel),
        submitText = stringResource(Res.string.delete_group_alert_submit),
        onSubmit = onSubmit,
        onDismissRequest = onDismiss,
        timerOn = false,
        middleContent = null
    )
}