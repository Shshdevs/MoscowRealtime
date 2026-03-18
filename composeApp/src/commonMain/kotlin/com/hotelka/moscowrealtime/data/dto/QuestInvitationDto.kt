package com.hotelka.moscowrealtime.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class QuestInvitationDto(
    val questId: String = "",
    val userFrom: String = "",
    val usersInvited: List<String> = listOf(),
    val questProgress: String = ""
)