package com.hotelka.moscowrealtime.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class WeeklyScoreDto(
    val userId: String,
    val totalScore: Int,
    val firstScoreTimestamp: Long = 0L,
    val lastScoreTimestamp: Long = 0L
)