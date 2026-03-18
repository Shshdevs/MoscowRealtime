package com.hotelka.moscowrealtime.data.mapper

import com.hotelka.moscowrealtime.data.dto.GroupDto
import com.hotelka.moscowrealtime.domain.model.Group

fun Group.toDto(): GroupDto = GroupDto(
    id = id,
    organizationId = organizationId,
    groupName = groupName,
    hostUserId = organizerId,
    pinnedQuestId = pinnedQuestId,
    users = users

)

fun GroupDto.toDomain(): Group = Group(
    id = id,
    organizationId = organizationId,
    groupName = groupName,
    organizerId = hostUserId,
    pinnedQuestId = pinnedQuestId,
    users = users
)