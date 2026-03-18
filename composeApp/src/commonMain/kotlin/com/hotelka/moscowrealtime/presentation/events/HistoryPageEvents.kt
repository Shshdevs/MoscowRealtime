package com.hotelka.moscowrealtime.presentation.events

import com.hotelka.moscowrealtime.domain.model.detailed.DiscoverDetailed

sealed class HistoryPageEvents {
    object OnRetryLoad: HistoryPageEvents()
    object OnNavigateQuestsPage : HistoryPageEvents()
    data class OnChangeDiscoverOpened(val discover: DiscoverDetailed?) : HistoryPageEvents()
}