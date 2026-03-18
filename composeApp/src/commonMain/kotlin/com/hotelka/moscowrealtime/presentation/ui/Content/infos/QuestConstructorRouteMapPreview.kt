package com.hotelka.moscowrealtime.presentation.ui.Content.infos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.mapData.Route
import com.hotelka.moscowrealtime.presentation.extensions.formatDistance
import com.hotelka.moscowrealtime.presentation.extensions.formatDuration
import com.hotelka.moscowrealtime.presentation.ui.Content.widgets.MapView
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.duration
import moscowrealtime.composeapp.generated.resources.location
import moscowrealtime.composeapp.generated.resources.location_included
import moscowrealtime.composeapp.generated.resources.locations_pair
import moscowrealtime.composeapp.generated.resources.locations_plural
import moscowrealtime.composeapp.generated.resources.map_preview
import moscowrealtime.composeapp.generated.resources.total_route
import org.jetbrains.compose.resources.stringResource

@Composable
fun QuestConstructorRouteMapPreview(
    route: Route?,
    locationsSize: Int,
    onMapReady: (Any) -> Unit,
    onRelease: () -> Unit,
    onMapTouchStateChanged: (Boolean) -> Unit,
) {
    Column(modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)) {
        Text(
            stringResource(Res.string.map_preview),
            color = secondaryTextColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
        )
        Spacer(Modifier.height(5.dp))

        if (route != null) {
            Text(
                locationsSize.toString() + when (locationsSize) {
                    1 -> stringResource(Res.string.location)
                    in 2..4 -> stringResource(Res.string.locations_pair)
                    else -> stringResource(Res.string.locations_plural)
                }
                        + " • " + stringResource(Res.string.total_route) + route.distance.formatDistance()
                        + " • " + stringResource(Res.string.duration) + route.duration.formatDuration(),
                color = secondaryTextColor,
                fontSize = 14.sp,
            )
        } else {
            Text(
                stringResource(Res.string.location_included),
                color = secondaryTextColor,
                fontSize = 14.sp,
            )
        }
    }

    MapView(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .height(250.dp)
            .background(blue),
        onMapReady = onMapReady,
        onRelease = onRelease,
        onMapTouchStateChanged = onMapTouchStateChanged,
        showMyLocationButton = false,
    )
}