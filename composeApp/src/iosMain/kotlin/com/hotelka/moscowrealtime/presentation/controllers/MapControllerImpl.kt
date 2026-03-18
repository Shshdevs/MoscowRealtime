package com.hotelka.moscowrealtime.presentation.controllers

import cocoapods.YandexMapsMobile.YMKAnimation
import cocoapods.YandexMapsMobile.YMKAnimationType
import cocoapods.YandexMapsMobile.YMKBoundingBox
import cocoapods.YandexMapsMobile.YMKCameraPosition
import cocoapods.YandexMapsMobile.YMKCameraUpdateReason
import cocoapods.YandexMapsMobile.YMKGeometry
import cocoapods.YandexMapsMobile.YMKMap
import cocoapods.YandexMapsMobile.YMKMapCameraListenerProtocol
import cocoapods.YandexMapsMobile.YMKMapView
import cocoapods.YandexMapsMobile.YMKPlacemarkMapObject
import cocoapods.YandexMapsMobile.YMKPoint
import cocoapods.YandexMapsMobile.YMKPolyline
import cocoapods.YandexMapsMobile.YMKPolylineMapObject
import cocoapods.YandexMapsMobile.YMKTextStyle
import cocoapods.YandexMapsMobile.YMKTextStylePlacement
import com.hotelka.moscowrealtime.domain.model.mapData.GeoPoint
import com.hotelka.moscowrealtime.domain.model.mapData.MapCameraState
import com.hotelka.moscowrealtime.domain.repository.GeoLocationHandler
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import platform.UIKit.UIColor
import platform.UIKit.UIImage
import platform.darwin.NSObject
import kotlin.math.max
import kotlin.math.min

