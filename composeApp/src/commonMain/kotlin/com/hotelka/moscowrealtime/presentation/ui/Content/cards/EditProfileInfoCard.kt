package com.hotelka.moscowrealtime.presentation.ui.Content.cards

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.domain.model.TempUserInfo
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.presentation.extensions.filterEmptyChars
import com.hotelka.moscowrealtime.presentation.extensions.filterLatinsAndSymbols
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.GradientButton
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.OutlinedGradientButton
import com.hotelka.moscowrealtime.presentation.ui.Content.frames.DismissableCardFrame
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.DismissableCardHeader
import com.hotelka.moscowrealtime.presentation.ui.Custom.CustomTextField
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.indigo
import com.hotelka.moscowrealtime.presentation.ui.theme.purple
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.at
import moscowrealtime.composeapp.generated.resources.change_profile_photo
import moscowrealtime.composeapp.generated.resources.edit_data
import moscowrealtime.composeapp.generated.resources.name_too_long
import moscowrealtime.composeapp.generated.resources.profile
import moscowrealtime.composeapp.generated.resources.submit
import moscowrealtime.composeapp.generated.resources.unavailable_username
import moscowrealtime.composeapp.generated.resources.username
import moscowrealtime.composeapp.generated.resources.your_name
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun EditProfileInfoCard(
    modifier: Modifier,
    tempUser: TempUserInfo?,
    onDismiss: () -> Unit,
    onSubmit: (TempUserInfo) -> Unit,
    updateTempUser: (TempUserInfo?) -> Unit,
    pickPhoto: () -> Unit,
) {
    var usernameError by remember { mutableStateOf(false) }
    var nameError by remember { mutableStateOf(false) }

    val focusRequester = LocalFocusManager.current

    DismissableCardFrame(
        modifier,
        paddingValues = PaddingValues(10.dp),
        onDismissRequest = {
            onDismiss()
        },
    ) {
        DismissableCardHeader(Res.string.edit_data)
        Spacer(Modifier.height(20.dp))
        CustomTextField(
            value = tempUser?.username ?: "",
            onValueChange = {
                updateTempUser(
                    tempUser?.copy(
                        tempUser.name,
                        it.filterLatinsAndSymbols().filterEmptyChars().lowercase()
                    )
                )
                usernameError = it.length < 4 || it.length > 15
            },
            isError = usernameError,
            leadingIcon = {
                Icon(painterResource(Res.drawable.at), "Username")
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(Res.string.username)) },
            errorDescription = {
                Text(
                    stringResource(Res.string.unavailable_username),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                focusRequester.moveFocus(FocusDirection.Down)
            }),
            singleLine = true,
            color = indigo
        )

        CustomTextField(
            tempUser?.name ?: "",
            {
                updateTempUser(tempUser?.copy(it, tempUser.username))
                nameError = it.length > 25
            },
            isError = nameError,
            leadingIcon = {
                Icon(
                    painterResource(Res.drawable.profile),
                    contentDescription = "Name"
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(stringResource(Res.string.your_name))
            },
            errorDescription = {
                Text(
                    stringResource(Res.string.name_too_long),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusRequester.clearFocus() }
            ),
            color = indigo
        )
        Spacer(Modifier.height(20.dp))
        OutlinedGradientButton(
            modifier = Modifier.fillMaxWidth(),
            shape = CircleShape,
            onClick = pickPhoto,
        ) {
            Text(
                stringResource(Res.string.change_profile_photo),
            )
        }
        Spacer(Modifier.height(10.dp))

        GradientButton(
            if (!nameError && !usernameError) listOf(blue, purple) else listOf(
                Color.Gray,
                Color.Gray
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            onClick = {
                tempUser?.let {
                    onSubmit(it)
                }
            },
            enabled = !nameError && !usernameError
        ) {
            Text(stringResource(Res.string.submit))
        }
    }
}