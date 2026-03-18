package com.hotelka.moscowrealtime.presentation.ui.Content.covers

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.Custom.shimmerLoading
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.lock
import moscowrealtime.composeapp.generated.resources.profile_circle_filled
import org.jetbrains.compose.resources.painterResource

@Composable
fun UserSearchItemPic(userPic: String?, isPrivate: Boolean, size: Dp = 60.dp, ) {
    if(userPic == null) {
        Image(
            painterResource(Res.drawable.profile_circle_filled),
            "User Profile",
            modifier = Modifier
                .size(size)
                .background(blue, CircleShape)
                .border(3.dp, blue, CircleShape)
                .clip(CircleShape),
            colorFilter = ColorFilter.tint(background)
        )
    }
    else if (isPrivate){
        Icon(
            painterResource(Res.drawable.lock),
            contentDescription = "User Profile Picture",
            modifier = Modifier
                .size(size)
                .border(2.dp, background, CircleShape)
                .background(blue, CircleShape)
                .padding(9.dp),
            tint = background
        )
    }
    else {
        KamelImage(
            {asyncPainterResource(userPic)},
            "User Profile",
            onLoading = {
                Box(Modifier
                    .fillMaxSize()
                    .shimmerLoading(500))
            },
            onFailure = {
                Box(Modifier
                    .fillMaxSize()
                    .shimmerLoading(500))
            },
            modifier = Modifier
                .size(size)
                .border(2.dp, blue, CircleShape)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}