package com.hotelka.moscowrealtime.presentation.ui.Content.cards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotelka.moscowrealtime.domain.model.mapData.Route
import com.hotelka.moscowrealtime.presentation.state.QuestProgressUiState
import com.hotelka.moscowrealtime.presentation.ui.Content.actions.MapCardsActions
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.QuestMapHeader

@Composable
fun QuestMapCard(
    modifier: Modifier = Modifier,
    questProgressUiState: QuestProgressUiState,
    progress: Float,
    routeInfo: Route?,
    onOpenCamera: () -> Unit,
    onMoveToUserLocation: () -> Unit
) {
    DefaultAppCard(
        modifier = modifier.fillMaxWidth().padding(10.dp),
        onClick = null,
        content = {
            Column(Modifier.padding(20.dp)) {
                QuestMapHeader(questProgressUiState, progress)
                Spacer(Modifier.height(10.dp))
                MapCardsActions(
                    showCamera = true,
                    onMoveToUserLocation = onMoveToUserLocation,
                    onOpenCamera = onOpenCamera,
                    routeInfo = routeInfo
                )
            }

        }
    )
}