package com.hotelka.moscowrealtime.presentation.ui.Content.items

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.ui.Custom.CurrentPoint
import com.hotelka.moscowrealtime.presentation.ui.Custom.PointType
import com.hotelka.moscowrealtime.presentation.ui.Custom.ReachedPoint
import com.hotelka.moscowrealtime.presentation.ui.Custom.RoutePoints
import com.hotelka.moscowrealtime.presentation.ui.Custom.UnReachedPoint
import kotlinx.coroutines.delay
import kotlin.math.sin

@Composable
fun LocationPoint(
    locationIndex: Int,
    phase: Float,
    reachedPoints: Int,
    onReachedLocationClicked: () -> Unit,
    onCurrentLocationClicked: () -> Unit
) {
    var showRoutePoint by remember { mutableStateOf(false) }
    var showPoint by remember { mutableStateOf(false) }

    LaunchedEffect(locationIndex) {
        delay(300L * locationIndex)
        showPoint = true
        delay(100L)
        showRoutePoint = true
    }

    val pointType = when {
        locationIndex < reachedPoints -> PointType.REACHED
        locationIndex == reachedPoints -> PointType.CURRENT
        else -> PointType.UNREACHED
    }

    val pointModifier = Modifier
        .offset(y = sin(phase + locationIndex * 0.5f).dp * 5f)

    AnimatedVisibility(
        visible = showPoint,
        enter = scaleIn(),
        exit = scaleOut()
    ) {
        Box {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                when (pointType) {
                    PointType.REACHED -> ReachedPoint(
                        pointModifier
                            .offset(x = sin(locationIndex * 0.5f).dp * 40f)
                            .clip(CircleShape)
                            .clickable(onClick = onReachedLocationClicked)

                    )

                    PointType.CURRENT -> CurrentPoint(
                        pointModifier
                            .offset(x = sin(locationIndex * 0.5f).dp * 45f)
                            .clip(CircleShape)
                            .clickable(onClick = onCurrentLocationClicked)

                    )

                    PointType.UNREACHED -> UnReachedPoint(
                        pointModifier.offset(x = sin(locationIndex * 0.5f).dp * 40f)
                            .clip(CircleShape)
                    )
                }
                AnimatedVisibility(
                    visible = showRoutePoint,
                    enter = scaleIn(),
                    exit = scaleOut()
                ) {
                    RoutePoints(
                        pointModifier.offset(sin(locationIndex * 0.5f).dp * 40f)
                    )
                }
                Spacer(Modifier.height(15.dp))
            }
        }
    }
}
