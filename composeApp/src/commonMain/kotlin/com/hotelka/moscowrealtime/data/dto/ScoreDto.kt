package com.hotelka.moscowrealtime.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ScoreDto(
    val score: Int = 0,
    val timestamp: Long = 0L,
    val sourceId: String = "",
    val userId: String = ""
)

