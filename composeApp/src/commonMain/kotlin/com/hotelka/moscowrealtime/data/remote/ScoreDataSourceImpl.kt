package com.hotelka.moscowrealtime.data.remote

import com.hotelka.moscowrealtime.data.dto.ScoreDto
import com.hotelka.moscowrealtime.data.dto.WeeklyScoreDto
import com.hotelka.moscowrealtime.presentation.utils.getCurrentWeekRange
import dev.gitlive.firebase.firestore.FieldPath
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ScoreDataSourceImpl(
    private val firestore: FirebaseFirestore
) : ScoreDataSource {

    override suspend fun getUserScores(userId: String): Result<List<ScoreDto>> = try {
        val scores = firestore.collection(SCORE_COLLECTION).where {
            FieldPath("userId") equalTo userId
        }.get().documents.map { it.data<ScoreDto>() }
        Result.success(scores)
    } catch (e: Exception) {
        Result.failure(Exception("Fetching user scores error", e))
    }

    override suspend fun addUserScore(userScore: ScoreDto): Result<Unit> = try {
        firestore.collection(SCORE_COLLECTION).add(userScore)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception("Creating user scores error", e))
    }

    override suspend fun observeUserScores(userId: String): Flow<Result<List<ScoreDto>>> =
        firestore.collection(SCORE_COLLECTION)
            .where { FieldPath("userId") equalTo userId }.snapshots
            .map { collector ->
                try {
                    Result.success(collector.documents.map { doc -> doc.data<ScoreDto>() })
                } catch (e: Exception) {
                    Result.failure(Exception("Error observing scores", e))
                }
            }
            .catch { e ->
                emit(Result.failure(Exception("Error observing scores", e)))
            }

    override suspend fun observeWeeklyTopUsers(limit: Int): Flow<Result<List<WeeklyScoreDto>>> =
        flow {
            val (weekStart, weekEnd) = getCurrentWeekRange()
            firestore.collection(SCORE_COLLECTION).where {
                ("timestamp" greaterThanOrEqualTo weekStart).and("timestamp" lessThanOrEqualTo weekEnd)
            }.snapshots.collect { snapshot ->
                val weeklyScores = snapshot.documents.map { it.data<ScoreDto>() }
                val summarizedWeeklyScores = weeklyScores.groupBy { it.userId }
                    .map { (userId, scores) ->
                        WeeklyScoreDto(
                            userId = userId,
                            totalScore = scores.sumOf { it.score },
                            firstScoreTimestamp = scores.minOf { it.timestamp },
                            lastScoreTimestamp = scores.maxOf { it.timestamp }
                        )
                    }.sortedByDescending { it.totalScore }
                    .take(limit)
                emit(Result.success(summarizedWeeklyScores))
            }
        }

    override suspend fun deleteUsersScores(userId: String): Result<Unit> = try {
        firestore.collection(SCORE_COLLECTION).where {
            FieldPath("userId") equalTo userId
        }.get().documents.forEach { it.reference.delete() }
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception("Error deleing scores for $userId", e))
    }

    companion object Companion {
        private const val SCORE_COLLECTION = "scores"
    }
}