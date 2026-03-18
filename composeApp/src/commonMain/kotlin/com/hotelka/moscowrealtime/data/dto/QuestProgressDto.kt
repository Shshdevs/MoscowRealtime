package com.hotelka.moscowrealtime.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class QuestProgressDto (
    val id: String ="",
    val questId: String = "",
    val results: List<VerificationQuestResultDto> = arrayListOf(),
    val users: List<String> = arrayListOf(),
)