package com.hotelka.moscowrealtime.data.mapper

import com.hotelka.moscowrealtime.data.dto.VerificationQuestResultDto
import com.hotelka.moscowrealtime.domain.model.VerificationQuestResult

fun VerificationQuestResultDto.toDomain(): VerificationQuestResult = VerificationQuestResult(
    awaitedLocation = awaitedLocation,
    receivedLocation = receivedLocation,
    result = result,
    userId = userId,
    success = success
)

fun VerificationQuestResult.toDto(): VerificationQuestResultDto = VerificationQuestResultDto(
    awaitedLocation = awaitedLocation,
    receivedLocation = receivedLocation,
    result = result,
    userId = userId,
    success = success
)