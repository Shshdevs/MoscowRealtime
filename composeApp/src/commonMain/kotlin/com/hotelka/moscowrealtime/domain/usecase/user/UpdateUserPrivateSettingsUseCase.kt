package com.hotelka.moscowrealtime.domain.usecase.user

import com.hotelka.moscowrealtime.domain.exceptions.UserNotAuthenticatedException
import com.hotelka.moscowrealtime.domain.repository.AuthRepository
import com.hotelka.moscowrealtime.domain.repository.UserRepository

class UpdateUserPrivateSettingsUseCase(
    private val authRepository: AuthRepository,
    private val repository: UserRepository
) {
    suspend operator fun invoke(
        private: Boolean,
        photosPrivate: Boolean
    ): Result<Unit> {
        val userId = authRepository.getCurrentUserId() ?: return Result.failure(
            UserNotAuthenticatedException()
        )
        return repository.getUser(userId).fold(
            onSuccess = { user ->
                repository.updateUser(
                    user.copy(
                        profilePrivate = private,
                        photosPrivate = if (private) true else photosPrivate
                    )
                )
            },
            onFailure = { e ->
                Result.failure(e)
            }
        )
    }
}