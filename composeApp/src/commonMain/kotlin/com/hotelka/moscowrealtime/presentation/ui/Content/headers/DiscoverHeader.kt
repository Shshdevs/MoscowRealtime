package com.hotelka.moscowrealtime.presentation.ui.Content.headers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.Custom.shimmerLoading
import dev.chrisbanes.haze.HazeProgressive
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun DiscoverHeader(modifier:Modifier, hazeState: HazeState, height: Dp, image: String) {
    Box(modifier) {
        KamelImage(
            { asyncPainterResource(image) },
            onLoading = { progress ->
                Box(
                    Modifier
                        .fillMaxSize()
                        .shimmerLoading(500)
                )
            },
            contentDescription = "AnalyzedImage",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(height)
                .hazeSource(hazeState)
        )

        Box(
            Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .height(60.dp)
                .hazeEffect(hazeState) {
                    progressive = HazeProgressive.verticalGradient(
                        startIntensity = 0.7f,
                        endIntensity = 0f
                    )
                }
                .background(
                    brush = Brush
                        .verticalGradient(
                            listOf(
                                Black.copy(0.5f),
                                Black.copy(0.45f),
                                Transparent
                            )
                        ),
                ),
            contentAlignment = Alignment.TopCenter
        ) {
            HorizontalDivider(
                thickness = 2.dp,
                color = background,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(0.5f)
                    .clip(CircleShape)
            )
        }
    }
}