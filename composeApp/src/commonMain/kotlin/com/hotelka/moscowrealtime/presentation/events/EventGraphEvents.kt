package com.hotelka.moscowrealtime.presentation.events

import com.hotelka.moscowrealtime.domain.model.mapData.GeoPoint

sealed class EventGraphEvents {
    data class OnAttachMap(val mapView: Any) : EventGraphEvents()
    object OnDetachMap : EventGraphEvents()
    object OnMoveToUserLocation : EventGraphEvents()
    object OnOpenEventLink : EventGraphEvents()
    object OnGoBack : EventGraphEvents()
    object OnNavigateEventMap : EventGraphEvents()
    object OnRetryLoadEvent : EventGraphEvents()
    data class OnAddPlacemark(val geoPoint: GeoPoint) : EventGraphEvents()
}