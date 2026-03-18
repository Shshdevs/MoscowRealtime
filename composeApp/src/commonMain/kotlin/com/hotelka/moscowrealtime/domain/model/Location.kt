package com.hotelka.moscowrealtime.domain.model

data class Location (
    val id: String,
    val label: String,
    val lat: Double,
    val lon: Double,
    val picture: String?
)