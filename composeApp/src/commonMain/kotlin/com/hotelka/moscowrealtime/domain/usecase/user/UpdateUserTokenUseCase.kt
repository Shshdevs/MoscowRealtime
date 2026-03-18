package com.hotelka.moscowrealtime.domain.usecase.user

import com.hotelka.moscowrealtime.domain.repository.UserRepository

class UpdateUserTokenUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userId: String, token: String): Result<Unit>{
        return repository.getUser(userId).fold(
            onSuccess = { user ->
                repository.updateUser(user.copy(token = token))
            },
            onFailure = { e ->
                Result.failure(e)
            }
        )
    }
}