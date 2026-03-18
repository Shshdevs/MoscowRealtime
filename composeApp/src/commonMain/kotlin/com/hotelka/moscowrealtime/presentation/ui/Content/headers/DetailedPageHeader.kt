package com.hotelka.moscowrealtime.presentation.ui.Content.headers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.Custom.shimmerLoading
import com.hotelka.moscowrealtime.presentation.ui.Custom.verticalGradient
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.BackButton
import com.hotelka.moscowrealtime.presentation.ui.Content.infos.TagsRow
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun PageHeader(
    modifier: Modifier,
    cover: String,
    tags: List<String>,
    showTags: Boolean,
    onNavigateBack: () -> Unit
) {
    val hazeState = rememberHazeState()

    Box(modifier) {
        KamelImage(
            { asyncPainterResource(cover) },
            onLoading = {
                Box(Modifier.fillMaxSize().shimmerLoading())
            },
            contentDescription = "EventCover",
            modifier = Modifier
                .fillMaxSize()
                .hazeSource(hazeState)
                .verticalGradient(
                    listOf(
                        Transparent,
                        Black.copy(0.4f)
                    )
                ),
            contentScale = ContentScale.Crop
        )

        if (showTags) {
            TagsRow(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .padding(vertical = 20.dp, horizontal = 15.dp)
                    .align(Alignment.BottomStart),
                tags = tags,
                hazeState = hazeState,
                expandEnabled = true
            )
        }
        BackButton(onClick = onNavigateBack)
    }


}

