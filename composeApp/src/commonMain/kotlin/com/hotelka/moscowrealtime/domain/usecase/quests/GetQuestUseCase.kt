package com.hotelka.moscowrealtime.domain.usecase.quests

import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.domain.repository.QuestRepository

class GetQuestUseCase (
    val questRepository: QuestRepository
) {
    suspend operator fun invoke(id: String): Result<Quest> {
        return questRepository.getQuest(id)
    }
}