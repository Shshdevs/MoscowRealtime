package com.hotelka.moscowrealtime.domain.model

import com.hotelka.moscowrealtime.domain.model.detailed.GroupDetailed

data class Organizer(
    val organizer: User,
    val groups: List<GroupDetailed>
)