package com.hotelka.moscowrealtime.data.repository

import com.hotelka.moscowrealtime.domain.model.GeoLocation
import com.hotelka.moscowrealtime.domain.repository.GeoLocationHandler
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.CoreLocation.kCLLocationAccuracyHundredMeters
import platform.CoreLocation.kCLLocationAccuracyKilometer
import platform.CoreLocation.kCLLocationAccuracyNearestTenMeters
import platform.Foundation.NSError
import platform.Foundation.timeIntervalSince1970
import platform.darwin.NSObject
import kotlin.coroutines.resume

actual class GeoLocationHandlerImpl actual constructor() : GeoLocationHandler {

    private val locationManager = CLLocationManager().apply {
        desiredAccuracy = kCLLocationAccuracyBest
    }

    @OptIn(ExperimentalForeignApi::class)
    actual override fun locationUpdates(interval: Long): Flow<GeoLocation> = callbackFlow {

        val delegate = object : NSObject(), CLLocationManagerDelegateProtocol {

            override fun locationManager(
                manager: CLLocationManager,
                didUpdateLocations: List<*>
            ) {

                val location = didUpdateLocations.lastOrNull() as? CLLocation ?: return

                val geoLocation = GeoLocation(
                    latitude = location.coordinate.useContents { latitude },
                    longitude = location.coordinate.useContents { longitude },
                    accuracy = location.horizontalAccuracy.toFloat(),
                    timestamp = location.timestamp.timeIntervalSince1970.toLong() * 1000
                )

                trySend(geoLocation)
            }

            override fun locationManager(
                manager: CLLocationManager,
                didFailWithError: NSError
            ) {
                close(Exception(didFailWithError.localizedDescription))
            }
        }

        locationManager.delegate = delegate
        locationManager.requestWhenInUseAuthorization()
        locationManager.startUpdatingLocation()

        awaitClose {
            locationManager.stopUpdatingLocation()
        }
    }

    actual override suspend fun getCurrentLocation(): GeoLocation? =
        locationUpdates().firstOrNull()

    actual override fun checkPermissions(): Result<Boolean> {
        return when (CLLocationManager.authorizationStatus()) {
            kCLAuthorizationStatusAuthorizedWhenInUse,
            kCLAuthorizationStatusAuthorizedAlways -> Result.success(true)
            else -> Result.success(false)
        }
    }
}