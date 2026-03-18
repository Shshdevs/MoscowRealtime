package com.hotelka.moscowrealtime.data.mapper

import com.hotelka.moscowrealtime.data.dto.WeeklyScoreDto
import com.hotelka.moscowrealtime.domain.model.WeeklyScore

fun WeeklyScoreDto.toDomain(): WeeklyScore = WeeklyScore(
    userId = userId,
    totalScore = totalScore,
    firstScoreTimestamp = firstScoreTimestamp,
    lastScoreTimestamp = lastScoreTimestamp
)