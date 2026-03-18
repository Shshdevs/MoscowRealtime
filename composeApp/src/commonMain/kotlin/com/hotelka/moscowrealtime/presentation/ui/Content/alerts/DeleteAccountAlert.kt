package com.hotelka.moscowrealtime.presentation.ui.Content.alerts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.hotelka.moscowrealtime.presentation.ui.Custom.CriticalAlert
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.are_you_sure_delete_account
import moscowrealtime.composeapp.generated.resources.cancel
import moscowrealtime.composeapp.generated.resources.delete_account
import moscowrealtime.composeapp.generated.resources.deleting_account_alert
import moscowrealtime.composeapp.generated.resources.no
import moscowrealtime.composeapp.generated.resources.submit_deleting_account
import moscowrealtime.composeapp.generated.resources.undone_action
import org.jetbrains.compose.resources.stringResource

@Composable
fun DeleteAccountAlert(onSubmit: () -> Unit, onDismissRequest: () -> Unit) {
    var showYouSure by remember { mutableStateOf(false) }
    if (!showYouSure) {
        CriticalAlert(
            titleText = stringResource(Res.string.delete_account),
            secondaryText = stringResource(Res.string.deleting_account_alert),
            cancelText = stringResource(Res.string.no),
            submitText = stringResource(Res.string.submit_deleting_account),
            {
                showYouSure = true
            },
            onDismissRequest
        )
    } else{
        CriticalAlert(
            titleText = stringResource(Res.string.undone_action),
            secondaryText = stringResource(Res.string.are_you_sure_delete_account),
            cancelText = stringResource(Res.string.cancel),
            submitText = stringResource(Res.string.submit_deleting_account),
            {onSubmit(); onDismissRequest()},
            onDismissRequest
        )
    }
}