package com.hotelka.moscowrealtime.presentation.ui.Content.alerts

import androidx.compose.runtime.Composable
import com.hotelka.moscowrealtime.presentation.ui.Custom.CriticalAlert
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.cancel
import moscowrealtime.composeapp.generated.resources.delete_participant_alert
import moscowrealtime.composeapp.generated.resources.delete_participant_alert_description
import moscowrealtime.composeapp.generated.resources.delete_participant_submit
import org.jetbrains.compose.resources.stringResource

@Composable
fun DeleteGroupParticipantAlert(
    userToDeleteName: String,
    onDelete: () -> Unit,
    onDismiss: () -> Unit
) {
    CriticalAlert(
        titleText = stringResource(Res.string.delete_participant_alert),
        secondaryText = stringResource(Res.string.delete_participant_alert_description) + userToDeleteName + "?",
        cancelText = stringResource(Res.string.cancel),
        submitText = stringResource(Res.string.delete_participant_submit),
        onSubmit = onDelete,
        onDismissRequest = onDismiss,
        timerOn = false,
    )
}