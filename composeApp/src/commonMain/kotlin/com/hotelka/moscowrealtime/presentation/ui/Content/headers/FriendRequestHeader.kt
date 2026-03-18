package com.hotelka.moscowrealtime.presentation.ui.Content.headers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.presentation.ui.Content.covers.MiniCardItemCover
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.friend_request
import moscowrealtime.composeapp.generated.resources.profile_square
import org.jetbrains.compose.resources.stringResource

@Composable
fun FriendRequestHeader(user: User) {

    Row(Modifier.wrapContentHeight(), verticalAlignment = Alignment.Top) {
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

            Text(
                stringResource(Res.string.friend_request),
                color = blue.copy(0.7f),
                fontSize = 14.sp,
            )
        }
    }
}