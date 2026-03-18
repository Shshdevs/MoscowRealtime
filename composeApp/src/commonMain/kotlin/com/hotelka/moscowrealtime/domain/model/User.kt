package com.hotelka.moscowrealtime.domain.model


data class User(
    val id: String,
    val username: String,
    val name: String,
    val userPic: String? = null,
    val token: String? = null,
    val organizationId: String? = null,
    val currentQuestProgress: String? = null,
    val profilePrivate: Boolean = false,
    val photosPrivate: Boolean = false,
    val friends: List<String> = listOf(),
)