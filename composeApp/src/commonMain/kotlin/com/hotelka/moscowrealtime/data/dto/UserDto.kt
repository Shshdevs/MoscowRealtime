package com.hotelka.moscowrealtime.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String = "",
    val username: String = "",
    val name: String = "",
    val name_lowered: String = name.lowercase(),
    val userPic: String? = null,
    val token: String? = "",
    val organizationId: String? = null,
    val currentQuestProgress: String? = null,
    val profilePrivate: Boolean = false,
    val photosPrivate: Boolean = false,
    val friends: List<String> = listOf(),
    val search_prefixes: List<String> = listOf()
)