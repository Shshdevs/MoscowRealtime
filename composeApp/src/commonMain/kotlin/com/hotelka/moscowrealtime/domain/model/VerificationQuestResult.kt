package com.hotelka.moscowrealtime.domain.model

data class VerificationQuestResult (
    val awaitedLocation: String,
    val receivedLocation: String,
    val result: String,
    val userId: String,
    val success: Boolean = awaitedLocation==receivedLocation
)