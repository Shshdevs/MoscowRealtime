package com.hotelka.moscowrealtime.data.dto

import com.hotelka.moscowrealtime.core.config.AppConfig
import kotlinx.serialization.Serializable

@Serializable
data class QuestDto (
    val id: String? = null,
    val description_en: String = "",
    val description_ru: String = "",
    val title_en: String = "",
    val title_ru: String = "",
    val cover: String = AppConfig.defaultPlaceHolder,
    val locations: List<String> = listOf(),
    val participantIds: List<String> = listOf(),
    val participantsAmount: Int = participantIds.count(),
    val tags_en: List<String> = listOf(),
    val tags_ru: List<String> = listOf(),
    val authorId: String? = null
)