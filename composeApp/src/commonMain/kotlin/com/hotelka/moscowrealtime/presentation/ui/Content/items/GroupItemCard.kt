package com.hotelka.moscowrealtime.presentation.ui.Content.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.detailed.GroupDetailed
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.DefaultAppCard
import com.hotelka.moscowrealtime.presentation.ui.Content.covers.UserSearchItemPic
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.no_pinned_quest
import moscowrealtime.composeapp.generated.resources.organizer
import moscowrealtime.composeapp.generated.resources.pinned_quest
import org.jetbrains.compose.resources.stringResource

@Composable
fun GroupItemCard(
    modifier: Modifier = Modifier,
    groupDetailed: GroupDetailed,
    navigateGroup: (GroupDetailed) -> Unit
) {
    DefaultAppCard(
        modifier = modifier,
        onClick = {
            navigateGroup(groupDetailed)
        }
    ) {
        Column(Modifier.padding(20.dp)) {
            Text(
                groupDetailed.group.groupName,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = titleTextColor.copy(0.8f)
            )
            if (groupDetailed.quest == null) {
                Text(
                    stringResource(Res.string.no_pinned_quest),
                    fontSize = 16.sp,
                    color = secondaryTextColor,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(end=60.dp)
                )
            } else {
                Text(
                    stringResource(Res.string.pinned_quest) + groupDetailed.quest.title,
                    fontSize = 16.sp,
                    color = secondaryTextColor,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(end=60.dp)
                )
            }

            Row {
                Text(
                    stringResource(Res.string.organizer) + groupDetailed.organizer.name,
                    fontSize = 16.sp,
                    color = secondaryTextColor,
                    modifier = Modifier.align(Alignment.Bottom).weight(1f)
                )
                Spacer(Modifier.width(5.dp))
                UserSearchItemPic(groupDetailed.organizer.userPic, false)
            }
        }
    }
}