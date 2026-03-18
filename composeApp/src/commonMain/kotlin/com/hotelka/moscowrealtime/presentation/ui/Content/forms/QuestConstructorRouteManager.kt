package com.hotelka.moscowrealtime.presentation.ui.Content.forms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.domain.model.TempQuest
import com.hotelka.moscowrealtime.presentation.ui.Content.items.NoLocationsItem
import com.hotelka.moscowrealtime.presentation.ui.Content.items.ReorderableQuestLocationItem
import com.hotelka.moscowrealtime.presentation.ui.theme.background
import com.hotelka.moscowrealtime.presentation.ui.theme.blue
import com.hotelka.moscowrealtime.presentation.ui.theme.secondaryTextColor
import com.hotelka.moscowrealtime.presentation.extensions.move
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.add
import moscowrealtime.composeapp.generated.resources.add_location
import moscowrealtime.composeapp.generated.resources.arrow_top
import moscowrealtime.composeapp.generated.resources.route
import moscowrealtime.composeapp.generated.resources.route_hint
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun QuestConstructorRouteManager(
    tempQuest: TempQuest,
    onUpdateLocations: (List<Location>) -> Unit,
    onRemoveLocation: (Location) -> Unit,
    onShowSearchLocations: () -> Unit,
    onDragStateChanged: (Boolean) -> Unit
) {
    var expandedLocations by remember { mutableStateOf(false) }

    val uiLocations = remember { mutableStateListOf<Location>() }

    LaunchedEffect(tempQuest.locations.toList()) {
        uiLocations.clear()
        uiLocations.addAll(tempQuest.locations)
    }

    Column {
        Row(modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)) {
            Column(Modifier.weight(1f)) {
                Text(
                    stringResource(Res.string.route),
                    color = secondaryTextColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    stringResource(Res.string.route_hint),
                    color = secondaryTextColor,
                    fontSize = 14.sp,
                )
            }

            Spacer(Modifier.width(10.dp))

            IconButton(onClick = { expandedLocations = !expandedLocations }) {
                Icon(
                    painterResource(Res.drawable.arrow_top),
                    contentDescription = "Expand/Collapse",
                    modifier = Modifier.rotate(if (!expandedLocations) 180f else 0f),
                    tint = secondaryTextColor
                )
            }
        }

        val visibleLocations: List<Pair<Int, Location>> =
            if (expandedLocations) {
                uiLocations.mapIndexed { index, location -> index to location }
            } else {
                uiLocations.take(3).mapIndexed { index, location -> index to location }
            }

        visibleLocations.forEach { (realIndex, location) ->
            key(location.id) {
                ReorderableQuestLocationItem(
                    location = location,
                    position = realIndex + 1,
                    currentIndex = realIndex,
                    itemsCount = uiLocations.size,
                    onDragStateChanged = onDragStateChanged,
                    onMove = { from, to ->
                        uiLocations.move(from, to)
                    },
                    onRemove = {
                        uiLocations.remove(location)
                        onRemoveLocation(location)
                        onUpdateLocations(uiLocations.toList())
                    },
                    onDragFinished = {
                        onUpdateLocations(uiLocations.toList())
                    }
                )
            }

            Spacer(Modifier.height(10.dp))
        }

        if (uiLocations.isEmpty()) {
            NoLocationsItem()
            Spacer(Modifier.height(10.dp))
        }

        Button(
            onClick = onShowSearchLocations,
            colors = ButtonDefaults.buttonColors(blue.copy(0.8f), background),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painterResource(Res.drawable.add),
                contentDescription = "Add location",
                modifier = Modifier.size(14.dp)
            )
            Spacer(Modifier.width(5.dp))
            Text(stringResource(Res.string.add_location))
        }
    }
}