package com.hotelka.moscowrealtime.domain.model

data class FriendRequest(
    val userTo: String,
    val userFrom: String,
    val sent: Boolean = false,
    val status: String = "sent"
)
