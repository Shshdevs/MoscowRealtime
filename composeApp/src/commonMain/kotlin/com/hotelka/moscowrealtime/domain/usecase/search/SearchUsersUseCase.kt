package com.hotelka.moscowrealtime.domain.usecase.search

import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.domain.repository.AuthRepository
import com.hotelka.moscowrealtime.domain.repository.SearchRepository

class SearchUsersUseCase(
    private val authRepository: AuthRepository,
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String): Result<List<User>> {
        val currUserId = authRepository.getCurrentUserId()
        return searchRepository.searchUsers(query).map { list -> list.filter { user -> user.id != currUserId }}
    }
}