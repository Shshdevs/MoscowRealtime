package com.hotelka.moscowrealtime.presentation.ui.Content.headers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.Content.infos.TagsRow
import com.hotelka.moscowrealtime.presentation.ui.Custom.shimmerLoading
import com.hotelka.moscowrealtime.presentation.ui.Custom.verticalGradient
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun CardHeader(
    cover: String,
    tags: List<String>
) {
    val hazeState = rememberHazeState()

    Box(
        Modifier
            .height(250.dp)
            .fillMaxWidth()
    ) {
        KamelImage(
            { asyncPainterResource(cover) },
            onLoading = {
                Box(Modifier.matchParentSize().shimmerLoading())
            },
            contentDescription = "EventCover",
            modifier = Modifier
                .matchParentSize()
                .hazeSource(hazeState)
                .verticalGradient(
                    listOf(
                        Transparent,
                        Black.copy(0.4f)
                    )
                ),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )

        TagsRow(
            Modifier.padding(vertical = 20.dp, horizontal = 15.dp)
                .align(Alignment.BottomStart),
            tags,
            hazeState
        )
    }
}