package com.hotelka.moscowrealtime.domain.model.detailed

import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.domain.model.QuestProgress

data class QuestProgressDetailed(
    val questProgress: QuestProgress,
    val quest: Quest,
    val locations: List<Location>,
    val discovers: List<DiscoverDetailed>
)