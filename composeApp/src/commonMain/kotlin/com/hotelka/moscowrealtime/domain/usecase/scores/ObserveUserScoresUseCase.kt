package com.hotelka.moscowrealtime.domain.usecase.scores

import com.hotelka.moscowrealtime.domain.exceptions.UserNotAuthenticatedException
import com.hotelka.moscowrealtime.domain.model.Score
import com.hotelka.moscowrealtime.domain.repository.AuthRepository
import com.hotelka.moscowrealtime.domain.repository.ScoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class ObserveUserScoresUseCase(
    private val authRepository: AuthRepository,
    private val repository: ScoreRepository
) {
    suspend operator fun invoke(): Flow<Result<List<Score>>> {
        val currUserId = authRepository.getCurrentUserId() ?: return flow {
            emit(
                Result.failure(
                    UserNotAuthenticatedException()
                )
            )
        }
        return repository.observeUserScores(currUserId)
    }
}