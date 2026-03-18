package com.hotelka.moscowrealtime.domain.usecase.quests

import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.domain.repository.QuestRepository


class GetQuestsUseCase (
    val questRepository: QuestRepository
) {
    suspend operator fun invoke(): Result<List<Quest>> {
        return questRepository.getQuests()
    }
}