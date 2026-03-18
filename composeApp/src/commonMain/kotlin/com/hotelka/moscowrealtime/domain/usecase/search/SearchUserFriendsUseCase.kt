package com.hotelka.moscowrealtime.domain.usecase.search

import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.domain.repository.AuthRepository
import com.hotelka.moscowrealtime.domain.repository.SearchRepository

class SearchUserFriendsUseCase(
    private val authRepository: AuthRepository,
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String): Result<List<User>> {
        return authRepository.getCurrentUserId()?.let {
            searchRepository.searchUserFriends(it, query.lowercase())
        } ?: Result.failure(Exception("User not authenticated"))
    }
}