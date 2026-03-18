package com.hotelka.moscowrealtime.domain.usecase.questProgress

import com.hotelka.moscowrealtime.domain.repository.QuestProgressRepository

class RemoveUserFromQuestProgressUseCase(
    private val repository: QuestProgressRepository
) {
    suspend operator fun invoke(userId: String, questId: String): Result<Unit> {
        return repository.removeUserFromQuestProgress(userId, questId)
    }
}