package com.hotelka.moscowrealtime.presentation.ui.Content.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitInteropInteractionMode
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import cocoapods.YandexMapsMobile.YMKCameraPosition
import cocoapods.YandexMapsMobile.YMKCameraUpdateReason
import cocoapods.YandexMapsMobile.YMKMap
import cocoapods.YandexMapsMobile.YMKMapCameraListenerProtocol
import cocoapods.YandexMapsMobile.YMKMapView
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.compress
import moscowrealtime.composeapp.generated.resources.expand
import moscowrealtime.composeapp.generated.resources.user_navigation
import org.jetbrains.compose.resources.painterResource
import platform.CoreGraphics.CGRectZero
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
private class IOSCameraListener(
    private val onTouchStateChanged: (Boolean) -> Unit
) : NSObject(), YMKMapCameraListenerProtocol {

    override fun onCameraPositionChangedWithMap(
        map: YMKMap,
        cameraPosition: YMKCameraPosition,
        cameraUpdateReason: YMKCameraUpdateReason,
        finished: Boolean
    ) {
        onTouchStateChanged(!finished)
    }
}

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun MapView(
    modifier: Modifier,
    onMapReady: (Any) -> Unit,
    onRelease: () -> Unit,
    onMapTouchStateChanged: (Boolean) -> Unit,
    showMyLocationButton: Boolean,
    moveToCurrentLocation: () -> Unit,
    expandActionVisible: Boolean,
    expanded: Boolean,
    onExpandMapCalled: () -> Unit,
    initialZoom: Float,
) {
    val currentOnMapReady = rememberUpdatedState(onMapReady)
    val currentOnRelease = rememberUpdatedState(onRelease)
    val currentOnTouchChanged = rememberUpdatedState(onMapTouchStateChanged)

    val mapView = remember {
        YMKMapView(frame = CGRectZero.readValue()).apply {
            mapWindow?.map?.rotateGesturesEnabled = true
            mapWindow?.map?.tiltGesturesEnabled = true
        }
    }

    val cameraListener = remember {
        IOSCameraListener { moving ->
            currentOnTouchChanged.value(moving)
        }
    }

    DisposableEffect(mapView, cameraListener) {
        mapView.mapWindow?.map?.addCameraListenerWithCameraListener(cameraListener)

        onDispose {
            mapView.mapWindow?.map?.removeCameraListenerWithCameraListener(cameraListener)
        }
    }

    Box(modifier = modifier) {
        UIKitView(
            factory = {
                mapView
            },
            update = { nativeMapView ->
                currentOnMapReady.value(nativeMapView)
            },
            modifier = Modifier.matchParentSize(),
            onRelease = { currentOnRelease.value() },
            properties = UIKitInteropProperties(
                interactionMode = UIKitInteropInteractionMode.NonCooperative,
                isNativeAccessibilityEnabled = true
            )
        )

        if (expandActionVisible) {
            IconButton(
                onClick = onExpandMapCalled,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = background,
                    contentColor = secondaryTextColor
                ),
                shape = CircleShape
            ) {
                Icon(
                    painter = painterResource(
                        if (expanded) Res.drawable.compress else Res.drawable.expand
                    ),
                    contentDescription = "MapResize"
                )
            }
        }

        if (showMyLocationButton) {
            FloatingActionButton(
                onClick = moveToCurrentLocation,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp)
                    .size(56.dp),
                containerColor = blue,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(
                    painter = painterResource(Res.drawable.user_navigation),
                    modifier = Modifier.scale(1.35f),
                    contentDescription = "User Location"
                )
            }
        }
    }
}