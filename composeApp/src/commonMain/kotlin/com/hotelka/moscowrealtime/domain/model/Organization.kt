package com.hotelka.moscowrealtime.domain.model

data class Organization(
    val id: String = "",
    val name: String,
    val usersHosts: List<String>,
    val users: List<String>
)

