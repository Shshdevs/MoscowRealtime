package com.hotelka.moscowrealtime.domain.repository

import com.hotelka.moscowrealtime.domain.model.Score
import com.hotelka.moscowrealtime.domain.model.WeeklyScore
import kotlinx.coroutines.flow.Flow

interface ScoreRepository {
    suspend fun getUserScores(userId: String): Result<List<Score>>
    suspend fun addUserScore(score: Score): Result<Unit>
    suspend fun observeUserScores(userId: String): Flow<Result<List<Score>>>
    suspend fun observeWeeklyTopUsers(limit: Int): Flow<Result<List<WeeklyScore>>>
    suspend fun deleteUsersScores(userId: String): Result<Unit>
}