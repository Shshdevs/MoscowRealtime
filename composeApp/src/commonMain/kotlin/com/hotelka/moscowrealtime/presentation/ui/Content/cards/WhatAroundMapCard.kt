package com.hotelka.moscowrealtime.presentation.ui.Content.cards

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.presentation.events.DiscoverPageEvents
import com.hotelka.moscowrealtime.presentation.state.listState.EventListState
import com.hotelka.moscowrealtime.presentation.ui.Content.frames.DismissableCardFrame
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.DismissableCardHeader
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.what_around

@Composable
fun WhatAroundMapCard(
    modifier: Modifier,
    location: Location,
    eventsStateList: EventListState,
    onEvent: (DiscoverPageEvents) -> Unit,
) {
    DismissableCardFrame(
        modifier,
        onDismissRequest = { onEvent(DiscoverPageEvents.OnChangeAroundEventsOpen) },
        content = {
            DismissableCardHeader(Res.string.what_around)
            WhatAroundMapCardContent(
                eventsState = eventsStateList,
                location = location,
                onEvent = onEvent
            )
        }
    )
}