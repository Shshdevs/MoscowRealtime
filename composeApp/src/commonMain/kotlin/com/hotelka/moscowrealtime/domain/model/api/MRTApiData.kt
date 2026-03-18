package com.hotelka.moscowrealtime.domain.model.api

import com.hotelka.moscowrealtime.domain.model.Location


data class Detection(
    val locationId: String,
    val confidence: Double,
)

data class ClosestLocationResult(
    val closestLocation: Location? = null,
    val distance: Float = 0f,
    val success: Boolean = false
)


data class ErrorData(
    val success: Boolean,
    val error: String
)

data class MRTApiResult<T>(
    val data: T? = null,
    val error: Exception? = null,
    val isSuccess: Boolean = error == null
){
    companion object {
        fun <T> success(data: T): MRTApiResult<T> = MRTApiResult(data = data)
        fun <T> failure(e: Exception): MRTApiResult<T> = MRTApiResult(error = e)
    }
}