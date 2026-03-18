package com.hotelka.moscowrealtime.domain.model.detailed

import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.domain.model.QuestProgress
import com.hotelka.moscowrealtime.domain.model.User

data class QuestDetailed(
    val quest: Quest,
    val questProgress: QuestProgress?,
    val locations: List<Location>,
    val participants: List<User>,
    val discovers: List<DiscoverDetailed>,
    val userAuthor: User?,
)