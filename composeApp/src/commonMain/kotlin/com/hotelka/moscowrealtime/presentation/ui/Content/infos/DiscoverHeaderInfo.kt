package com.hotelka.moscowrealtime.presentation.ui.Content.infos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.presentation.ui.Custom.shimmerLoading
import com.hotelka.moscowrealtime.presentation.ui.Content.pills.AroundPill
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import dev.chrisbanes.haze.HazeState
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.loading
import moscowrealtime.composeapp.generated.resources.not_found
import org.jetbrains.compose.resources.stringResource

@Composable
fun DiscoverHeaderInfo(
    enableShowWhatAround: Boolean,
    modifier: Modifier,
    location: Location?,
    height: Dp,
    hazeState: HazeState,
    onShowAround: (Location) -> Unit
) {
    Box(modifier) {
        Column(
            Modifier
                .align(Alignment.BottomCenter)
                .height(height),
            verticalArrangement = Arrangement.spacedBy(
                10.dp,
                alignment = Alignment.Bottom
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (enableShowWhatAround) AroundPill(
                hazeState,
                { location?.let { onShowAround(it) } })

            Text(
                location?.label?:stringResource(Res.string.not_found),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = titleTextColor.copy(0.8f),
                modifier = Modifier.padding(bottom = 5.dp).fillMaxWidth(),
                lineHeight = 1.5.em,
                textAlign = TextAlign.Center
            )
        }
    }
}