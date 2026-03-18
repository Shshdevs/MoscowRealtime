package com.hotelka.moscowrealtime.presentation.ui.Content.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.SettingsMainForm
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.SettingsOrganizationForm
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.SettingsRedZone
import com.hotelka.moscowrealtime.presentation.ui.Content.frames.DismissableCardFrame
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.DismissableCardHeader
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.settings

@Composable
fun EditSettingsCard(
    modifier: Modifier = Modifier,
    user: User,
    updatePrivateSettings:(Boolean, Boolean) -> Unit,
    onChangePassword: () -> Unit,
    onLogOut: () -> Unit,
    onEditProfile: () -> Unit,
    onDeleteAccount: () -> Unit,
    onCreateOrganization: () -> Unit,
    onDismissRequest: () -> Unit
) {
    DismissableCardFrame(
        modifier,
        paddingValues = PaddingValues(10.dp),
        onDismissRequest = onDismissRequest,
    ) {
        DismissableCardHeader(Res.string.settings)
        Spacer(Modifier.height(30.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(30.dp)) {
            item("main_form") {
                SettingsMainForm(
                    onEditProfile = onEditProfile,
                    onChangePassword = onChangePassword,
                    user = user,
                    updatePrivateSettings = updatePrivateSettings
                )
            }
            if (user.organizationId == null) {
                item("for_business") {
                    SettingsOrganizationForm(onCreateOrganization)
                }
            }
            item("red_zone"){
                SettingsRedZone(
                    onLogOut = onLogOut,
                    onDeleteAccount = onDeleteAccount
                )
            }

            item("spacer") {
                Spacer(Modifier.height(120.dp))
            }
        }
    }
}