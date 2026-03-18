package com.hotelka.moscowrealtime.data.mapper

import com.hotelka.moscowrealtime.data.dto.ScoreDto
import com.hotelka.moscowrealtime.data.dto.WeeklyScoreDto
import com.hotelka.moscowrealtime.domain.model.Score
import com.hotelka.moscowrealtime.domain.model.WeeklyScore

fun ScoreDto.toDomain(): Score = Score(
    score = score,
    timestamp = timestamp,
    sourceId = sourceId,
    userId = userId
)

fun Score.toDto(): ScoreDto = ScoreDto(
    score = score,
    timestamp = timestamp,
    sourceId = sourceId,
    userId = userId
)