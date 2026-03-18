package com.hotelka.moscowrealtime.presentation.ui.Content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.domain.model.mapData.GeoPoint
import com.hotelka.moscowrealtime.presentation.events.EventGraphEvents
import com.hotelka.moscowrealtime.presentation.model.EventGraphUiModel
import com.hotelka.moscowrealtime.presentation.state.EventState
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.QuitMapsButton
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.MiniEventMapCard
import com.hotelka.moscowrealtime.presentation.ui.Content.widgets.MapView
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.back_to_event

@Composable
fun EventMapRouteContent(
    eventGraphUiModel: EventGraphUiModel,
    onEvent: (EventGraphEvents) -> Unit
) {
    LaunchedEffect(eventGraphUiModel.isUserLocationSet) {
        if (eventGraphUiModel.isUserLocationSet){
            val event = (eventGraphUiModel.eventState as EventState.Success).event
            event.detailedPlace?.coords?.let { coords ->
                if (coords.latitude == null || coords.longitude == null) return@let
                val geoPoint = GeoPoint(coords.latitude, coords.longitude,event.title)
                onEvent(EventGraphEvents.OnAddPlacemark(geoPoint))
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize().background(background)) {
        MapView(
            modifier = Modifier.fillMaxSize(),
            onMapReady = { map ->
                onEvent(EventGraphEvents.OnAttachMap(map))
            },
            onRelease = { onEvent(EventGraphEvents.OnDetachMap) },
            showMyLocationButton = false
        )
        MiniEventMapCard(
            Modifier.align(Alignment.BottomCenter).fillMaxWidth().padding(10.dp)
                .padding(bottom = 20.dp),
            eventGraphUiModel.eventState,
            routeInfoState = eventGraphUiModel.routeInfo,
            onMoveToUserLocation = { onEvent(EventGraphEvents.OnMoveToUserLocation) },
        )
        QuitMapsButton(
            Modifier.align(Alignment.TopStart),
            Res.string.back_to_event
        ) { onEvent(EventGraphEvents.OnGoBack) }
    }
}