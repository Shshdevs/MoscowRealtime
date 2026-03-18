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
import com.hotelka.moscowrealtime.presentation.extensions.filterEmptyChars
import com.hotelka.moscowrealtime.presentation.extensions.filterLatinsAndSymbols
import com.hotelka.moscowrealtime.presentation.extensions.filterPasswordChars
import com.hotelka.moscowrealtime.presentation.extensions.validateEmail
import com.hotelka.moscowrealtime.presentation.ui.Custom.CustomTextField
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.AuthButton
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.at
import moscowrealtime.composeapp.generated.resources.confirm_password
import moscowrealtime.composeapp.generated.resources.email
import moscowrealtime.composeapp.generated.resources.eye
import moscowrealtime.composeapp.generated.resources.eye_crossed
import moscowrealtime.composeapp.generated.resources.invalid_email
import moscowrealtime.composeapp.generated.resources.lock
import moscowrealtime.composeapp.generated.resources.mail
import moscowrealtime.composeapp.generated.resources.name_too_long
import moscowrealtime.composeapp.generated.resources.password
import moscowrealtime.composeapp.generated.resources.passwords_dont_match
import moscowrealtime.composeapp.generated.resources.profile
import moscowrealtime.composeapp.generated.resources.sign_up
import moscowrealtime.composeapp.generated.resources.unavailable_username
import moscowrealtime.composeapp.generated.resources.username
import moscowrealtime.composeapp.generated.resources.weak_password
import moscowrealtime.composeapp.generated.resources.your_name
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SignUpForm(
    focusRequester: FocusManager,
    username: String,
    onUsernameChange: (String) -> Unit,
    name: String,
    onNameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    onSignUp:() -> Unit
) {
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }
    var usernameError by remember { mutableStateOf(false) }
    var nameError by remember { mutableStateOf(false) }

    var showPassword by remember { mutableStateOf(false) }

    CustomTextField(
        username,
        {
            val s = it.filterLatinsAndSymbols().filterEmptyChars().lowercase()
            onUsernameChange(s)
            usernameError = s.length < 4 || s.length > 15
        },
        isError = usernameError,
        leadingIcon = {
            Icon(
                painterResource(Res.drawable.at),
                contentDescription = "Username"
            )
        },
        modifier = Modifier.Companion.fillMaxWidth(),
        label = {
            Text(stringResource(Res.string.username))
        },
        errorDescription = {
            Text(
                stringResource(Res.string.unavailable_username),
                modifier = Modifier.Companion.fillMaxWidth(),
                textAlign = TextAlign.Companion.End
            )
        },
        keyboardOptions = KeyboardOptions.Companion.Default.copy(
            imeAction = ImeAction.Companion.Next,
            keyboardType = KeyboardType.Companion.Text
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusRequester.moveFocus(FocusDirection.Companion.Down) }
        ),
    )
    CustomTextField(
        name, { onNameChange(it); nameError = it.length > 25 },
        isError = nameError,
        leadingIcon = {
            Icon(
                painterResource(Res.drawable.profile),
                contentDescription = "Name"
            )
        },
        modifier = Modifier.Companion.fillMaxWidth(),
        label = {
            Text(stringResource(Res.string.your_name))
        },
        errorDescription = {
            Text(
                stringResource(Res.string.name_too_long),
                modifier = Modifier.Companion.fillMaxWidth(),
                textAlign = TextAlign.Companion.End
            )
        },
        keyboardOptions = KeyboardOptions.Companion.Default.copy(
            imeAction = ImeAction.Companion.Next,
            keyboardType = KeyboardType.Companion.Text
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusRequester.moveFocus(FocusDirection.Companion.Down) }
        ),
    )

    CustomTextField(
        email,
        {
            onEmailChange(it);
            emailError = !it.validateEmail() || it.isEmpty()
        },
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
            imeAction = ImeAction.Companion.Next,
            keyboardType = KeyboardType.Companion.Email
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusRequester.moveFocus(FocusDirection.Companion.Down) }
        ),
    )

    CustomTextField(
        password,
        { onPasswordChange(it.filterPasswordChars()); passwordError = it.length < 7 },
        isError = passwordError,
        leadingIcon = {
            Icon(
                painterResource(Res.drawable.lock),
                contentDescription = "Password",
            )
        },
        modifier = Modifier.Companion.fillMaxWidth(),
        label = {
            Text(stringResource(Res.string.password))
        },
        trailingIcon = {
            if (showPassword) {
                Icon(
                    painterResource(Res.drawable.eye),
                    contentDescription = "Show Password",
                    modifier = Modifier.Companion.clickable(onClick = {
                        showPassword = !showPassword
                    })
                )
            } else {
                Icon(
                    painterResource(Res.drawable.eye_crossed),
                    contentDescription = "Hide password",
                    modifier = Modifier.Companion.clickable(onClick = {
                        showPassword = !showPassword
                    })
                )
            }
        },
        errorDescription = {
            Text(
                stringResource(Res.string.weak_password),
                modifier = Modifier.Companion.fillMaxWidth(),
                textAlign = TextAlign.Companion.End
            )
        },
        visualTransformation = if (showPassword) VisualTransformation.Companion.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions.Companion.Default.copy(
            imeAction = ImeAction.Companion.Next,
            keyboardType = KeyboardType.Companion.Password
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusRequester.moveFocus(FocusDirection.Companion.Down) }
        ),
    )
    CustomTextField(
        confirmPassword,
        {
            onConfirmPasswordChange(it.filterPasswordChars()); confirmPasswordError = it != password
        },
        isError = confirmPasswordError,
        leadingIcon = {
            Icon(
                painterResource(Res.drawable.lock),
                contentDescription = "Confirm Password"
            )
        },
        modifier = Modifier.Companion.fillMaxWidth(),
        label = {
            Text(stringResource(Res.string.confirm_password))
        },
        trailingIcon = {
            if (showPassword) {
                Icon(
                    painterResource(Res.drawable.eye),
                    contentDescription = "Show Password",
                    modifier = Modifier.Companion.clickable(onClick = {
                        showPassword = !showPassword
                    })
                )
            } else {
                Icon(
                    painterResource(Res.drawable.eye_crossed),
                    contentDescription = "Hide password",
                    modifier = Modifier.Companion.clickable(onClick = {
                        showPassword = !showPassword
                    })
                )
            }
        },
        errorDescription = {
            Text(
                stringResource(Res.string.passwords_dont_match),
                modifier = Modifier.Companion.fillMaxWidth(),
                textAlign = TextAlign.Companion.End
            )
        },
        visualTransformation = if (showPassword) VisualTransformation.Companion.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions.Companion.Default.copy(
            imeAction = ImeAction.Companion.Done,
            keyboardType = KeyboardType.Companion.Password
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusRequester.clearFocus() }
        ),
    )
    Spacer(Modifier.Companion.height(10.dp))
    AuthButton(
        Res.string.sign_up
    ) {
        if (email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && name.isNotEmpty()
            && !emailError && !usernameError && !passwordError && !confirmPasswordError && !nameError
        ) {
            focusRequester.clearFocus()
            onSignUp()
        }
    }
}