package com.hotelka.moscowrealtime.data.repository

import com.hotelka.moscowrealtime.data.remote.ScoreDataSource
import com.hotelka.moscowrealtime.data.mapper.toDomain
import com.hotelka.moscowrealtime.data.mapper.toDto
import com.hotelka.moscowrealtime.domain.model.Score
import com.hotelka.moscowrealtime.domain.model.WeeklyScore
import com.hotelka.moscowrealtime.domain.repository.ScoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ScoreRepositoryImpl(
    private val scoreDataSource: ScoreDataSource
) : ScoreRepository {
    override suspend fun getUserScores(userId: String): Result<List<Score>> {
        return scoreDataSource.getUserScores(userId).map { list -> list.map { it.toDomain() } }
    }

    override suspend fun addUserScore(score: Score): Result<Unit> {
        return scoreDataSource.addUserScore(score.toDto())
    }

    override suspend fun observeUserScores(userId: String): Flow<Result<List<Score>>> {
        return scoreDataSource.observeUserScores(userId)
            .map { result -> result.map { list -> list.map { it.toDomain() } } }
    }

    override suspend fun observeWeeklyTopUsers(limit: Int): Flow<Result<List<WeeklyScore>>> {
        return scoreDataSource.observeWeeklyTopUsers(limit)
            .map { result -> result.map { list -> list.map { it.toDomain() } } }
    }

    override suspend fun deleteUsersScores(userId: String): Result<Unit> {
        return scoreDataSource.deleteUsersScores(userId)
    }
}