package com.hotelka.moscowrealtime.presentation.ui.Content.alerts

import androidx.compose.runtime.Composable
import com.hotelka.moscowrealtime.presentation.ui.Custom.CriticalAlert
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.cancel
import moscowrealtime.composeapp.generated.resources.delete_quest_alert
import moscowrealtime.composeapp.generated.resources.delete_quest_alert_description
import moscowrealtime.composeapp.generated.resources.delete_quest_alert_submit
import org.jetbrains.compose.resources.stringResource

@Composable
fun DeleteQuestAlert(
    onSubmit: () -> Unit,
    onDismiss:() -> Unit
) {
    CriticalAlert(
        titleText = stringResource(Res.string.delete_quest_alert),
        secondaryText = stringResource(Res.string.delete_quest_alert_description),
        cancelText = stringResource(Res.string.cancel),
        submitText = stringResource(Res.string.delete_quest_alert_submit),
        onSubmit = onSubmit,
        onDismissRequest = onDismiss,
        timerOn = false,
        middleContent = null
    )
}