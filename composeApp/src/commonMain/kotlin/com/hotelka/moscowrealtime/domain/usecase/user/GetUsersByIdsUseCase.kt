package com.hotelka.moscowrealtime.domain.usecase.user

import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.domain.repository.UserRepository

class GetUsersByIdsUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(ids: List<String>): Result<List<User>>{
        return userRepository.getUsersByIds(ids)
    }
}