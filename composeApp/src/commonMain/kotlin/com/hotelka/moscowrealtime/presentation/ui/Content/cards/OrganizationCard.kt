package com.hotelka.moscowrealtime.presentation.ui.Content.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.Organization
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.GradientButton
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.briefcase
import moscowrealtime.composeapp.generated.resources.manage_organization
import moscowrealtime.composeapp.generated.resources.view_my_organization
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun OrganizationCard(
    organization: Organization,
    userIsHost: Boolean,
    showButton: Boolean,
    navigateOrganizationPage: () -> Unit
) {
    DefaultAppCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = null
    ) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    painterResource(Res.drawable.briefcase),
                    "Briefcase",
                    tint = blue,
                    modifier = Modifier.size(60.dp)
                )

                Column {
                    Text(
                        organization.name,
                        fontWeight = FontWeight.SemiBold,
                        color = titleTextColor.copy(0.8f),
                        fontSize = 16.sp
                    )
                }
            }
            if (showButton) {
                GradientButton(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    onClick = navigateOrganizationPage
                ) {
                    Text(stringResource(if (userIsHost) Res.string.manage_organization else Res.string.view_my_organization))
                }
            }
        }
    }
}