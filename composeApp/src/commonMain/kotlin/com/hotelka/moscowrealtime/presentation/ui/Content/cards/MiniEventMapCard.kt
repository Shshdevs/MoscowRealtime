package com.hotelka.moscowrealtime.presentation.ui.Content.cards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.domain.model.mapData.Route
import com.hotelka.moscowrealtime.presentation.state.EventState
import com.hotelka.moscowrealtime.presentation.ui.Content.actions.MapCardsActions
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.EventMapCardHeader

@Composable
fun MiniEventMapCard(
    modifier: Modifier,
    eventState: EventState,
    routeInfoState: Route?,
    onMoveToUserLocation:() -> Unit
) {
    DefaultAppCard(
        modifier.padding(10.dp),
        onClick = null,
    ){
        Column(Modifier.padding(20.dp)) {

            EventMapCardHeader(eventState)
            Spacer(Modifier.height(10.dp))
            MapCardsActions(
                routeInfo = routeInfoState,
                showCamera = false,
                onMoveToUserLocation = onMoveToUserLocation,
            )
        }
    }

}