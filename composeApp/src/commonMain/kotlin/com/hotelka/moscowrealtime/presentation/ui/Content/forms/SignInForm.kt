package com.hotelka.moscowrealtime.presentation.ui.Content.forms

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.extensions.filterPasswordChars
import com.hotelka.moscowrealtime.presentation.extensions.validateEmail
import com.hotelka.moscowrealtime.presentation.ui.Custom.CustomTextField
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.AuthButton
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.email
import moscowrealtime.composeapp.generated.resources.eye
import moscowrealtime.composeapp.generated.resources.eye_crossed
import moscowrealtime.composeapp.generated.resources.forgot_password
import moscowrealtime.composeapp.generated.resources.invalid_email
import moscowrealtime.composeapp.generated.resources.lock
import moscowrealtime.composeapp.generated.resources.mail
import moscowrealtime.composeapp.generated.resources.password
import moscowrealtime.composeapp.generated.resources.sign_in
import moscowrealtime.composeapp.generated.resources.weak_password
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SignInForm(
    focusRequester: FocusManager,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onForgotPassword: () -> Unit,
    onSignIn: () -> Unit
) {

    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    var showPassword by remember { mutableStateOf(false) }
    CustomTextField(
        email,
        {
            onEmailChange(it)
            emailError = !it.validateEmail() || it.isEmpty()

        },
        isError = emailError,
        leadingIcon = {
            Icon(
                painterResource(Res.drawable.mail),
                contentDescription = "Email"
            )
        },
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(stringResource(Res.string.email))
        },
        errorDescription = {
            Text(
                stringResource(Res.string.invalid_email),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusRequester.moveFocus(FocusDirection.Down) }
        ),
    )

    CustomTextField(
        password,
        { onPasswordChange(it.filterPasswordChars()); passwordError = false },
        isError = passwordError,
        leadingIcon = {
            Icon(
                painterResource(Res.drawable.lock),
                contentDescription = "Password"
            )
        },
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(stringResource(Res.string.password))
        },
        trailingIcon = {
            if (showPassword) {
                Icon(
                    painterResource(Res.drawable.eye),
                    contentDescription = "Show Password",
                    modifier = Modifier.clickable(onClick = {
                        showPassword = !showPassword
                    })

                )
            } else {
                Icon(
                    painterResource(Res.drawable.eye_crossed),
                    modifier = Modifier.clickable(onClick = {
                        showPassword = !showPassword
                    }),
                    contentDescription = "Hide password"
                )
            }
        },
        errorDescription = {
            Text(
                stringResource(Res.string.weak_password),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        },
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusRequester.clearFocus() }
        ),
    )

    Text(
        stringResource(Res.string.forgot_password),
        modifier = Modifier.clickable(
            onClick = onForgotPassword
        ).fillMaxWidth(),
        textAlign = TextAlign.End,
        color = background,
        fontSize = 14.sp
    )
    Spacer(Modifier.height(10.dp))
    AuthButton(
        Res.string.sign_in
    ) {
        focusRequester.clearFocus()
        onSignIn()
    }
}
