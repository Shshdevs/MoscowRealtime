package com.hotelka.moscowrealtime

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.ComposeUIViewController
import com.hotelka.moscowrealtime.di.appModule
import com.hotelka.moscowrealtime.presentation.ui.App
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.delay
import org.koin.compose.KoinApplication
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse

@OptIn(ExperimentalForeignApi::class)
fun MainViewController() = ComposeUIViewController {

    var hasPermissions by remember { mutableStateOf(hasLocationPermission()) }

    LaunchedEffect(hasPermissions) {
        if (!hasPermissions) {
            requestLocationPermission()
        }
    }
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            val newPermissionStatus = hasLocationPermission()
            if (newPermissionStatus != hasPermissions) {
                hasPermissions = newPermissionStatus
            }
        }
    }
    KoinApplication(
        application = {
            modules(appModule)
        }
    ) {
        App()
    }

}


private fun hasLocationPermission(): Boolean {
    return when (CLLocationManager.authorizationStatus()) {
        kCLAuthorizationStatusAuthorizedWhenInUse,
        kCLAuthorizationStatusAuthorizedAlways -> true

        else -> false
    }
}

fun requestLocationPermission() {
    val locationManager = CLLocationManager()
    locationManager.requestWhenInUseAuthorization()
}