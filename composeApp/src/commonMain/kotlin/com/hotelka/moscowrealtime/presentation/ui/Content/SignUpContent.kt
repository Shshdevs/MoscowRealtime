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
import com.hotelka.moscowrealtime.presentation.ui.Content.alerts.LoadingAlert
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.GoogleAuthButton
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.SignUpForm
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.SignUpHeader
import com.hotelka.moscowrealtime.presentation.ui.Content.items.OrSignInWithItem
import com.hotelka.moscowrealtime.presentation.ui.theme.darkBlue
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.authenticating
import org.jetbrains.compose.resources.stringResource

@Composable
fun SignUpContent(
    showLoad: Boolean,
    onEvent: (AuthGraphEvents) -> Unit
) {
    val focusRequester = LocalFocusManager.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }


    Box(
        Modifier
            .fillMaxSize()
            .background(darkBlue)
    ) {
        LazyColumn {
            item("header") {
                SignUpHeader { onEvent(AuthGraphEvents.OnGoBack) }
            }
            item("form") {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(30.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    SignUpForm(
                        focusRequester = focusRequester,
                        username = username,
                        onUsernameChange = { username = it },
                        name = name,
                        onNameChange = { name = it },
                        email = email,
                        onEmailChange = { email = it },
                        password = password,
                        onPasswordChange = { password = it },
                        confirmPassword = confirmPassword,
                        onConfirmPasswordChange = { confirmPassword = it },
                        onSignUp = {
                            onEvent(
                                AuthGraphEvents.OnSignUp(
                                    email,
                                    password,
                                    name,
                                    username
                                )
                            )
                        }
                    )

                    OrSignInWithItem()

                    GoogleAuthButton {
                        focusRequester.clearFocus()
                        onEvent(AuthGraphEvents.GoogleSignIn)
                    }
                }
            }
        }
        if (showLoad) LoadingAlert(stringResource(Res.string.authenticating))
    }
}