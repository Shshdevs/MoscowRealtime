package com.hotelka.moscowrealtime.domain.usecase.questProgress

import com.hotelka.moscowrealtime.domain.model.QuestProgress
import com.hotelka.moscowrealtime.domain.repository.QuestProgressRepository

class GetQuestProgressUseCase(
    private val repository: QuestProgressRepository
) {
    suspend operator fun invoke(userId: String, questId: String): Result<QuestProgress?> {
        return repository.getQuestProgress(userId, questId)
    }
    suspend operator fun invoke(questProgressId: String): Result<QuestProgress> {
        return repository.getQuestProgressWithId(questProgressId)
    }
}