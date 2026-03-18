package com.hotelka.moscowrealtime.presentation.ui.Content.forms

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.CreateOrganizationButton
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.for_business
import org.jetbrains.compose.resources.stringResource

@Composable
fun SettingsOrganizationForm(onCreateOrganization:() -> Unit) {
    Text(
        stringResource(Res.string.for_business),
        fontSize = 18.sp,
        color = titleTextColor.copy(0.8f),
        fontWeight = FontWeight.SemiBold
    )
    Spacer(Modifier.height(10.dp))
    CreateOrganizationButton(onCreateOrganization)
}