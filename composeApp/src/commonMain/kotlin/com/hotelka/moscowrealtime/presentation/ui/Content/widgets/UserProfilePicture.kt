package com.hotelka.moscowrealtime.presentation.ui.Content.widgets

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.purple
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.Custom.shimmerLoading
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.lock
import moscowrealtime.composeapp.generated.resources.profile_circle_filled
import org.jetbrains.compose.resources.painterResource

@Composable
fun UserProfilePicture(modifier:Modifier, showProfilePrivate: Boolean, userPic: Any?) {
    if (!showProfilePrivate){
        Icon(
            painterResource(Res.drawable.lock),
            contentDescription = "User Profile Picture",
            modifier = modifier
                .size(120.dp)
                .border(4.dp, background, CircleShape)
                .background(blue, CircleShape)
                .padding(18.dp),
            tint = background
        )
    } else {
        when (userPic) {
            is ImageBitmap -> {
                Image(
                    bitmap = userPic,
                    contentDescription = "User Profile Picture",
                    modifier = modifier
                        .size(120.dp)
                        .border(4.dp, background, CircleShape)
                        .clip(CircleShape)
                        .background(Transparent),
                    contentScale = ContentScale.Crop,
                )
            }

            is String -> {
                KamelImage(
                    { asyncPainterResource(userPic) },
                    onLoading = {
                        Box(Modifier.fillMaxSize().shimmerLoading(500))
                    },
                    onFailure = {
                        Box(Modifier.fillMaxSize().shimmerLoading(500))
                    },
                    contentDescription = "User Profile Picture",
                    modifier = modifier
                        .size(120.dp)
                        .border(4.dp, background, CircleShape)
                        .clip(CircleShape)
                        .background(background),
                    contentScale = ContentScale.Crop
                )
            }

            else -> {
                Image(
                    painterResource(Res.drawable.profile_circle_filled),
                    contentDescription = "User Profile Picture",
                    modifier = modifier
                        .size(120.dp)
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    blue,
                                    purple
                                )
                            ), CircleShape
                        )
                        .padding(8.dp)
                        .clip(CircleShape)
                        .background(blue),
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(background)
                )
            }
        }
    }
}