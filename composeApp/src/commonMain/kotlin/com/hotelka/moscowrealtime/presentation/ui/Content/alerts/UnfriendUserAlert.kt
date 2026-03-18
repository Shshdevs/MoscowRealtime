package com.hotelka.moscowrealtime.presentation.ui.Content.alerts

import androidx.compose.runtime.Composable
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.presentation.ui.Custom.NeutralAlert
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.alert_delete_friend
import moscowrealtime.composeapp.generated.resources.alert_delete_friend_sub
import moscowrealtime.composeapp.generated.resources.alert_delete_friend_submit
import moscowrealtime.composeapp.generated.resources.alert_delete_friend_up
import moscowrealtime.composeapp.generated.resources.cancel
import org.jetbrains.compose.resources.stringResource

@Composable
fun UnfriendUserAlert(user: User, onSubmit:() -> Unit, onDismiss: () -> Unit){
    NeutralAlert(
        titleText = stringResource(Res.string.alert_delete_friend),
        secondaryText = stringResource(Res.string.alert_delete_friend_sub) + "@${user.username} " + stringResource(Res.string.alert_delete_friend_up),
        cancelText = stringResource(Res.string.cancel),
        submitText = stringResource(Res.string.alert_delete_friend_submit),
        onSubmit = onSubmit,
        onDismissRequest = onDismiss,
    )
}