package com.hotelka.moscowrealtime.presentation.ui.Content.covers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.domain.model.Discover
import com.hotelka.moscowrealtime.presentation.ui.Custom.shimmerLoading
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.question
import org.jetbrains.compose.resources.painterResource

@Composable
fun QuestLocationItemCover(modifier: Modifier, completed: Boolean, discover: Discover?) {
    if (!completed || discover == null) {
        Icon(
            painter = painterResource(Res.drawable.question),
            modifier = modifier,
            contentDescription = "Image",
            tint = blue
        )
    } else {
        KamelImage(
            { asyncPainterResource(discover.imageSavedUrl) },
            contentDescription = "Cover",
            onLoading = {
                Box(
                    Modifier
                        .fillMaxSize()
                        .shimmerLoading(500)
                )
            },
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(15.dp)
                .size(50.dp)
                .clip(RoundedCornerShape(10.dp)),
        )
    }
}