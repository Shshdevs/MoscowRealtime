package com.hotelka.moscowrealtime.presentation.ui.Content.alerts

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.hotelka.moscowrealtime.presentation.extensions.validateEmail
import com.hotelka.moscowrealtime.presentation.ui.Custom.CustomTextField
import com.hotelka.moscowrealtime.presentation.ui.Custom.NeutralAlert
import com.hotelka.moscowrealtime.presentation.ui.theme.darkBlue
import kotlinx.coroutines.delay
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.cancel
import moscowrealtime.composeapp.generated.resources.change_password_title
import moscowrealtime.composeapp.generated.resources.close
import moscowrealtime.composeapp.generated.resources.confirm_email_address
import moscowrealtime.composeapp.generated.resources.email
import moscowrealtime.composeapp.generated.resources.invalid_email
import moscowrealtime.composeapp.generated.resources.link_is_sent
import moscowrealtime.composeapp.generated.resources.mail
import moscowrealtime.composeapp.generated.resources.new_link_is_available_in
import moscowrealtime.composeapp.generated.resources.send_password_link
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ChangePasswordAlert(
    email: String,
    onEmailChange: (String) -> Unit,
    emailChangeAvailable: Boolean = true,
    onSubmit: () -> Unit,
    onDismiss:() -> Unit
){
    val focusRequester = LocalFocusManager.current

    var emailError by remember { mutableStateOf(!email.validateEmail() || email.isEmpty()) }
    var timer by remember { mutableIntStateOf(30) }
    LaunchedEffect(timer == 0) {
        while (timer < 30) {
            timer++
            delay(1000)
        }
    }
    NeutralAlert(
        titleText = stringResource(Res.string.change_password_title),
        secondaryText = if (timer < 30) stringResource(Res.string.link_is_sent) else stringResource(
            Res.string.confirm_email_address
        ),
        cancelText = if (timer == 30) stringResource(Res.string.cancel) else stringResource(Res.string.close),
        submitText = if (timer == 30) stringResource(Res.string.send_password_link) else stringResource(
            Res.string.new_link_is_available_in
        ) + timer.toString(),
        onSubmit = {
            if (timer == 30 && !emailError) {
                timer = 0
                onSubmit()
                onDismiss()
            }
        },
        onDismissRequest = onDismiss,
        middleContent = {
            CustomTextField(
                email, { onEmailChange(it) },
                isError = emailError,
                leadingIcon = {
                    Icon(
                        painterResource(Res.drawable.mail),
                        contentDescription = "Email"
                    )
                },
                modifier = Modifier.Companion.fillMaxWidth(),
                label = {
                    Text(stringResource(Res.string.email))
                },
                errorDescription = {
                    Text(
                        stringResource(Res.string.invalid_email),
                        modifier = Modifier.Companion.fillMaxWidth(),
                        textAlign = TextAlign.Companion.End
                    )
                },
                keyboardOptions = KeyboardOptions.Companion.Default.copy(
                    imeAction = ImeAction.Companion.Done,
                    keyboardType = KeyboardType.Companion.Email
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusRequester.clearFocus() }
                ),
                readOnly = !emailChangeAvailable,
                color = darkBlue
            )
        }
    )
}