@OptIn(ExperimentalForeignApi::class)
actual class MapControllerImpl actual constructor(
    geoLocationHandler: GeoLocationHandler
) : MapController {

    private var mapView: YMKMapView? = null

    private var routePolyline: YMKPolylineMapObject? = null
    private var userPlacemark: YMKPlacemarkMapObject? = null
    private val pointPlacemarks = mutableListOf<YMKPlacemarkMapObject>()

    private var trackingJob: Job? = null
    private val locationFlow = geoLocationHandler.locationUpdates()

    private var savedCameraState: MapCameraState? = null
    private var pendingCameraAction: (() -> Unit)? = null
    private var lastRenderedPoints: List<GeoPoint> = emptyList()
    private var lastRenderedRouteGeometry: List<GeoPoint> = emptyList()

    private val markerImage by lazy { UIImage.imageNamed("location_mark") }
    private val userImage by lazy { UIImage.imageNamed("user_location") }

    private val cameraListener = object : NSObject(), YMKMapCameraListenerProtocol {
        override fun onCameraPositionChangedWithMap(
            map: YMKMap,
            cameraPosition: YMKCameraPosition,
            cameraUpdateReason: YMKCameraUpdateReason,
            finished: Boolean
        ) {
            savedCameraState = MapCameraState(
                lat = cameraPosition.target.latitude,
                lon = cameraPosition.target.longitude,
                zoom = cameraPosition.zoom,
                azimuth = cameraPosition.azimuth,
                tilt = cameraPosition.tilt
            )
        }
    }

    actual override fun attachMapView(mapView: Any) {
        val newMapView = mapView as YMKMapView

        if (this.mapView === newMapView) {
            onMapLaidOut()
            return
        }

        this.mapView?.mapWindow?.map?.removeCameraListenerWithCameraListener(cameraListener)

        this.mapView = newMapView
        newMapView.mapWindow?.map?.addCameraListenerWithCameraListener(cameraListener)

        onMapLaidOut()
    }

    actual override fun detachMapView() {
        mapView?.mapWindow?.map?.removeCameraListenerWithCameraListener(cameraListener)
        mapView = null
        routePolyline = null
        userPlacemark = null
        pointPlacemarks.clear()
        pendingCameraAction = null
    }

    actual override fun moveTo(lat: Double, lon: Double, zoom: Float) {
        runOrDeferCameraAction {
            val map = mapView?.mapWindow?.map ?: return@runOrDeferCameraAction

            val point = YMKPoint.pointWithLatitude(lat, lon)
            val cameraPosition = YMKCameraPosition.cameraPositionWithTarget(
                target = point,
                zoom = zoom,
                azimuth = 0f,
                tilt = 0f
            )

            map.moveWithCameraPosition(
                cameraPosition,
                YMKAnimation.animationWithType(
                    YMKAnimationType.YMKAnimationTypeSmooth,
                    0.5f
                ),
                null
            )
        }
    }

    actual override fun moveToCurrentUserLocation() {
        val point = userPlacemark?.geometry ?: return
        moveTo(point.latitude, point.longitude, 20f)
    }

    actual override fun clearMapObjects() {
        val mapObjects = mapView?.mapWindow?.map?.mapObjects ?: return
        mapObjects.clear()

        userPlacemark = null
        routePolyline = null
        pointPlacemarks.clear()
        lastRenderedPoints = emptyList()
        lastRenderedRouteGeometry = emptyList()
    }

    actual override fun clearRoute() {
        routePolyline?.let { mapView?.mapWindow?.map?.mapObjects?.removeWithMapObject(it) }
        routePolyline = null
        lastRenderedRouteGeometry = emptyList()
    }

    actual override fun setUserLocation(
        lat: Double,
        lon: Double,
        onSetUserLocation: (GeoPoint) -> Unit
    ) {
        val mapObjects = mapView?.mapWindow?.map?.mapObjects ?: return
        val point = YMKPoint.pointWithLatitude(lat, lon)

        if (userPlacemark == null) {
            userPlacemark = userImage?.let { image ->
                mapObjects.addPlacemarkWithPoint(point, image)
            }
        } else {
            userPlacemark?.geometry = point
        }

        onSetUserLocation(GeoPoint(lat, lon, ""))
    }

    actual override fun startTrackingLocation(
        onUpdateUserPlacemark: (GeoPoint) -> Unit,
        onSetUserLocation: (GeoPoint) -> Unit
    ) {
        trackingJob?.cancel()
        trackingJob = CoroutineScope(Dispatchers.Main).launch {
            locationFlow.collect { location ->
                if (userPlacemark == null) {
                    setUserLocation(
                        lat = location.latitude,
                        lon = location.longitude,
                        onSetUserLocation = onSetUserLocation
                    )
                } else {
                    setUserLocation(
                        lat = location.latitude,
                        lon = location.longitude,
                        onSetUserLocation = onUpdateUserPlacemark
                    )
                }
            }
        }
    }

    actual override fun stopTrackingLocation() {
        trackingJob?.cancel()
        trackingJob = null
    }

    actual override fun restoreCameraState(cameraState: MapCameraState) {
        savedCameraState = cameraState

        runOrDeferCameraAction {
            val map = mapView?.mapWindow?.map ?: return@runOrDeferCameraAction

            map.moveWithCameraPosition(
                YMKCameraPosition.cameraPositionWithTarget(
                    YMKPoint.pointWithLatitude(cameraState.lat, cameraState.lon),
                    cameraState.zoom,
                    cameraState.azimuth,
                    cameraState.tilt
                ),
                YMKAnimation.animationWithType(
                    YMKAnimationType.YMKAnimationTypeSmooth,
                    0.3f
                ),
                null
            )
        }
    }

    actual override fun renderState(
        points: List<GeoPoint>,
        routeGeometry: List<GeoPoint>
    ) {
        lastRenderedPoints = points
        lastRenderedRouteGeometry = routeGeometry

        val map = mapView?.mapWindow?.map ?: return
        val mapObjects = map.mapObjects

        updatePointMarkers(points, mapObjects)
        updateRoute(routeGeometry, mapObjects)

        val oldUserPoint = userPlacemark?.geometry?.let {
            YMKPoint.pointWithLatitude(it.latitude, it.longitude)
        }

        val allPoints = buildList {
            oldUserPoint?.let(::add)
            addAll(points.map { YMKPoint.pointWithLatitude(it.lat, it.lon) })
            addAll(routeGeometry.map { YMKPoint.pointWithLatitude(it.lat, it.lon) })
        }.distinctBy { "${it.latitude}_${it.longitude}" }

        if (allPoints.isNotEmpty()) {
            setCameraPositionWithPoints(allPoints)
        }
    }

    fun onMapLaidOut() {
        if (!isMapReadyForCamera()) return

        pendingCameraAction?.invoke()
        pendingCameraAction = null

        if (lastRenderedPoints.isNotEmpty() || lastRenderedRouteGeometry.isNotEmpty()) {
            val currentPoints = lastRenderedPoints
            val currentRoute = lastRenderedRouteGeometry
            lastRenderedPoints = emptyList()
            lastRenderedRouteGeometry = emptyList()
            renderState(currentPoints, currentRoute)
        } else if (savedCameraState != null) {
            val state = savedCameraState ?: return
            restoreCameraState(state)
        }
    }

    private fun updatePointMarkers(
        points: List<GeoPoint>,
        mapObjects: cocoapods.YandexMapsMobile.YMKMapObjectCollection
    ) {
        pointPlacemarks.forEach { mapObjects.removeWithMapObject(it) }
        pointPlacemarks.clear()

        points.forEach { geoPoint -> addMarkerToCurrentMap(geoPoint)?.let(pointPlacemarks::add) }
    }

    private fun updateRoute(
        routeGeometry: List<GeoPoint>,
        mapObjects: cocoapods.YandexMapsMobile.YMKMapObjectCollection
    ) {
        routePolyline?.let { mapObjects.removeWithMapObject(it) }
        routePolyline = null

        if (routeGeometry.isEmpty()) return

        val mapPoints = routeGeometry.map {
            YMKPoint.pointWithLatitude(it.lat, it.lon)
        }

        routePolyline = mapObjects.addPolylineWithPolyline(
            YMKPolyline.polylineWithPoints(mapPoints)
        ).apply {
            setStrokeColorWithColor(
                UIColor(
                    red = blue.red.toDouble(),
                    green = blue.green.toDouble(),
                    blue = blue.blue.toDouble(),
                    alpha = blue.alpha.toDouble()
                )
            )
            strokeWidth = 0.8f
        }
    }

    private fun addMarkerToCurrentMap(point: GeoPoint): YMKPlacemarkMapObject? {
        val mapObjects = mapView?.mapWindow?.map?.mapObjects ?: return null
        val markerPoint = YMKPoint.pointWithLatitude(point.lat, point.lon)

        val placemark = markerImage?.let { image ->
            mapObjects.addPlacemarkWithPoint(markerPoint, image)
        } ?: return null

        val titleColor = UIColor(
            red = secondaryTextColor.red.toDouble(),
            green = secondaryTextColor.green.toDouble(),
            blue = secondaryTextColor.blue.toDouble(),
            alpha = secondaryTextColor.alpha.toDouble()
        )

        placemark.setTextWithText(
            point.title,
            YMKTextStyle.textStyleWithSize(
                size = 8.0f,
                color = titleColor,
                outlineWidth = 1.0f,
                outlineColor = titleColor,
                placement = YMKTextStylePlacement.YMKTextStylePlacementBottom,
                offset = 0.0f,
                offsetFromIcon = true,
                textOptional = false
            )
        )

        return placemark
    }

    private fun setCameraPositionWithPoints(points: List<YMKPoint>) {
        runOrDeferCameraAction {
            applyCameraPositionWithPoints(points)
        }
    }

    private fun applyCameraPositionWithPoints(points: List<YMKPoint>) {
        val map = mapView?.mapWindow?.map ?: return
        if (points.isEmpty() || !isMapReadyForCamera()) return

        val boundingGeometry = calculateBoundingBox(points)
        val cameraPosition = map.cameraPositionWithGeometry(boundingGeometry)

        map.moveWithCameraPosition(
            YMKCameraPosition.cameraPositionWithTarget(
                cameraPosition.target,
                cameraPosition.zoom - 0.5f,
                cameraPosition.azimuth,
                cameraPosition.tilt
            ),
            YMKAnimation.animationWithType(
                YMKAnimationType.YMKAnimationTypeSmooth,
                1.0f
            ),
            null
        )
    }

    private fun runOrDeferCameraAction(action: () -> Unit) {
        if (isMapReadyForCamera()) {
            pendingCameraAction = null
            action()
        } else {
            pendingCameraAction = action
        }
    }

    private fun isMapReadyForCamera(): Boolean {
        val currentMapView = mapView ?: return false
        val mapWindow = currentMapView.mapWindow ?: return false
        return mapWindow.width() > 0f && mapWindow.height() > 0f
    }

    private fun calculateBoundingBox(points: List<YMKPoint>): YMKGeometry {
        var minLat = points.minOf { it.latitude }
        var maxLat = points.maxOf { it.latitude }
        var minLon = points.minOf { it.longitude }
        var maxLon = points.maxOf { it.longitude }

        for (point in points) {
            minLat = min(minLat, point.latitude)
            maxLat = max(maxLat, point.latitude)
            minLon = min(minLon, point.longitude)
            maxLon = max(maxLon, point.longitude)
        }

        return YMKGeometry.geometryWithBoundingBox(
            YMKBoundingBox.boundingBoxWithSouthWest(
                southWest = YMKPoint.pointWithLatitude(minLat, minLon),
                northEast = YMKPoint.pointWithLatitude(maxLat, maxLon)
            )
        )
    }
}