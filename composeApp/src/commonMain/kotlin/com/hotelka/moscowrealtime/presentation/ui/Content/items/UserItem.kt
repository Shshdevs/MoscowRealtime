package com.hotelka.moscowrealtime.presentation.ui.Content.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.presentation.ui.Content.covers.UserSearchItemPic
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor

@Composable
fun UserItem(modifier: Modifier, user: User, onClick: (() -> Unit)?) {
    Row(
        modifier.then(
            if (onClick != null) {
                Modifier.clickable(onClick = onClick)
            } else {
                Modifier
            }
        )
    ) {
        UserSearchItemPic(user.userPic, user.profilePrivate)
        Spacer(Modifier.width(10.dp))
        Column(Modifier.padding(top = 5.dp)) {
            Text(
                user.name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = titleTextColor
            )
            Text(
                "@${user.username}",
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = titleTextColor
            )
        }

    }
}
