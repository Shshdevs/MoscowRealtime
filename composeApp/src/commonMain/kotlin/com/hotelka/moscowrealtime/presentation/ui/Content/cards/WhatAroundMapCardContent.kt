package com.hotelka.moscowrealtime.presentation.ui.Content.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.domain.model.mapData.GeoPoint
import com.hotelka.moscowrealtime.presentation.events.DiscoverPageEvents
import com.hotelka.moscowrealtime.presentation.state.listState.EventListState
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.ErrorForm
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.LoadingForm
import com.hotelka.moscowrealtime.presentation.ui.Content.items.MiniEventItem
import com.hotelka.moscowrealtime.presentation.ui.Content.widgets.MapView
import com.hotelka.moscowrealtime.presentation.ui.theme.titleTextColor
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.loading_events
import moscowrealtime.composeapp.generated.resources.something_went_wrong
import org.jetbrains.compose.resources.stringResource

@Composable
fun WhatAroundMapCardContent(
    eventsState: EventListState,
    location: Location,
    onEvent: (DiscoverPageEvents) -> Unit,
) {
    LaunchedEffect(eventsState) {
        if (eventsState is EventListState.Success) {
            eventsState.events.forEach { event ->
                event.detailedPlace?.coords?.let {
                    if(it.latitude == null || it.longitude == null) return@let
                    val geoPoint = GeoPoint(it.latitude, it.longitude, event.title)
                    onEvent(DiscoverPageEvents.OnAddMarker(geoPoint))
                }
            }
        }
    }
    Column(Modifier) {
        MapView(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(20.dp))
                .shadow(
                    3.dp,
                    RoundedCornerShape(10.dp),
                    true,
                    titleTextColor,
                    titleTextColor
                ),
            onMapReady = {
                onEvent(DiscoverPageEvents.OnSetMapView(it) {
                    val geoPoint = GeoPoint(location.lat, location.lon, location.label)
                    onEvent(DiscoverPageEvents.OnAddMarker(geoPoint))
                    onEvent(DiscoverPageEvents.OnMoveTo(geoPoint))
                })
            },
            onRelease = { onEvent(DiscoverPageEvents.OnDetachMapView) },
            moveToCurrentLocation = { onEvent(DiscoverPageEvents.OnMoveToCurrentUserLocation) },
            expandActionVisible = false,
            expanded = false,
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item { Spacer(Modifier) }
            when (eventsState) {
                is EventListState.Error -> {
                    item {
                        ErrorForm(
                            modifier = Modifier.fillMaxWidth(),
                            errorDescription = Res.string.something_went_wrong,
                            onRetry = { onEvent(DiscoverPageEvents.OnRetryLoadEvents) }
                        )
                    }
                }

                EventListState.Loading -> {
                    item {
                        LoadingForm(
                            Modifier.fillMaxWidth(),
                            stringResource(Res.string.loading_events)
                        )
                    }
                }

                is EventListState.Success -> {
                    items(eventsState.events) { event ->
                        MiniEventItem(
                            modifier = Modifier.fillMaxWidth(),
                            event
                        ) {
                            onEvent(DiscoverPageEvents.OnNavigateEvent(event.id))
                        }
                    }
                    item {
                        Spacer(Modifier.height(120.dp))
                    }
                }
            }
        }

    }
}