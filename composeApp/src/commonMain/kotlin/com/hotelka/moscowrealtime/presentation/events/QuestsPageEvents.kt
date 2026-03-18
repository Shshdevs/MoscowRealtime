package com.hotelka.moscowrealtime.presentation.events

import com.hotelka.moscowrealtime.domain.model.mapData.GeoPoint

sealed class QuestsPageEvents {
    object OnRetryLoad: QuestsPageEvents()
    object OnMapExpandedChange : QuestsPageEvents()
    object OnNavigateQuestsLibrary : QuestsPageEvents()
    object OnNavigateQuestRoute : QuestsPageEvents()
    data class OnNavigateQuestPage(val questId: String) : QuestsPageEvents()
    object OnDetachMap : QuestsPageEvents()
    object OnMoveToUserLocation : QuestsPageEvents()
    data class OnAttachMap(val mapView: Any, val onSetUserLocationMarker: () -> Unit) :
        QuestsPageEvents()

    data class OnAddPlacemark(val geoPoint: GeoPoint) : QuestsPageEvents()

}