package com.hotelka.moscowrealtime.domain.usecase.quests

import com.hotelka.moscowrealtime.domain.repository.QuestProgressRepository
import com.hotelka.moscowrealtime.domain.repository.QuestRepository

class DeleteQuestUseCase(
    private val questRepository: QuestRepository,
    private val questProgressRepository: QuestProgressRepository
) {
    suspend operator fun invoke(questId: String): Result<Unit> = runCatching{
        questProgressRepository.deleteQuestProgress(questId)
        questRepository.deleteQuest(questId)
    }
}