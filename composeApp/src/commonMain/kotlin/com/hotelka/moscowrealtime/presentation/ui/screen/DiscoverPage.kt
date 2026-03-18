package com.hotelka.moscowrealtime.presentation.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.domain.model.detailed.DiscoverDetailed
import com.hotelka.moscowrealtime.presentation.events.DiscoverPageEvents
import com.hotelka.moscowrealtime.presentation.ui.Content.DiscoverContent
import com.hotelka.moscowrealtime.presentation.ui.Content.anim.SlideVerticallyCardAnim
import com.hotelka.moscowrealtime.presentation.ui.Content.cards.WhatAroundMapCard
import com.hotelka.moscowrealtime.presentation.viewmodel.DiscoverPageViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DiscoverPage(
    discoverDetailed: DiscoverDetailed,
    enableShowWhatAround: Boolean = true,
    discoverPageViewModel: DiscoverPageViewModel = koinViewModel(),
    onDismiss: () -> Unit
) {
    LaunchedEffect(discoverDetailed) {
        discoverPageViewModel.setDiscover(discoverDetailed)
    }
    val discoverPageUiModel by discoverPageViewModel.discoverUiState.collectAsState()

    discoverPageUiModel.discover?.let { uiModel ->
        DiscoverContent(
            enableShowWhatAround = enableShowWhatAround,
            discoverDetailed = uiModel,
            onDismiss = onDismiss,
            onShowAround = { discoverPageViewModel.onEvent(DiscoverPageEvents.OnChangeAroundEventsOpen) },
        )
    }

    SlideVerticallyCardAnim(
        modifier = Modifier.fillMaxSize().padding(top = 40.dp),
        visible = discoverPageUiModel.showAroundEvent,
        content = {
            discoverPageUiModel.discover?.location?.let { location ->
                WhatAroundMapCard(
                    modifier = Modifier,
                    location = location,
                    eventsStateList = discoverPageUiModel.aroundEvents,
                    onEvent = discoverPageViewModel::onEvent,
                )
            }
        }
    )
}
