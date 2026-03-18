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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.mapview.MapView
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.compress
import moscowrealtime.composeapp.generated.resources.expand
import moscowrealtime.composeapp.generated.resources.user_navigation
import org.jetbrains.compose.resources.painterResource

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
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val currentOnMapTouchStateChanged = rememberUpdatedState(onMapTouchStateChanged)
    val currentOnMapReady = rememberUpdatedState(onMapReady)
    val currentOnRelease = rememberUpdatedState(onRelease)

    val cameraListener = remember {
        CameraListener { _, _, _, finished ->
            currentOnMapTouchStateChanged.value(!finished)
        }
    }

    val mapView = remember {
        MapView(context).apply {
            mapWindow.map.isRotateGesturesEnabled = true
            mapWindow.map.isTiltGesturesEnabled = true
        }
    }

    DisposableEffect(mapView) {
        mapView.mapWindow.map.addCameraListener(cameraListener)
        currentOnMapReady.value(mapView)

        onDispose {
            mapView.mapWindow.map.removeCameraListener(cameraListener)
            currentOnRelease.value()
        }
    }

    DisposableEffect(lifecycleOwner, mapView) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    mapView.onStart()
                }

                Lifecycle.Event.ON_STOP -> {
                    mapView.onStop()
                }

                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Box(modifier = modifier) {
        AndroidView(
            modifier = Modifier.matchParentSize(),
            factory = { mapView }
        )

        if (expandActionVisible) {
            IconButton(
                onClick = onExpandMapCalled,
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
                    .size(70.dp)
                    .padding(10.dp),
                containerColor = blue,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(
                    painter = painterResource(Res.drawable.user_navigation),
                    modifier = Modifier.scale(1.7f),
                    contentDescription = "User Location"
                )
            }
        }
    }
}
