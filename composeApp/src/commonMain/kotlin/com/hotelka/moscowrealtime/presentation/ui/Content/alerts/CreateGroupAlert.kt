package com.hotelka.moscowrealtime.presentation.ui.Content.alerts

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import com.hotelka.moscowrealtime.presentation.ui.Custom.CustomTextField
import com.hotelka.moscowrealtime.presentation.ui.Custom.NeutralAlert
import com.hotelka.moscowrealtime.presentation.ui.theme.darkBlue
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.cancel
import moscowrealtime.composeapp.generated.resources.create_group
import moscowrealtime.composeapp.generated.resources.group_exists
import moscowrealtime.composeapp.generated.resources.group_name
import moscowrealtime.composeapp.generated.resources.provide_group_name
import org.jetbrains.compose.resources.stringResource

@Composable
fun CreateGroupAlert(
    groupExists:(String) -> Boolean,
    onCreateGroup: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    var groupName by remember { mutableStateOf("") }
    NeutralAlert(
        titleText = stringResource(Res.string.create_group),
        secondaryText = stringResource(Res.string.provide_group_name),
        cancelText = stringResource(Res.string.cancel),
        submitText = stringResource(Res.string.create_group),
        onSubmit = { onCreateGroup(groupName) },
        onDismissRequest = onDismiss,
        middleContent = {
            CustomTextField(
                value = groupName,
                onValueChange = { groupName = it },
                isError = groupExists(groupName),
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(stringResource(Res.string.group_name))
                },
                errorDescription = { Text(stringResource(Res.string.group_exists)) },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                color = darkBlue
            )
        }
    )
}