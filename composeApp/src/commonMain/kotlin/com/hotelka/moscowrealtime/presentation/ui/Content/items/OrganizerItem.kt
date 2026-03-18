package com.hotelka.moscowrealtime.presentation.ui.Content.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.Organizer
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.DefaultAppCard
import com.hotelka.moscowrealtime.presentation.ui.Content.covers.MiniCardItemCover
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.current_groups
import moscowrealtime.composeapp.generated.resources.no_groups_yet
import moscowrealtime.composeapp.generated.resources.profile_square
import org.jetbrains.compose.resources.stringResource

@Composable
fun OrganizerItem(
    modifier: Modifier,
    organizer: Organizer,
    openOrganizerPage: () -> Unit
) {
    DefaultAppCard(
        modifier = modifier,
        onClick = openOrganizerPage
    ) {
        Row(Modifier.padding(20.dp)) {
            MiniCardItemCover(organizer.organizer.userPic, Res.drawable.profile_square)
            Spacer(Modifier.width(10.dp))
            Column(
                Modifier.height(80.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    organizer.organizer.name,
                    color = titleTextColor.copy(0.8f),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    "@${organizer.organizer.username}",
                    color = secondaryTextColor,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    if (organizer.groups.isNotEmpty()){
                        stringResource(Res.string.current_groups) + organizer.groups.joinToString(
                            ", "
                        ) { it.group.groupName }
                    } else {
                        stringResource(Res.string.no_groups_yet)
                    },
                    color = secondaryTextColor,
                    fontSize = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}