package com.hotelka.moscowrealtime.domain.model

data class GroupParticipant (
    val user: User,
    val questProgressValue: Float? = null
)