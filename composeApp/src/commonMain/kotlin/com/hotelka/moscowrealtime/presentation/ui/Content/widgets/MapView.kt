package com.hotelka.moscowrealtime.presentation.ui.Content.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun MapView(
    modifier: Modifier = Modifier,
    onMapReady: (Any) -> Unit,
    onRelease: () -> Unit,
    onMapTouchStateChanged: (Boolean) -> Unit = {},
    showMyLocationButton: Boolean = true,
    moveToCurrentLocation: () -> Unit = {},
    expandActionVisible: Boolean = false,
    expanded: Boolean = false,
    onExpandMapCalled: () -> Unit = {},
    initialZoom: Float = 12f
)