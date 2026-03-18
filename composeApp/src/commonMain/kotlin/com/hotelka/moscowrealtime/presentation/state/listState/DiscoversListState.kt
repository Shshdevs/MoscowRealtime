package com.hotelka.moscowrealtime.presentation.state.listState

import com.hotelka.moscowrealtime.domain.model.detailed.DiscoverDetailed

sealed class DiscoversListState {
    object Loading: DiscoversListState()
    data class Success(val discovers: List<DiscoverDetailed>): DiscoversListState()
    data class Error(val message: String): DiscoversListState()
}