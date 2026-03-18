package com.hotelka.moscowrealtime.presentation.ui.Content.covers

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.Custom.shimmerLoading
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun MiniCardItemCover(url: String?, placeHolder: DrawableResource) {
    if (url == null) {
        Image(
            painterResource(placeHolder),
            "Mini cover",
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(10.dp)),
            colorFilter = ColorFilter.tint(blue)
        )
    } else {
        KamelImage(
            { asyncPainterResource(url) },
            "Mini Cover",
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(10.dp)),
            onLoading = {
                Box(
                    Modifier
                        .fillMaxSize()
                        .shimmerLoading(500)
                )
            },
            onFailure = {
                Box(
                    Modifier
                        .fillMaxSize()
                        .shimmerLoading(500)
                )
            },
            contentScale = ContentScale.Crop
        )
    }
}