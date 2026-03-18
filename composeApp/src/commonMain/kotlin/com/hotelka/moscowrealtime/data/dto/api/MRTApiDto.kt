package com.hotelka.moscowrealtime.data.dto.api

import com.hotelka.moscowrealtime.data.dto.LocationDto
import kotlinx.serialization.Serializable

@Serializable
data class DetectionDto(
    val locationId: String,
    val confidence: Double,
)

@Serializable
data class MRTApiDto<T>(
    val data: T? = null,
    val error: String? = null,
    val isSuccess: Boolean = error == null
) {
    companion object {
        fun <T> success(data: T): MRTApiDto<T> = MRTApiDto(data = data)
        fun <T> failure(error: String): MRTApiDto<T> = MRTApiDto(error = error)
    }
}

@Serializable
data class ClosestLocationResultDto(
    val closest_location: LocationDto? = null,
    val distance: Float = 0f,
    val success: Boolean = false
)

@Serializable
data class ErrorDataDto(
    val success: Boolean,
    val error: String
)

