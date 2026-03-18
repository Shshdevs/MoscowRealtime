package com.hotelka.moscowrealtime.presentation.ui.Content.alerts

import androidx.compose.runtime.Composable
import com.hotelka.moscowrealtime.presentation.ui.Custom.CriticalAlert
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.accept_quest_invitation_alert
import moscowrealtime.composeapp.generated.resources.accept_quest_invitation_alert_description
import moscowrealtime.composeapp.generated.resources.accept_quest_invitation_alert_submit
import moscowrealtime.composeapp.generated.resources.cancel
import org.jetbrains.compose.resources.stringResource

@Composable
fun AcceptQuestInvitationAlert(
    onSubmit:() -> Unit,
    onDismiss: () -> Unit
) {
    CriticalAlert(
        titleText = stringResource(Res.string.accept_quest_invitation_alert),
        secondaryText = stringResource(Res.string.accept_quest_invitation_alert_description),
        cancelText = stringResource(Res.string.cancel),
        submitText = stringResource(Res.string.accept_quest_invitation_alert_submit),
        onSubmit = {onSubmit(); onDismiss()},
        onDismissRequest = onDismiss,
        timerOn = false,
        middleContent = null
    )
}