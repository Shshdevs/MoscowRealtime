package com.hotelka.moscowrealtime.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.hotelka.moscowrealtime.presentation.ui.Content.EventMapRouteContent
import com.hotelka.moscowrealtime.presentation.viewmodel.EventGraphViewModel

@Composable
fun EventMapRoutePage(
    eventGraphViewModel: EventGraphViewModel
) {
    val eventGraphState by eventGraphViewModel.eventState.collectAsState()
    EventMapRouteContent(
        eventGraphUiModel = eventGraphState,
        onEvent = eventGraphViewModel::onEvent
    )
}