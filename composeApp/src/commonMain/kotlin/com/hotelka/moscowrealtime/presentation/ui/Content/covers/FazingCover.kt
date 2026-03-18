package com.hotelka.moscowrealtime.presentation.ui.Content.covers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.Custom.shimmerLoading
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun FazingCover(url: String, direction: Int = 1, modifier: Modifier) {
    Box(modifier) {
        KamelImage(
            { asyncPainterResource(url) }, "Success",
            onLoading = {
                Box(Modifier.fillMaxSize().shimmerLoading(500))
            },
            onFailure = {
                Box(Modifier.fillMaxSize().shimmerLoading(500))
            },
            modifier = Modifier
                .rotate(-15f * direction)
                .alpha(0.3f)
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Crop
        )
        KamelImage(
            { asyncPainterResource(url) }, "Success",
            onLoading = {
                Box(Modifier.fillMaxSize().shimmerLoading(500))
            },
            onFailure = {
                Box(Modifier.fillMaxSize().shimmerLoading(500))
            },
            modifier = Modifier
                .rotate(15f * direction)
                .alpha(0.3f)
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Crop
        )
        KamelImage(
            { asyncPainterResource(url) }, "Success",
            onLoading = {
                Box(Modifier.fillMaxSize().shimmerLoading(500))
            },
            onFailure = {
                Box(Modifier.fillMaxSize().shimmerLoading(500))
            },
            modifier = Modifier
                .rotate(5f * direction)
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Crop
        )
    }
}