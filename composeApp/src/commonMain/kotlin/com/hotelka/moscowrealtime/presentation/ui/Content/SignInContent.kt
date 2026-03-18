package com.hotelka.moscowrealtime.presentation.ui.Content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.events.AuthGraphEvents
import com.hotelka.moscowrealtime.presentation.ui.Content.alerts.ChangePasswordAlert
import com.hotelka.moscowrealtime.presentation.ui.Content.alerts.LoadingAlert
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.GoogleAuthButton
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.SignInForm
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.SignInHeader
import com.hotelka.moscowrealtime.presentation.ui.Content.items.OrSignInWithItem
import com.hotelka.moscowrealtime.presentation.ui.theme.darkBlue
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.authenticating
import org.jetbrains.compose.resources.stringResource

@Composable
fun SignInContent(
    showLoad: Boolean,
    onEvent: (AuthGraphEvents) -> Unit
) {
    val focusRequester = LocalFocusManager.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var forgotPassword by remember { mutableStateOf(false) }
    Box(
        Modifier
            .fillMaxSize()
            .background(darkBlue)
    ) {
        LazyColumn {
            item("header") {
                SignInHeader { onEvent(AuthGraphEvents.OnGoBack) }
            }
            item("form") {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(30.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    SignInForm(
                        focusRequester = focusRequester,
                        email = email,
                        onEmailChange = { email = it },
                        password = password,
                        onPasswordChange = { password = it },
                        onForgotPassword = { forgotPassword = true },
                        onSignIn = { onEvent(AuthGraphEvents.OnSign(email, password)) }
                    )

                    OrSignInWithItem()

                    GoogleAuthButton {
                        focusRequester.clearFocus()
                        onEvent(AuthGraphEvents.GoogleSignIn)
                    }
                }
            }

        }
    }

    if (forgotPassword) {
        ChangePasswordAlert(
            email = email,
            onEmailChange = { email = it },
            emailChangeAvailable = true,
            onSubmit = { onEvent(AuthGraphEvents.OnResetPassword(email)) },
            onDismiss = { forgotPassword = false }
        )
    }
    if (showLoad) LoadingAlert(stringResource(Res.string.authenticating))

}