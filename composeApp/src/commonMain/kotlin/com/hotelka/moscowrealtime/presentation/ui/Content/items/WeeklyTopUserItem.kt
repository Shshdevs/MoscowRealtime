package com.hotelka.moscowrealtime.presentation.ui.Content.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.TopWeeklyUser
import com.hotelka.moscowrealtime.presentation.ui.Content.covers.UserSearchItemPic
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.bronze
import com.hotelka.moscowrealtime.presentation.ui.theme.gold
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import com.hotelka.moscowrealtime.presentation.ui.theme.silver
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.points
import moscowrealtime.composeapp.generated.resources.trophy_star
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun WeeklyTopUserItem(
    modifier: Modifier = Modifier,
    topWeeklyUser: TopWeeklyUser,
    place: Int
) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Badge(
            modifier = Modifier.size(30.dp),
            contentColor = background,
            containerColor = when (place) {
                1 -> gold
                2 -> silver
                3 -> bronze
                else -> Color.Gray
            }
        ) {
            Text("#$place", fontSize = 14.sp)
        }
        Spacer(Modifier.width(10.dp))
        if (place == 1) {
            BadgedBox(
                badge = {
                    Badge(
                        modifier = Modifier.size(20.dp).offset(x = (-5).dp, y = 5.dp),
                        contentColor = background,
                        containerColor = gold
                    ) {
                        Icon(
                            painterResource(Res.drawable.trophy_star),
                            "Trophy",
                            modifier = Modifier
                        )
                    }
                }
            ) {
                UserSearchItemPic(
                    topWeeklyUser.user.userPic,
                    topWeeklyUser.user.photosPrivate,
                    size = 50.dp
                )
            }
        } else {
            UserSearchItemPic(
                topWeeklyUser.user.userPic,
                topWeeklyUser.user.photosPrivate,
                size = 50.dp
            )
        }
        Spacer(Modifier.width(10.dp))

        Column {
            Text(
                "@${topWeeklyUser.user.username}",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = titleTextColor.copy(0.8f)
            )
            Spacer(Modifier.height(5.dp))
            Text(
                topWeeklyUser.score.totalScore.toString() + stringResource(Res.string.points),
                fontSize = 12.sp,
                color = secondaryTextColor
            )
        }
    }
}
