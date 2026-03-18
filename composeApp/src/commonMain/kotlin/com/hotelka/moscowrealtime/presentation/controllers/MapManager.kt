package com.hotelka.moscowrealtime.presentation.controllers

import co.touchlab.kermit.Logger
import com.hotelka.moscowrealtime.domain.model.mapData.GeoPoint
import com.hotelka.moscowrealtime.domain.model.mapData.Route
import com.hotelka.moscowrealtime.domain.routing.RouteBuilder

class MapManager(
    private val mapController: MapController,
    private val routeBuilder: RouteBuilder
) {
    private val points = mutableListOf<GeoPoint>()
    private var routeGeometry: List<GeoPoint> = emptyList()

    fun attachMap(
        mapView: Any,
        restoreMapObjects: Boolean = false,
        onSetUserLocation: (() -> Unit)?
    ) {
        mapController.attachMapView(mapView)

        if (onSetUserLocation != null) {
            mapController.startTrackingLocation(
                onUpdateUserPlacemark = { userGeoPoint ->
                    if (points.isNotEmpty()) {
                        points[0] = userGeoPoint
                    }
                },
                onSetUserLocation = { userGeoPoint ->
                    if (points.isEmpty()) {
                        points.add(userGeoPoint)
                    } else {
                        points[0] = userGeoPoint
                    }
                    onSetUserLocation()
                }
            )
        }

        if (restoreMapObjects) {
            restoreMapObjects()
        }
    }

    fun detachMap() {
        mapController.stopTrackingLocation()
        mapController.detachMapView()
    }

    fun restoreMapObjects() {
        mapController.renderState(
            points = points,
            routeGeometry = routeGeometry
        )
    }

    fun addMarker(point: GeoPoint) {
        points.add(point)
        mapController.renderState(
            points = points,
            routeGeometry = routeGeometry
        )
    }
    fun removeMarker(point: GeoPoint) {
        points.remove(point)
        mapController.renderState(
            points = points,
            routeGeometry = routeGeometry
        )
    }

    fun clearMap() {
        points.clear()
        routeGeometry = emptyList()
        mapController.clearMapObjects()
    }

    fun moveToUserCurrentLocation() {
        mapController.moveToCurrentUserLocation()
    }

    fun moveTo(point: GeoPoint, zoom: Float = 15f) {
        mapController.moveTo(point.lat, point.lon, zoom)
    }

    suspend fun updateRoute(points: List<GeoPoint>): Result<Route>{
        this@MapManager.points.apply {
            clear()
            addAll(points)
        }

        val result = routeBuilder.buildRoute(this@MapManager.points)
        result.onSuccess { route ->
            routeGeometry = route.geometry
            mapController.renderState(
                points = points,
                routeGeometry = routeGeometry
            )
        }
        return result
    }
    suspend fun removeRoutePoint(point: GeoPoint): Result<Route> {
        points.remove(point)

        val result = routeBuilder.buildRoute(points)
        result.onSuccess { route ->
            routeGeometry = route.geometry
            mapController.renderState(
                points = points,
                routeGeometry = routeGeometry
            )
        }
        return result
    }

    suspend fun addRoutePoint(point: GeoPoint): Result<Route> {
        points.add(point)

        val result = routeBuilder.buildRoute(points)
        result.onSuccess { route ->
            routeGeometry = route.geometry
            mapController.renderState(
                points = points,
                routeGeometry = routeGeometry
            )
        }
        return result
    }

    fun clearRoute() {
        val userPlacemark = points.getOrNull(0)

        points.clear()
        userPlacemark?.let(points::add)

        routeGeometry = emptyList()

        mapController.renderState(
            points = points,
            routeGeometry = routeGeometry
        )
    }
}
