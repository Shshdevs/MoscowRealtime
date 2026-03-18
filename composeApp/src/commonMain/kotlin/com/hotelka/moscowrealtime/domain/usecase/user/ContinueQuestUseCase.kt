package com.hotelka.moscowrealtime.domain.usecase.user

import com.hotelka.moscowrealtime.domain.exceptions.UserNotAuthenticatedException
import com.hotelka.moscowrealtime.domain.exceptions.UserRetrievalException
import com.hotelka.moscowrealtime.domain.repository.AuthRepository
import com.hotelka.moscowrealtime.domain.repository.UserRepository

class ContinueQuestUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(questProgressId: String): Result<Unit>{
        val currUserId = authRepository.getCurrentUserId()?:return Result.failure(UserNotAuthenticatedException())
        return userRepository.getUser(currUserId).fold(
            onSuccess = { user ->
                userRepository.updateUser(user.copy(currentQuestProgress = questProgressId))
            },
            onFailure = { e ->
                Result.failure(UserRetrievalException(e))
            }
        )
    }
}