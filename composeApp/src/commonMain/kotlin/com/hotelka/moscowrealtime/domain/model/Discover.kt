package com.hotelka.moscowrealtime.domain.model

import com.hotelka.moscowrealtime.domain.model.api.Detection

data class Discover(
    val id: String,
    val userAuthor: String,
    val success: Boolean,
    val detections: List<Detection>,
    val totalObjects: Int,
    val timestamp: Long,
    val error: String?,
    val uploadSuccess: Boolean,
    val imageSavedUrl: String,
    val yandexGPTGuide: String
)