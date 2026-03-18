package com.hotelka.moscowrealtime.presentation.ui.Content.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.DefaultAppCard
import com.hotelka.moscowrealtime.presentation.ui.Content.covers.MiniCardItemCover
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.no_quests_together
import moscowrealtime.composeapp.generated.resources.profile_square
import moscowrealtime.composeapp.generated.resources.quest
import moscowrealtime.composeapp.generated.resources.quests_plural
import moscowrealtime.composeapp.generated.resources.together_in
import org.jetbrains.compose.resources.stringResource

@Composable
fun FriendItem(
    modifier: Modifier = Modifier,
    user: User,
    togetherInQuests: Int,
    onViewProfile: () -> Unit
) {

    DefaultAppCard(modifier, onClick = onViewProfile) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row {
                MiniCardItemCover(user.userPic, Res.drawable.profile_square)
                Spacer(Modifier.width(10.dp))
                Column(
                    Modifier
                        .height(80.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.Bottom)
                ) {
                    Text(
                        user.name,
                        color = titleTextColor.copy(0.8f),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        "@${user.username}",
                        color = secondaryTextColor,
                        fontSize = 14.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                }
            }

        }
    }
}