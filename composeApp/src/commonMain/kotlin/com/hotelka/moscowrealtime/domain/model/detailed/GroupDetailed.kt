package com.hotelka.moscowrealtime.domain.model.detailed

import com.hotelka.moscowrealtime.domain.model.Group
import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.domain.model.GroupParticipant

data class GroupDetailed(
    val group: Group,
    val quest: Quest?,
    val organizer: User,
    val users: List<GroupParticipant> = emptyList()
)
