package com.hotelka.moscowrealtime.domain.usecase.scores

import com.hotelka.moscowrealtime.domain.model.Score
import com.hotelka.moscowrealtime.domain.repository.ScoreRepository

class GetUserScoresUseCase(
    private val repository: ScoreRepository
) {
    suspend operator fun invoke(userId: String): Result<List<Score>> {
        return repository.getUserScores(userId)
    }
}