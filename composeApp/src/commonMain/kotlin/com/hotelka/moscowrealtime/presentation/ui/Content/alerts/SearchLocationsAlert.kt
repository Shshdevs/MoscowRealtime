package com.hotelka.moscowrealtime.presentation.ui.Content.alerts

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.domain.model.TempQuest
import com.hotelka.moscowrealtime.presentation.state.listState.LocationsListState
import com.hotelka.moscowrealtime.presentation.ui.Content.anim.SlideVerticallyCardAnim
import com.hotelka.moscowrealtime.presentation.ui.Content.forms.SearchLocationsForm
import com.hotelka.moscowrealtime.presentation.ui.Content.frames.DismissableCardFrame
import com.hotelka.moscowrealtime.presentation.ui.Content.headers.DismissableCardHeader
import com.hotelka.moscowrealtime.presentation.ui.Content.items.AddLocationSearchItem
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.add_location

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchLocationsAlert(
    contentVisible: Boolean,
    tempQuest: TempQuest,
    searchedLocations: LocationsListState,
    finalQuery: String,
    onAddLocation: (Location) -> Unit,
    onSearchLocations: (String) -> Unit,
    onDismiss: () -> Unit
) {
    BasicAlertDialog(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 200.dp),
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        content = {
            SlideVerticallyCardAnim(
                visible = contentVisible,
            ) {
                DismissableCardFrame(
                    onDismissRequest = onDismiss
                ) {
                    DismissableCardHeader(Res.string.add_location)
                    Spacer(Modifier.height(20.dp))
                    SearchLocationsForm(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(0.dp),
                        finalQuery = finalQuery,
                        locationsListState = searchedLocations,
                        locationSearchItem = { location ->
                            AddLocationSearchItem(
                                location = location,
                                included = tempQuest.locations.contains(location),
                                onAddLocation = { onAddLocation(location) }
                            )
                        },
                        searchLocations = onSearchLocations,
                    )
                }
            }
        })
}