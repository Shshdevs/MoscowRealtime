package com.hotelka.moscowrealtime.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class VerificationQuestResultDto (
    val awaitedLocation: String,
    val receivedLocation: String,
    val result: String,
    val userId: String,
    val success: Boolean = false
)