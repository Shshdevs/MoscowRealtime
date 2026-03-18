package com.hotelka.moscowrealtime.presentation.events

import com.hotelka.moscowrealtime.domain.model.mapData.GeoPoint

sealed class DiscoverPageEvents {
    object OnChangeAroundEventsOpen : DiscoverPageEvents()
    object OnRetryLoadEvents : DiscoverPageEvents()
    data class OnNavigateEvent(val eventId: Int) : DiscoverPageEvents()

    object OnDetachMapView:DiscoverPageEvents()
    object OnMoveToCurrentUserLocation : DiscoverPageEvents()
    data class OnSetMapView(val mapView: Any, val onSetMapView:() -> Unit) : DiscoverPageEvents()
    data class OnAddMarker(val geoPoint: GeoPoint) : DiscoverPageEvents()
    data class OnMoveTo(val geoPoint: GeoPoint) : DiscoverPageEvents()

}