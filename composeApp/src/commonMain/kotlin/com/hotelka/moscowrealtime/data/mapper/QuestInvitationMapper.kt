package com.hotelka.moscowrealtime.data.mapper

import com.hotelka.moscowrealtime.data.dto.QuestInvitationDto
import com.hotelka.moscowrealtime.domain.model.QuestInvitation

fun QuestInvitation.toDto(): QuestInvitationDto = QuestInvitationDto(
    questId = questId,
    userFrom = userFrom,
    usersInvited = usersInvited,
    questProgress = questProgress
)
fun QuestInvitationDto.toDomain(): QuestInvitation = QuestInvitation(
    questId = questId,
    userFrom = userFrom,
    usersInvited = usersInvited,
    questProgress = questProgress
)