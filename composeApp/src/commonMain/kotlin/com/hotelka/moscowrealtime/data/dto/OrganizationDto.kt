package com.hotelka.moscowrealtime.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class OrganizationDto(
    val id: String = "",
    val name: String ="",
    val usersHosts: List<String> = listOf(),
    val users: List<String> = listOf()
)

