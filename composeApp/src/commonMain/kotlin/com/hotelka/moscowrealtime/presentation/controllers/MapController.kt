package com.hotelka.moscowrealtime.presentation.controllers

import com.hotelka.moscowrealtime.domain.model.mapData.GeoPoint
import com.hotelka.moscowrealtime.domain.model.mapData.MapCameraState
import com.hotelka.moscowrealtime.domain.model.mapData.Route

interface MapController {
    fun attachMapView(mapView: Any)
    fun detachMapView()

    fun moveTo(lat: Double, lon: Double, zoom: Float)
    fun moveToCurrentUserLocation()

    fun clearMapObjects()
    fun clearRoute()

    fun setUserLocation(
        lat: Double,
        lon: Double,
        onSetUserLocation: (GeoPoint) -> Unit
    )

    fun startTrackingLocation(
        onUpdateUserPlacemark: (GeoPoint) -> Unit,
        onSetUserLocation: (GeoPoint) -> Unit
    )

    fun stopTrackingLocation()
    fun restoreCameraState(cameraState: MapCameraState)

    fun renderState(
        points: List<GeoPoint>,
        routeGeometry: List<GeoPoint>
    )
}
