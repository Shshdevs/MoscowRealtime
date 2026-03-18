package com.hotelka.moscowrealtime.domain.model

data class QuestProgress (
    val id: String,
    val questId: String,
    val results: List<VerificationQuestResult>,
    val users: List<String>
)