package com.hotelka.moscowrealtime.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class GroupDto(
    val id: String = "",
    val organizationId: String = "",
    val groupName: String = "",
    val hostUserId: String = "",
    val pinnedQuestId: String? = null,
    val users: List<String> = listOf(),
)