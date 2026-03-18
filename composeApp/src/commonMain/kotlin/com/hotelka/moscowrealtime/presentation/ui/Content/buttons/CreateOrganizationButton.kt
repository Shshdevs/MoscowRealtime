package com.hotelka.moscowrealtime.presentation.ui.Content.buttons

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.briefcase
import moscowrealtime.composeapp.generated.resources.create_organization
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CreateOrganizationButton(onCreateOrganization:() -> Unit) {
    GradientButton(
        modifier = Modifier.fillMaxWidth(),
        shape = CircleShape,
        onClick = onCreateOrganization,
    ) {
        Icon(
            painterResource(Res.drawable.briefcase),
            "Briefcase"
        )
        Spacer(Modifier.width(5.dp))
        Text(
            stringResource(Res.string.create_organization),
            fontWeight = FontWeight.Bold
        )
    }
}