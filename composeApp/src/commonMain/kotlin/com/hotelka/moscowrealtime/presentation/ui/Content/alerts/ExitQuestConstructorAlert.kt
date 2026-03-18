package com.hotelka.moscowrealtime.presentation.ui.Content.alerts

import androidx.compose.runtime.Composable
import com.hotelka.moscowrealtime.presentation.ui.Custom.CriticalAlert
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.cancel
import moscowrealtime.composeapp.generated.resources.exit_quest_constructor_alert
import moscowrealtime.composeapp.generated.resources.exit_quest_constructor_alert_description
import org.jetbrains.compose.resources.stringResource

@Composable
fun ExitQuestConstructorAlert(
    onSubmit: () -> Unit,
    onDismiss: () -> Unit
) {
    CriticalAlert(
        titleText = stringResource(Res.string.exit_quest_constructor_alert),
        secondaryText = stringResource(Res.string.exit_quest_constructor_alert_description),
        cancelText = stringResource(Res.string.cancel),
        submitText = stringResource(Res.string.exit_quest_constructor_alert),
        onSubmit = onSubmit,
        onDismissRequest = onDismiss,
        timerOn = false,
    )
}