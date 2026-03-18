package com.hotelka.moscowrealtime.domain.model

data class Group(
    val id: String,
    val organizationId: String,
    val groupName: String,
    val pinnedQuestId: String?,
    val organizerId: String,
    val users: List<String>,
)