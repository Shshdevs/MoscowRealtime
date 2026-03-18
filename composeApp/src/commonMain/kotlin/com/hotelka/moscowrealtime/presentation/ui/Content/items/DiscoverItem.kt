package com.hotelka.moscowrealtime.presentation.ui.Content.items

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.Discover
import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.presentation.ui.Custom.shimmerLoading
import com.hotelka.moscowrealtime.presentation.ui.Custom.verticalGradient
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.DefaultAppCard
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.utils.DateFormatters.toDate
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.not_found
import org.jetbrains.compose.resources.stringResource

@Composable
fun DiscoverItem(
    modifier: Modifier,
    discover: Discover,
    location: Location?,
    viewHistory: () -> Unit,
) {
    DefaultAppCard(
        modifier = modifier,
        onClick = viewHistory,
    ) {
        KamelImage(
            { asyncPainterResource(discover.imageSavedUrl) },
            onLoading = { progress ->
                Box(
                    Modifier.Companion
                        .fillMaxSize()
                        .shimmerLoading(500)
                )
            },
            onFailure = {
                Box(
                    Modifier.Companion
                        .fillMaxSize()
                        .shimmerLoading(500)
                )
            },
            contentDescription = "Discover",
            contentScale = ContentScale.Companion.Crop,
            modifier = Modifier.Companion.verticalGradient(
                listOf(
                    Color.Companion.Transparent,
                    Color.Companion.Black.copy(0.3f)
                )
            )
        )

        Column(
            Modifier.Companion
                .padding(15.dp)
                .fillMaxWidth()
                .align(Alignment.Companion.BottomStart),
        ) {
            Text(
                location?.label?:stringResource(Res.string.not_found),
                fontWeight = FontWeight.Companion.SemiBold,
                color = background,
                fontSize = 14.sp
            )

            Text(
                discover.timestamp.toDate(),
                color = background,
                fontSize = 12.sp
            )

        }
    }

}