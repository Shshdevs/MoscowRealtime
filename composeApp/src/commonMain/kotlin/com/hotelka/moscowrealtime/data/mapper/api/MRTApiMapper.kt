package com.hotelka.moscowrealtime.data.mapper.api

import com.hotelka.moscowrealtime.data.dto.DiscoverDto
import com.hotelka.moscowrealtime.data.dto.api.ClosestLocationResultDto
import com.hotelka.moscowrealtime.data.dto.api.DetectionDto
import com.hotelka.moscowrealtime.data.dto.api.ErrorDataDto
import com.hotelka.moscowrealtime.data.dto.api.MRTApiDto
import com.hotelka.moscowrealtime.data.mapper.LocationMapper
import com.hotelka.moscowrealtime.domain.model.Discover
import com.hotelka.moscowrealtime.domain.model.api.ClosestLocationResult
import com.hotelka.moscowrealtime.domain.model.api.Detection
import com.hotelka.moscowrealtime.domain.model.api.ErrorData
import com.hotelka.moscowrealtime.domain.model.api.MRTApiResult

fun Discover.toDto(): DiscoverDto = DiscoverDto(
    id = this.id,
    userAuthor = this.userAuthor,
    success = this.success,
    detections = this.detections.map { it.toDto() },
    total_objects = this.totalObjects,
    timestamp = this.timestamp,
    error = this.error,
    upload_success = this.uploadSuccess,
    imageSavedUrl = this.imageSavedUrl,
    YandexGPTGuide = this.yandexGPTGuide
)

fun Detection.toDto(): DetectionDto = DetectionDto(
    locationId = this.locationId,
    confidence = this.confidence
)

fun ErrorData.toDto(): ErrorDataDto = ErrorDataDto(
    success = this.success,
    error = this.error
)

fun <T, R> MRTApiResult<T>.toDto(itemMapper: (T) -> R): MRTApiDto<R> =
    when {
        this.isSuccess && this.data != null -> MRTApiDto.Companion.success(itemMapper(this.data))
        this.error != null -> MRTApiDto.Companion.failure(this.error.message ?: "Unknown error")
        else -> MRTApiDto.Companion.failure("Unknown error")
    }

fun DiscoverDto.toDomain(): Discover = Discover(
    id = this.id,
    userAuthor = this.userAuthor,
    success = this.success,
    detections = this.detections.map { it.toDomain() },
    totalObjects = this.total_objects,
    timestamp = this.timestamp,
    error = this.error,
    uploadSuccess = this.upload_success,
    imageSavedUrl = this.imageSavedUrl,
    yandexGPTGuide = this.YandexGPTGuide
)

fun DetectionDto.toDomain(): Detection = Detection(
    locationId = this.locationId,
    confidence = this.confidence
)

fun ClosestLocationResultDto.toDomain(locationMapper: LocationMapper): ClosestLocationResult =
    ClosestLocationResult(
        closestLocation = locationMapper.toDomain(this.closest_location),
        distance = this.distance,
        success = this.success
    )


fun ErrorDataDto.toDomain(): ErrorData = ErrorData(
    success = this.success,
    error = this.error
)
fun <T, R> MRTApiDto<T>.toDomain(itemMapper: (T) -> R): MRTApiResult<R> =
    if (this.isSuccess && this.data != null) {
        MRTApiResult.Companion.success(itemMapper(this.data))
    } else {
        MRTApiResult.Companion.failure(Exception(this.error ?: "Unknown error"))
    }
