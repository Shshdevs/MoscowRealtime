package com.hotelka.moscowrealtime.presentation.ui.Content.infos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.domain.model.QuestProgress
import com.hotelka.moscowrealtime.domain.model.detailed.DiscoverDetailed
import com.hotelka.moscowrealtime.presentation.ui.Content.items.QuestLocationItem
import com.hotelka.moscowrealtime.presentation.utils.filterSuccessfulDiscovers

@Composable
fun UserCurrentQuestProgressInfoSuccess(
    questLocations: List<Location>,
    questProgressDiscovers: List<DiscoverDetailed>,
    questProgress: QuestProgress?,
    showRange: Boolean,
    onQuestLocationItemClick: (Location) -> Unit
) {

    val successfulDiscovers by remember {
        derivedStateOf {
            if (questProgress != null) questProgressDiscovers.filterSuccessfulDiscovers(
                questProgress
            ) else emptyList()
        }
    }

    val visibleLocations by remember {
        derivedStateOf {
            if (questLocations.isEmpty()) return@derivedStateOf emptyList()
            val currentPos = (successfulDiscovers.size - 1).coerceIn(0, questLocations.lastIndex)
            if (!showRange) {
                questLocations
            } else {
                when {
                    questLocations.size <= 3 -> questLocations
                    currentPos <= 1 -> questLocations.take(3)
                    currentPos >= questLocations.lastIndex - 1 -> questLocations.takeLast(3)
                    else -> questLocations.subList(currentPos - 1, currentPos + 2)
                }
            }
        }
    }

    Column {
        visibleLocations.forEachIndexed { index, location ->
            val discover = successfulDiscovers.firstOrNull {
                it.discover.detections.firstOrNull()?.locationId == location.id
            }
            val isCurrent = try {
                (questLocations.size - successfulDiscovers.size) == index + 1
            } catch (e: Exception) {
                false
            }

            QuestLocationItem(
                modifier = Modifier.fillMaxWidth(),
                location = location,
                discover = discover?.discover,
                theFirstOne = index == 0,
                completed = discover != null,
                current = isCurrent,
                onClick = {
                    onQuestLocationItemClick(location)
                }
            )
        }
    }
}