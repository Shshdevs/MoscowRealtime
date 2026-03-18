package com.hotelka.moscowrealtime.presentation.ui.Content.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import co.touchlab.kermit.Logger
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.Custom.shimmerLoading
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.circle_user
import org.jetbrains.compose.resources.painterResource

@Composable
fun UsersStack(
    modifier: Modifier = Modifier,
    userCardSize: Dp,
    users: List<User>
) {
    Row(modifier.offset(x = if (users.size > 1) userCardSize/2 else if (users.size > 2) userCardSize else 0.dp)) {
        users.take(3).forEachIndexed { index, user ->
            if (user.userPic != null) {
                KamelImage(
                    resource = {
                        asyncPainterResource(user.userPic)
                    },
                    onLoading = {it ->
                        Box(Modifier.fillMaxSize().shimmerLoading(500))
                    },
                    onFailure = {
                        Logger.withTag("UserPic").d { it.message.toString() }
                    },
                    contentDescription = "User Profile Picture",
                    modifier = Modifier
                        .size(userCardSize)
                        .zIndex((users.size - index).toFloat())
                        .offset(x = (-userCardSize/users.take(3).size * index))
                        .clip(CircleShape)
                    ,
                    contentScale = ContentScale.Crop
                )
            } else{
                Image(
                    painterResource(Res.drawable.circle_user),
                    contentDescription = "User Profile Picture",
                    modifier = Modifier
                        .size(userCardSize)
                        .zIndex((users.size - index).toFloat())
                        .offset(x = (-userCardSize/users.take(3).size) * index)
                        .clip(CircleShape)
                        .background(background)
                    ,
                    colorFilter = ColorFilter.tint(blue),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}