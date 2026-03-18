package com.hotelka.moscowrealtime.presentation.ui.Content.alerts

import androidx.compose.runtime.Composable
import com.hotelka.moscowrealtime.presentation.ui.Custom.CriticalAlert
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.cancel
import moscowrealtime.composeapp.generated.resources.close
import moscowrealtime.composeapp.generated.resources.error_creating_group
import moscowrealtime.composeapp.generated.resources.error_creating_group_description
import org.jetbrains.compose.resources.stringResource

@Composable
fun ErrorCreatingGroupAlert(onDismiss:() -> Unit) {
    CriticalAlert(
        titleText = stringResource(Res.string.error_creating_group),
        secondaryText = stringResource(Res.string.error_creating_group_description),
        cancelText = stringResource(Res.string.cancel),
        submitText = stringResource(Res.string.close),
        onSubmit = onDismiss,
        onDismissRequest = onDismiss,
        timerOn = false,
        middleContent = null
    )
}