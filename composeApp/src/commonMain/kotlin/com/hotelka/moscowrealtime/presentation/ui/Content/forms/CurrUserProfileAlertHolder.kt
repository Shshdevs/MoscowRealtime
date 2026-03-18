package com.hotelka.moscowrealtime.presentation.ui.Content.forms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.hotelka.moscowrealtime.presentation.ui.Content.alerts.AcceptQuestInvitationAlert
import com.hotelka.moscowrealtime.presentation.ui.Content.alerts.ChangeOrganizationAlert
import com.hotelka.moscowrealtime.presentation.ui.Content.alerts.ChangePasswordAlert
import com.hotelka.moscowrealtime.presentation.ui.Content.alerts.CreateOrganizationAlert
import com.hotelka.moscowrealtime.presentation.ui.Content.alerts.DeleteAccountAlert
import com.hotelka.moscowrealtime.presentation.ui.Content.alerts.LogOutAlert

@Composable
fun CurrUserProfileAlertHolder(
    createOrganization: Boolean,
    deleteAccountAlert: Boolean,
    changePassword: Boolean,
    logOut: Boolean,
    changeOrg: Boolean,
    acceptQuestInvitationAlert: Boolean,
    email: String,
    onDeleteAccount:() -> Unit,
    onChangePassword: (String)-> Unit,
    onCreateOrganization:(String) -> Unit,
    onRestartProgress:() -> Unit,
    onLogOut:() -> Unit,
    onClose: () -> Unit,
    onChangeOrg:() -> Unit
) {
    if (acceptQuestInvitationAlert){
        AcceptQuestInvitationAlert(
            onSubmit = onRestartProgress,
            onDismiss = onClose
        )
    }
    if (createOrganization){
        CreateOrganizationAlert(
            onDismissDialog = onClose,
            onCreateOrganization = onCreateOrganization
        )
    }
    if (deleteAccountAlert) {
        DeleteAccountAlert(
            {
                onDeleteAccount()
            },
            onClose
        )
    }
    if (changePassword) {
        var email by remember { mutableStateOf(email) }
        ChangePasswordAlert(
            email = email,
            onEmailChange = { email = it },
            onSubmit = { onChangePassword(email) },
            emailChangeAvailable = false,
            onDismiss = onClose
        )
    }

    if (logOut) {
        LogOutAlert(
            onLogOut = onLogOut,
            onDismiss = onClose
        )
    }

    if (changeOrg){
        ChangeOrganizationAlert(
            onSubmit = onChangeOrg,
            onDismiss = onClose
        )
    }
}