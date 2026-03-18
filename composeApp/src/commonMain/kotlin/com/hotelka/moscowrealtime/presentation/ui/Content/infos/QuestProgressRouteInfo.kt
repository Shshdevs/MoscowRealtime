package com.hotelka.moscowrealtime.presentation.ui.Content.infos

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.domain.model.detailed.DiscoverDetailed
import com.hotelka.moscowrealtime.presentation.ui.Content.items.LocationPoint
import kotlin.math.PI

@Composable
fun QuestProgressRouteInfo(
    locations: List<Location>,
    successfulDiscovers: List<DiscoverDetailed>,
    onReachedLocationClicked: (Location, DiscoverDetailed) -> Unit,
    onCurrentLocationClicked: (Location) -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "sineWave")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase"
    )
    LazyColumn(
        Modifier.Companion.fillMaxSize(),
        reverseLayout = true,
        horizontalAlignment = Alignment.Companion.CenterHorizontally
    ) {
        item { Spacer(Modifier.Companion.height(100.dp)) }

        itemsIndexed(locations) { index, location ->
            LocationPoint(
                locationIndex = index,
                phase = phase,
                reachedPoints = successfulDiscovers.size,
                onReachedLocationClicked = {
                    onReachedLocationClicked(
                        location,
                        successfulDiscovers.first { it.location?.id == location.id })
                },
                onCurrentLocationClicked = { onCurrentLocationClicked(location) }
            )
        }
        item { Spacer(Modifier.Companion.height(40.dp)) }

    }
}