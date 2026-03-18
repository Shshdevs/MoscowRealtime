package com.hotelka.moscowrealtime.domain.mappers

import com.hotelka.moscowrealtime.domain.model.Group
import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.domain.model.GroupParticipant
import com.hotelka.moscowrealtime.domain.model.detailed.GroupDetailed

fun detailGroup(
    group: Group,
    quest: Quest?,
    organizer: User,
    participants: List<GroupParticipant>,
) = GroupDetailed(
    group = group,
    quest = quest,
    organizer = organizer,
    users = participants
)