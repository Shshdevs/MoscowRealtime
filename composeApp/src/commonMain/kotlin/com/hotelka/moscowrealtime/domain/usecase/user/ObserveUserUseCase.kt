package com.hotelka.moscowrealtime.domain.usecase.user

import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class ObserveUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userId: String): Flow<Result<User>> {
        return repository.observeUser(userId)
    }
}