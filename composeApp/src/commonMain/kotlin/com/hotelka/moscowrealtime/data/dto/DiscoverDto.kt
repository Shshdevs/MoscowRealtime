package com.hotelka.moscowrealtime.data.dto

import com.hotelka.moscowrealtime.data.dto.api.DetectionDto
import kotlinx.serialization.Serializable

@Serializable
data class DiscoverDto(
    val id: String = "",
    val userAuthor: String = "",
    val success: Boolean = false,
    val detections: List<DetectionDto> = listOf(),
    val total_objects: Int = 0,
    val timestamp: Long = 0,
    val error: String? = null,
    val upload_success: Boolean = false,
    val imageSavedUrl: String = "",
    val YandexGPTGuide: String = ""
)