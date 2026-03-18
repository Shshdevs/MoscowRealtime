package com.hotelka.moscowrealtime.domain.model


data class Quest(
    val id: String,
    val title: String,
    val description: String,
    val cover: String,
    val locations: List<String>,
    val participantIds: List<String>,
    val participantsAmount: Int,
    val tags: List<String>,
    val authorId: String?
)

