package com.hotelka.moscowrealtime.presentation.ui.Content.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.GradientButton
import com.hotelka.moscowrealtime.presentation.ui.Content.covers.UserSearchItemPic
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun InviteUserItem(modifier:Modifier, inviteAction: StringResource, user: User, onClick:()-> Unit){
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        UserSearchItemPic(user.userPic, user.profilePrivate)
        Spacer(Modifier.width(10.dp))
        Column(Modifier.padding(top = 5.dp).weight(1f)) {
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
        GradientButton(
            modifier = Modifier,
            shape = RoundedCornerShape(10.dp),
            onClick = onClick,
            content = {
                Text(stringResource(inviteAction), textAlign = TextAlign.Center)
            }
        )
    }
}