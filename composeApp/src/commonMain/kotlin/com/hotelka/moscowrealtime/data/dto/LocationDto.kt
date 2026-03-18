package com.hotelka.moscowrealtime.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class LocationDto (
    val id: String? = null,
    val label: String = "",
    val label_en: String? = "",
    val label_ru: String? = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val picture: String? = null
)