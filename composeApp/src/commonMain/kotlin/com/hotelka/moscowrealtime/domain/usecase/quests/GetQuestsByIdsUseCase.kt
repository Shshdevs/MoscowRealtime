package com.hotelka.moscowrealtime.domain.usecase.quests

import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.domain.repository.QuestRepository

class GetQuestsByIdsUseCase (
    val questRepository: QuestRepository
) {
    suspend operator fun invoke(ids: List<String>): Result<List<Quest>> {
        return questRepository.getQuestsByIds(ids)
    }
}