package com.hotelka.moscowrealtime.domain.usecase.search

import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.domain.repository.SearchRepository

class SearchFriendsUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(userId: String, query: String): Result<List<User>> {
        return searchRepository.searchUserFriends(userId, query)
    }
}


