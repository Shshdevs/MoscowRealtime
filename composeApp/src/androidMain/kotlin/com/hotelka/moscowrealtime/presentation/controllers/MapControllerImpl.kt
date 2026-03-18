package com.hotelka.moscowrealtime.presentation.controllers

import android.content.Context
import android.graphics.Color
import androidx.compose.ui.graphics.toColorLong
import com.hotelka.moscowrealtime.AndroidContextHolder
import com.hotelka.moscowrealtime.R
import com.hotelka.moscowrealtime.domain.model.mapData.GeoPoint
import com.hotelka.moscowrealtime.domain.model.mapData.MapCameraState
import com.hotelka.moscowrealtime.domain.model.mapData.Route
import com.hotelka.moscowrealtime.domain.repository.GeoLocationHandler
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.BoundingBox
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.map.PolylineMapObject
import com.yandex.mapkit.map.TextStyle
import com.yandex.mapkit.map.internal.PolylineMapObjectBinding
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min

actual class MapControllerImpl actual constructor(
    geoLocationHandler: GeoLocationHandler
) : MapController {

    private val context: Context get() = AndroidContextHolder.applicationContext

    private var mapView: MapView? = null

    private val imageProvider = ImageProvider.fromResource(context, R.drawable.location_mark)
    private val userLocationImageProvider =
        ImageProvider.fromResource(context, R.drawable.user_location)

    private var routePolyline: PolylineMapObject? = null
    private var userPlacemark: PlacemarkMapObject? = null
    private var trackingJob: Job? = null

    private val locationFlow = geoLocationHandler.locationUpdates()
    private var savedCameraState: MapCameraState? = null

    private val cameraListener = CameraListener { _, cameraPosition, _, _ ->
        savedCameraState = MapCameraState(
            lat = cameraPosition.target.latitude,
            lon = cameraPosition.target.longitude,
            zoom = cameraPosition.zoom,
            azimuth = cameraPosition.azimuth,
            tilt = cameraPosition.tilt
        )
    }

    actual override fun attachMapView(mapView: Any) {
        val newMapView = mapView as MapView
        this.mapView = newMapView

        val map = newMapView.mapWindow.map
        map.addCameraListener(cameraListener)

        savedCameraState?.let(::restoreCameraState)
    }

    actual override fun detachMapView() {
        mapView?.mapWindow?.map?.let { map ->
            if (map.isValid) {
                map.removeCameraListener(cameraListener)
            }
        }

        trackingJob?.cancel()
        trackingJob = null

        userPlacemark = null
        routePolyline = null
        mapView = null
    }

    actual override fun moveTo(lat: Double, lon: Double, zoom: Float) {
        val map = mapView?.mapWindow?.map ?: return
        if (!map.isValid) return

        map.move(
            CameraPosition(Point(lat, lon), zoom, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0.5f),
            null
        )
    }

    actual override fun moveToCurrentUserLocation() {
        val placemark = userPlacemark
        if (placemark == null || !placemark.isValid) return

        val point = placemark.geometry
        moveTo(point.latitude, point.longitude, 20f)
    }

    actual override fun clearMapObjects() {
        val map = mapView?.mapWindow?.map ?: return
        if (!map.isValid) return

        map.mapObjects.clear()
        userPlacemark = null
        routePolyline = null
    }

    actual override fun clearRoute() {
        val map = mapView?.mapWindow?.map ?: return
        if (!map.isValid) return

        routePolyline?.takeIf { it.isValid }?.let {
            runCatching { map.mapObjects.remove(it) }
        }
        routePolyline = null
    }

    actual override fun setUserLocation(
        lat: Double,
        lon: Double,
        onSetUserLocation: (GeoPoint) -> Unit
    ) {
        val map = mapView?.mapWindow?.map ?: return
        if (!map.isValid) return

        val mapObjects = map.mapObjects
        val point = Point(lat, lon)

        if (userPlacemark == null || userPlacemark?.isValid == false) {
            userPlacemark = mapObjects.addPlacemark { placemark ->
                placemark.geometry = point
                placemark.setIcon(userLocationImageProvider)
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
        trackingJob = CoroutineScope(Dispatchers.Main.immediate).launch {
            locationFlow.collect { location ->
                if (userPlacemark == null || userPlacemark?.isValid != true) {
                    setUserLocation(location.latitude, location.longitude, onSetUserLocation)
                } else {
                    setUserLocation(location.latitude, location.longitude, onUpdateUserPlacemark)
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

        val map = mapView?.mapWindow?.map ?: return
        if (!map.isValid) return

        map.move(
            CameraPosition(
                Point(cameraState.lat, cameraState.lon),
                cameraState.zoom,
                cameraState.azimuth,
                cameraState.tilt
            ),
            Animation(Animation.Type.SMOOTH, 0.3f),
            null
        )
    }

    actual override fun renderState(
        points: List<GeoPoint>,
        routeGeometry: List<GeoPoint>
    ) {
        val currentMapView = mapView ?: return
        val map = currentMapView.mapWindow.map
        if (!map.isValid) return

        currentMapView.post {
            val postedMapView = mapView ?: return@post
            val postedMap = postedMapView.mapWindow.map
            if (!postedMap.isValid) return@post

            val oldUserPoint = userPlacemark
                ?.takeIf { it.isValid }
                ?.geometry
                ?.let { GeoPoint(it.latitude, it.longitude, "") }

            postedMap.mapObjects.clear()
            userPlacemark = null
            routePolyline = null

            oldUserPoint?.let {
                setUserLocation(it.lat, it.lon) {}
            }

            points.forEachIndexed { index, geoPoint ->
                if (index == 0 && oldUserPoint != null) return@forEachIndexed
                addMarkerToCurrentMap(geoPoint)
            }

            if (routeGeometry.isNotEmpty()) {
                val mapPoints = routeGeometry.map { Point(it.lat, it.lon) }
                routePolyline = postedMap.mapObjects.addPolyline(Polyline(mapPoints)).apply {
                    val color = Color.valueOf(blue.toColorLong()).toArgb()
                    setStrokeColor(color)
                    strokeWidth = 0.8f
                }
            }

            val allPoints = buildList {
                oldUserPoint?.let { add(Point(it.lat, it.lon)) }
                addAll(points.map { Point(it.lat, it.lon) })
                addAll(routeGeometry.map { Point(it.lat, it.lon) })
            }.distinctBy { "${it.latitude}_${it.longitude}" }

            if (allPoints.isNotEmpty()) {
                setCameraPositionWithPoints(allPoints)
            }
        }
    }

    private fun addMarkerToCurrentMap(point: GeoPoint) {
        val map = mapView?.mapWindow?.map ?: return
        if (!map.isValid) return

        map.mapObjects.addPlacemark { placemark ->
            placemark.geometry = Point(point.lat, point.lon)

            val titleColor = Color.valueOf(secondaryTextColor.toColorLong()).toArgb()
            placemark.setText(
                point.title,
                TextStyle(
                    8.0f,
                    titleColor,
                    1.0f,
                    -1,
                    TextStyle.Placement.BOTTOM,
                    0.0f,
                    true,
                    false
                )
            )
            placemark.setIcon(imageProvider)
        }
    }

    private fun setCameraPositionWithPoints(points: List<Point>) {
        val map = mapView?.mapWindow?.map ?: return
        if (!map.isValid) return
        if (points.isEmpty()) return

        val boundingBox = calculateBoundingBox(points)
        val cameraPosition = map.cameraPosition(boundingBox)

        cameraPosition.let {
            map.move(
                CameraPosition(
                    it.target,
                    it.zoom - 0.5f,
                    it.azimuth,
                    it.tilt
                ),
                Animation(Animation.Type.SMOOTH, 1.0f),
                null
            )
        }
    }

    private fun calculateBoundingBox(points: List<Point>): com.yandex.mapkit.geometry.Geometry {
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

        return com.yandex.mapkit.geometry.Geometry.fromBoundingBox(
            BoundingBox(
                Point(minLat, minLon),
                Point(maxLat, maxLon)
            )
        )
    }
}
