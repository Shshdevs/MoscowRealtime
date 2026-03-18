package com.hotelka.moscowrealtime.presentation.model

import com.hotelka.moscowrealtime.domain.model.detailed.DiscoverDetailed
import com.hotelka.moscowrealtime.presentation.state.listState.EventListState

data class DiscoverPageUiModel(
    val discover: DiscoverDetailed? = null,
    val aroundEvents: EventListState = EventListState.Loading,
    val showAroundEvent: Boolean = false,
)