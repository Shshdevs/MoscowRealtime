package com.hotelka.moscowrealtime.domain.usecase.user

import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.domain.repository.UserRepository

class GetFriendsUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userId: String): Result<List<User>> {
        repository.getUser(userId).fold(
            onSuccess = {user ->
                repository.getUsersByIds(user.friends).fold(
                    onSuccess = {users ->
                        return Result.success(users )
                    },
                    onFailure = {e ->
                        return Result.failure(e)
                    }
                )
            },
            onFailure = {e ->
                return Result.failure(e)
            }
        )
    }
}