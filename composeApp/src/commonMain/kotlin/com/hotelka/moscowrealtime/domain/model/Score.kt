package com.hotelka.moscowrealtime.domain.model

data class Score(
    val score: Int,
    val timestamp: Long,
    val sourceId: String,
    val userId: String
)