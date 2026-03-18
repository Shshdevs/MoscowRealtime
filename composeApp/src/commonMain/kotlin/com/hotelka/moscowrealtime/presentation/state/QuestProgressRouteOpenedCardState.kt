package com.hotelka.moscowrealtime.presentation.state

import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.domain.model.detailed.DiscoverDetailed

sealed class QuestProgressRouteOpenedCardState {
    object Quest : QuestProgressRouteOpenedCardState()
    data class CurrentLocation(val location: Location) : QuestProgressRouteOpenedCardState()
    data class ReachedLocation(val location: Location, val discover: DiscoverDetailed) :
        QuestProgressRouteOpenedCardState()
}