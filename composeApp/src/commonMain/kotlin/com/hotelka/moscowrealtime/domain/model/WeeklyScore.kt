package com.hotelka.moscowrealtime.domain.model

data class WeeklyScore(
    val userId: String,
    val totalScore: Int,
    val firstScoreTimestamp: Long,
    val lastScoreTimestamp: Long
)