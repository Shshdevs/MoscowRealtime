package com.hotelka.moscowrealtime.domain.usecase.search

import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.domain.repository.SearchRepository

class SearchUsersWithIdsUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String, ids: List<String>): Result<List<User>> {
        return searchRepository.searchUsersWithIds(query, ids)
    }
}