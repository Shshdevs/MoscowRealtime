package com.hotelka.moscowrealtime.data.mapper

import com.hotelka.moscowrealtime.data.dto.QuestProgressDto
import com.hotelka.moscowrealtime.domain.model.QuestProgress

fun QuestProgress.toDto(): QuestProgressDto {
    return QuestProgressDto(
        questId = questId,
        results = results.map { it.toDto() },
        users = users,
        id = id
    )
}

fun QuestProgressDto.toDomain(): QuestProgress {
    return QuestProgress(
        questId = questId,
        results = results.map { it.toDomain() },
        users = users,
        id = id
    )
}