package com.hotelka.moscowrealtime.presentation.ui.Content.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.OrganizationInvitation
import com.hotelka.moscowrealtime.domain.model.detailed.OrganizationInvitationDetailed
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.GradientButton
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.OutlinedGradientButton
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.accept
import moscowrealtime.composeapp.generated.resources.briefcase
import moscowrealtime.composeapp.generated.resources.decline
import moscowrealtime.composeapp.generated.resources.invites_to_be_the_organizer
import moscowrealtime.composeapp.generated.resources.invites_to_join_organization
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun OrganizationInvitationCard(
    organizationInvitation: OrganizationInvitationDetailed,
    onAccept: (OrganizationInvitation) -> Unit,
    onDecline: (OrganizationInvitation) -> Unit
) {
    DefaultAppCard(onClick = null) {
        Column(Modifier.padding(20.dp)) {
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

                Text(
                    organizationInvitation.organization.name + stringResource(
                        if (organizationInvitation.invitation.toBeHost) Res.string.invites_to_be_the_organizer else Res.string.invites_to_join_organization
                    ),
                    fontWeight = FontWeight.SemiBold,
                    color = secondaryTextColor,
                    fontSize = 14.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                OutlinedGradientButton(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        onDecline(organizationInvitation.invitation)
                    },
                    content = {
                        Text(
                            stringResource(Res.string.decline),
                        )
                    }
                )
                GradientButton(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        onAccept(organizationInvitation.invitation)
                    }
                ) {
                    Text(stringResource(Res.string.accept))
                }
            }
        }
    }
}