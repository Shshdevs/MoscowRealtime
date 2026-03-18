package com.hotelka.moscowrealtime.domain.usecase.scores

import com.hotelka.moscowrealtime.domain.model.Score
import com.hotelka.moscowrealtime.domain.repository.AuthRepository
import com.hotelka.moscowrealtime.domain.repository.ScoreRepository
import com.hotelka.moscowrealtime.presentation.utils.currentTimestamp

class AddUserScoreUseCase(
    private val authRepository: AuthRepository,
    private val repository: ScoreRepository
) {
    suspend operator fun invoke(score:Int, source: String): Result<Unit> {
        val currUserId = authRepository.getCurrentUserId()?: return Result.failure(Exception("User not authenticated"))
        val userScore = Score(
            score = score,
            timestamp = currentTimestamp(),
            sourceId = source,
            userId = currUserId
        )
        return repository.addUserScore(userScore)
    }
}
