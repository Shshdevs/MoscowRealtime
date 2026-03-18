package com.hotelka.moscowrealtime.presentation.events

import com.hotelka.moscowrealtime.domain.model.Discover
import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.domain.model.mapData.GeoPoint
import com.hotelka.moscowrealtime.domain.model.detailed.DiscoverDetailed
import com.hotelka.moscowrealtime.presentation.state.QuestProgressRouteOpenedCardState
import com.hotelka.moscowrealtime.domain.model.detailed.QuestProgressDetailed

sealed class QuestProgressGraphEvents {
    object OnChangeCameraMode : QuestProgressGraphEvents()
    object OnNavigateBack : QuestProgressGraphEvents()
    object OnRetryLoadQuestProgress : QuestProgressGraphEvents()
    object OnResetVerificationResult : QuestProgressGraphEvents()

    data class OnShowDiscover(val discover: DiscoverDetailed?) : QuestProgressGraphEvents()
    data class OnNavigateMap(val location: Location) : QuestProgressGraphEvents()
    data class OnVerifyResult(val discover: Discover, val questId: String, val score: Int) :
        QuestProgressGraphEvents()

    data class OnSetQuestProgressRouteOpenedCard(val questProgressRouteOpenedCardState: QuestProgressRouteOpenedCardState) :
        QuestProgressGraphEvents()

    object OnChangeShowQuestDone:QuestProgressGraphEvents()
    data class OnRestartQuest(val questProgressDetailed: QuestProgressDetailed):QuestProgressGraphEvents()
    object OnDetachMap : QuestProgressGraphEvents()
    object OnMoveToUserLocation : QuestProgressGraphEvents()
    data class OnAttachMap(val mapView: Any, val onSet:() -> Unit) : QuestProgressGraphEvents()
    data class OnSetPlacemark(val geoPoint: GeoPoint) : QuestProgressGraphEvents()

}