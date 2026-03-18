package com.hotelka.moscowrealtime.data.repository

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.hotelka.moscowrealtime.domain.model.GeoLocation
import com.hotelka.moscowrealtime.domain.repository.GeoLocationHandler
import com.hotelka.moscowrealtime.AndroidContextHolder
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
actual class GeoLocationHandlerImpl actual constructor() : GeoLocationHandler {
    val context: Context get() = AndroidContextHolder.applicationContext

    private var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    actual override suspend fun getCurrentLocation(): GeoLocation? {
        return suspendCancellableCoroutine { continuation ->
            if (!isLocationEnabled()) {
                continuation.resume(null)
                return@suspendCancellableCoroutine
            }
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null && isLocationFresh(location)) {
                        continuation.resume(
                            GeoLocation(
                                latitude = location.latitude,
                                longitude = location.longitude,
                                accuracy = location.accuracy,
                                timestamp = location.time
                            )
                        )
                    } else {
                        requestCurrentLocation(continuation)
                    }
                }
                .addOnFailureListener {
                    requestCurrentLocation(continuation)
                }
        }
    }



    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    private fun requestCurrentLocation(continuation: CancellableContinuation<GeoLocation?>) {
        try {
            val cancellationTokenSource = CancellationTokenSource()
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource.token
            ).addOnSuccessListener { location ->
                if (location != null) {
                    val geoLocation = GeoLocation(
                        latitude = location.latitude,
                        longitude = location.longitude,
                        accuracy = location.accuracy,
                        timestamp = location.time
                    )
                    continuation.resume(geoLocation)
                } else {
                    continuation.resume(null)
                }
            }.addOnFailureListener { e ->
                continuation.resume(null)
            }
        } catch (e: Exception) {
            continuation.resume(null)
        }
    }

    private fun isLocationFresh(location: Location, maxAgeSeconds: Long = 1L): Boolean {
        val currentTime = System.currentTimeMillis()
        return (currentTime - location.time) < (maxAgeSeconds * 1000)
    }

    private fun isLocationEnabled(): Boolean {
        return try {
            val locationManager: LocationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (e: Exception) {
            false
        }
    }

    actual override fun checkPermissions(): Result<Boolean> = try {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
        Result.success(
            permissions.any {
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }
        )
    } catch (e: Exception) {
        Result.failure(Exception("Error checking permissions", e))
    }
    @RequiresApi(Build.VERSION_CODES.S)
    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    actual override fun locationUpdates(interval: Long): Flow<GeoLocation> = callbackFlow  {

        if (!isLocationEnabled()) {
            close(Exception("Location disabled"))
            return@callbackFlow
        }

        val request: LocationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,interval).build()

        val callback: LocationCallback = object : LocationCallback() {

            override fun onLocationResult(result: LocationResult) {

                val location = result.lastLocation ?: return

                val geoLocation = GeoLocation(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    accuracy = location.accuracy,
                    timestamp = location.time
                )

                trySend(geoLocation)
            }
        }

        fusedLocationClient.requestLocationUpdates(request, callback, Looper.getMainLooper() )

        awaitClose {
            fusedLocationClient.removeLocationUpdates(callback)
        }
    }
}