package com.hotelka.moscowrealtime.presentation.ui.Content.actions

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.domain.model.mapData.Route
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.CameraButton
import com.hotelka.moscowrealtime.presentation.ui.Content.buttons.LocateUserButton
import com.hotelka.moscowrealtime.presentation.ui.Content.infos.DurationView
import com.hotelka.moscowrealtime.presentation.ui.Content.infos.ShoePrintsView

@Composable
fun MapCardsActions(
    routeInfo: Route?,
    showCamera: Boolean = false,
    onMoveToUserLocation: () -> Unit,
    onOpenCamera: (() -> Unit) = {},
) {
    Row(Modifier.padding(start = 5.dp), verticalAlignment = Alignment.CenterVertically) {
        ShoePrintsView(routeInfo?.distance)
        Spacer(Modifier.width(10.dp))
        DurationView(routeInfo?.duration)
        Spacer(Modifier.fillMaxWidth().weight(1f))

        if (showCamera) {
            CameraButton(onOpenCamera)
            Spacer(Modifier.width(10.dp))
        }
        LocateUserButton(onMoveToUserLocation)
    }
}