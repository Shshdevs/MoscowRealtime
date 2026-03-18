package com.hotelka.moscowrealtime.data.remote

import com.hotelka.moscowrealtime.data.dto.ScoreDto
import com.hotelka.moscowrealtime.data.dto.WeeklyScoreDto
import kotlinx.coroutines.flow.Flow

interface ScoreDataSource {
    suspend fun getUserScores(userId: String): Result<List<ScoreDto>>
    suspend fun addUserScore(userScore: ScoreDto): Result<Unit>
    suspend fun observeUserScores(userId: String): Flow<Result<List<ScoreDto>>>
    suspend fun observeWeeklyTopUsers(limit: Int):Flow<Result<List<WeeklyScoreDto>>>
    suspend fun deleteUsersScores(userId: String): Result<Unit>
}