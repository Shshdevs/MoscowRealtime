package com.hotelka.moscowrealtime.domain.mappers

import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.domain.model.QuestProgress
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.domain.model.detailed.DiscoverDetailed
import com.hotelka.moscowrealtime.domain.model.detailed.QuestDetailed

fun detailQuest(
    quest: Quest,
    questProgress: QuestProgress?,
    locations: List<Location>,
    participants: List<User>,
    discovers: List<DiscoverDetailed>,
    userAuthor: User?
) = QuestDetailed(
    quest = quest,
    questProgress = questProgress,
    locations = locations,
    participants = participants,
    discovers = discovers,
    userAuthor = userAuthor
)