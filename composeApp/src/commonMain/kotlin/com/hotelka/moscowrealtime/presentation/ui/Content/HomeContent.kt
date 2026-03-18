package com.hotelka.moscowrealtime.presentation.ui.Content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.presentation.events.HomePageEvents
import com.hotelka.moscowrealtime.presentation.model.HomePageModel
import com.hotelka.moscowrealtime.presentation.state.listState.EventListState
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.NearYouWidget
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.ErrorForm
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.LoadingForm
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.HomeHeader
import com.hotelka.moscowrealtime.presentation.ui.Content.successState.HomePageSuccessState
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.loading_events
import moscowrealtime.composeapp.generated.resources.something_went_wrong
import org.jetbrains.compose.resources.stringResource


@Composable
fun HomeContent(
    homeState: HomePageModel,
    onEvent: (HomePageEvents) -> Unit
) {
    Box(Modifier.fillMaxSize().background(background)) {
        when (homeState.events) {
            is EventListState.Success -> {
                HomePageSuccessState(
                    homeState,
                    onEvent
                )
            }

            is EventListState.Loading -> {
                LazyColumn(
                    Modifier.fillMaxSize().padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    item("header") {
                        HomeHeader(homeState.greeting)
                    }
                    item("near_location") {
                        NearYouWidget(null) {}
                    }
                    item {
                        LoadingForm(
                            modifier = Modifier.fillMaxSize(),
                            stringResource(Res.string.loading_events)
                        )
                    }
                }
            }

            is EventListState.Error -> {
                Column {
                    HomeHeader(homeState.greeting)

                    ErrorForm(
                        modifier = Modifier.fillMaxWidth().padding(top = 40.dp),
                        errorDescription = Res.string.something_went_wrong,
                        onRetry = { onEvent(HomePageEvents.OnRetryLoad) }
                    )
                }
            }
        }
    }
}