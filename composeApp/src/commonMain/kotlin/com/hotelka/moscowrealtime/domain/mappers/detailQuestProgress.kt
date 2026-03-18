package com.hotelka.moscowrealtime.domain.mappers

import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.domain.model.QuestProgress
import com.hotelka.moscowrealtime.domain.model.detailed.DiscoverDetailed
import com.hotelka.moscowrealtime.domain.model.detailed.QuestProgressDetailed

fun detailQuestProgress(
    progress: QuestProgress,
    quest: Quest?,
    locations: List<Location>,
    discovers: List<DiscoverDetailed>
): QuestProgressDetailed? = quest?.let {
    QuestProgressDetailed(
        questProgress = progress,
        quest = quest,
        locations = locations,
        discovers = discovers
    )
}