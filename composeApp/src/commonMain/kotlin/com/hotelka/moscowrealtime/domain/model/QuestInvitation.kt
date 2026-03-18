package com.hotelka.moscowrealtime.domain.model

data class QuestInvitation (
    val questId: String,
    val userFrom: String,
    val usersInvited: List<String>,
    val questProgress: String
)