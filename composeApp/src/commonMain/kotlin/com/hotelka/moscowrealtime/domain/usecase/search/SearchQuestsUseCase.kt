package com.hotelka.moscowrealtime.domain.usecase.search

import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.domain.repository.SearchRepository

class SearchQuestsUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String): Result<List<Quest>> {
        return searchRepository.searchQuests(query)
    }
}