package com.hotelka.moscowrealtime.presentation.ui.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.hotelka.moscowrealtime.presentation.ui.Content.EventPageContent
import com.hotelka.moscowrealtime.presentation.viewmodel.EventGraphViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventPage(
    eventId: Int,
    eventGraphViewModel: EventGraphViewModel
) {
    LaunchedEffect(eventId) {
        eventGraphViewModel.getEvent(eventId)
    }
    val eventState by eventGraphViewModel.eventState.collectAsState()

    EventPageContent(
        eventState = eventState.eventState,
        onEvent = eventGraphViewModel::onEvent
    )
}

