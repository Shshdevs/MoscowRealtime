package com.hotelka.moscowrealtime.presentation.ui.Content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.events.EventGraphEvents
import com.hotelka.moscowrealtime.presentation.state.EventState
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.ErrorForm
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.LoadingForm
import com.hotelka.moscowrealtime.presentation.ui.Content.successState.EventPageSuccessState
import com.hotelka.moscowrealtime.presentation.ui.Custom.shimmerLoading
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.loading_events
import moscowrealtime.composeapp.generated.resources.something_went_wrong
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventPageContent(
    eventState: EventState,
    onEvent: (EventGraphEvents) -> Unit
) {

    Column(
        Modifier
            .fillMaxSize()
            .background(background),
    ) {
        when (eventState) {
            EventState.Loading -> {
                Column {
                    Box(
                        modifier = Modifier
                            .height(350.dp)
                            .fillMaxWidth()
                            .shimmerLoading()
                    )
                    LoadingForm(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(background),
                        loadingAction = stringResource(Res.string.loading_events)
                    )
                }
            }

            is EventState.Success -> {
                EventPageSuccessState(
                    event = eventState.event,
                    onNavigateBack = { onEvent(EventGraphEvents.OnGoBack) },
                    onOpenLink = { onEvent(EventGraphEvents.OnOpenEventLink) },
                    navigateEventMapRoute = { onEvent(EventGraphEvents.OnNavigateEventMap) }
                )
            }

            else -> {
                ErrorForm(
                    modifier = Modifier.fillMaxSize(),
                    errorDescription = Res.string.something_went_wrong,
                    onRetry = { onEvent(EventGraphEvents.OnRetryLoadEvent) }
                )
            }
        }
    }
}
