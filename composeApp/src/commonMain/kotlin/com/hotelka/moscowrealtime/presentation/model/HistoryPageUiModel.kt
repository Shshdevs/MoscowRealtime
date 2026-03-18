package com.hotelka.moscowrealtime.presentation.model

import com.hotelka.moscowrealtime.domain.model.detailed.DiscoverDetailed
import com.hotelka.moscowrealtime.presentation.state.listState.DiscoversListState

data class HistoryPageUiModel(
    val discoversListState: DiscoversListState = DiscoversListState.Loading,
    val discoverOpened: DiscoverDetailed? = null
)