package com.hotelka.moscowrealtime.data.mapper

import com.hotelka.moscowrealtime.data.dto.QuestDto
import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.domain.repository.LocaleProvider

class QuestMapper(
    private val localeProvider: LocaleProvider
) {
    fun toDomain(dto: QuestDto): Quest = Quest(
        id = dto.id!!,
        title = when (localeProvider.getLocale()) {
            "ru" -> dto.title_ru
            else -> dto.title_en
        },
        description = when (localeProvider.getLocale()) {
            "ru" -> dto.description_ru
            else -> dto.description_en
        },
        cover = dto.cover,
        locations = dto.locations,
        participantIds = dto.participantIds,
        tags = when (localeProvider.getLocale()) {
            "ru" -> dto.tags_ru
            else -> dto.tags_en
        },
        participantsAmount = dto.participantsAmount,
        authorId = dto.authorId,
    )

    fun toDto(quest: Quest): QuestDto = QuestDto(
        id = quest.id,
        description_en = quest.description,
        description_ru = quest.description,
        title_en = quest.title,
        title_ru = quest.title,
        cover = quest.cover,
        locations = quest.locations,
        participantIds = quest.participantIds,
        participantsAmount = quest.participantsAmount,
        tags_en = quest.tags,
        tags_ru = quest.tags,
        authorId = quest.authorId
    )
}