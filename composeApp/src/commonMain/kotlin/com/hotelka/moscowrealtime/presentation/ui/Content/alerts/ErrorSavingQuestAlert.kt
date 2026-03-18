package com.hotelka.moscowrealtime.presentation.ui.Content.alerts

import androidx.compose.runtime.Composable
import com.hotelka.moscowrealtime.presentation.ui.Custom.CriticalAlert
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.cancel
import moscowrealtime.composeapp.generated.resources.close
import moscowrealtime.composeapp.generated.resources.creating_quest_error
import moscowrealtime.composeapp.generated.resources.creating_quest_error_description
import org.jetbrains.compose.resources.stringResource

@Composable
fun ErrorSavingQuestAlert(onDismiss:() -> Unit){
    CriticalAlert(
        titleText = stringResource(Res.string.creating_quest_error),
        secondaryText = stringResource(Res.string.creating_quest_error_description),
        cancelText = stringResource(Res.string.cancel),
        submitText = stringResource(Res.string.close),
        onSubmit = onDismiss,
        onDismissRequest = onDismiss,
        timerOn = false,
        middleContent = null
    )
}