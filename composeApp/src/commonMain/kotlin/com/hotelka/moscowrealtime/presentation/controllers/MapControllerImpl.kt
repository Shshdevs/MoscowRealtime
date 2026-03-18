package com.hotelka.moscowrealtime.presentation.controllers

import com.hotelka.moscowrealtime.domain.model.mapData.GeoPoint
import com.hotelka.moscowrealtime.domain.model.mapData.MapCameraState
import com.hotelka.moscowrealtime.domain.model.mapData.Route
import com.hotelka.moscowrealtime.domain.repository.GeoLocationHandler

expect class MapControllerImpl(geoLocationHandler: GeoLocationHandler): MapController {
    override fun attachMapView(mapView: Any)
    override fun detachMapView()
    override fun moveTo(lat: Double, lon: Double, zoom: Float)
    override fun moveToCurrentUserLocation()
    override fun clearMapObjects()
    override fun clearRoute()
    override fun setUserLocation(
        lat: Double,
        lon: Double,
        onSetUserLocation: (GeoPoint) -> Unit
    )

    override fun startTrackingLocation(
        onUpdateUserPlacemark: (GeoPoint) -> Unit,
        onSetUserLocation: (GeoPoint) -> Unit
    )

    override fun stopTrackingLocation()
    override fun restoreCameraState(cameraState: MapCameraState)
    override fun renderState(
        points: List<GeoPoint>,
        routeGeometry: List<GeoPoint>
    )

}