package com.hotelka.moscowrealtime.presentation.ui.Content.alerts

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import com.hotelka.moscowrealtime.domain.usecase.organizations.CheckExistingOrganizationsUseCase
import com.hotelka.moscowrealtime.presentation.ui.Custom.CustomTextField
import com.hotelka.moscowrealtime.presentation.ui.Custom.NeutralAlert
import com.hotelka.moscowrealtime.presentation.ui.theme.darkBlue
import kotlinx.coroutines.delay
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.cancel
import moscowrealtime.composeapp.generated.resources.change_organization_name_alert
import moscowrealtime.composeapp.generated.resources.change_organization_name_description_alert
import moscowrealtime.composeapp.generated.resources.error_checking_organizations
import moscowrealtime.composeapp.generated.resources.found_organizations_alike
import moscowrealtime.composeapp.generated.resources.provide_organization_name
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun ChangeOrganizationNameAlert(
    checkExistingOrganizationsUseCase: CheckExistingOrganizationsUseCase = koinInject(),
    onChangeOrganizationName: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var similarOrganizations by remember { mutableStateOf<List<String>>(emptyList()) }
    var showCheckError by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    var organizationName by remember { mutableStateOf("") }

    LaunchedEffect(organizationName) {
        if (organizationName.length >= 3) {
            delay(500)
            if (organizationName.length >= 3) {
                checkExistingOrganizationsUseCase(organizationName).fold(
                    onSuccess = { similarOrganizations = it },
                    onFailure = { showCheckError = true }
                )
            }
        } else {
            similarOrganizations = emptyList()
        }
    }

    NeutralAlert(
        titleText = stringResource(Res.string.change_organization_name_alert),
        secondaryText = stringResource(Res.string.change_organization_name_description_alert),
        cancelText = stringResource(Res.string.cancel),
        submitText = stringResource(Res.string.change_organization_name_alert),
        onSubmit = { onChangeOrganizationName(organizationName) },
        onDismissRequest = onDismiss,
        middleContent = {
            CustomTextField(
                value = organizationName,
                onValueChange = { organizationName = it },
                isError = similarOrganizations.isNotEmpty(),
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(stringResource(Res.string.provide_organization_name))
                },
                errorDescription = {
                    if (showCheckError) {
                        Text(stringResource(Res.string.error_checking_organizations))
                    } else if (similarOrganizations.isNotEmpty()) {
                        Text(
                            stringResource(Res.string.found_organizations_alike) + "\n" +
                                    similarOrganizations.joinToString(", "),
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                },
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