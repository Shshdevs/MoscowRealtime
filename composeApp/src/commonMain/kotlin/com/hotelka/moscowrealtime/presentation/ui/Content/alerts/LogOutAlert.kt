package com.hotelka.moscowrealtime.presentation.ui.Content.alerts

import androidx.compose.runtime.Composable
import com.hotelka.moscowrealtime.presentation.ui.Custom.CriticalAlert
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.alert_log_out
import moscowrealtime.composeapp.generated.resources.alert_log_out_submit
import moscowrealtime.composeapp.generated.resources.alert_log_out_you_sure
import moscowrealtime.composeapp.generated.resources.cancel
import org.jetbrains.compose.resources.stringResource

@Composable
fun LogOutAlert(onLogOut:() -> Unit, onDismiss: () -> Unit){
    CriticalAlert(
        titleText = stringResource(Res.string.alert_log_out),
        secondaryText = stringResource(Res.string.alert_log_out_you_sure),
        cancelText = stringResource(Res.string.cancel),
        submitText = stringResource(Res.string.alert_log_out_submit),
        onSubmit = onLogOut,
        onDismissRequest = onDismiss,
        timerOn = false
    )
}