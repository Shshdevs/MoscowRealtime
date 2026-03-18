package com.hotelka.moscowrealtime.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class FriendRequestDto(
    val userTo: String = "",
    val userFrom: String = "",
    val status: String = "",
    val sent: Boolean = false,
)
//Status: "sent", "accepted", "rejected", "friends"