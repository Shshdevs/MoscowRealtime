package com.hotelka.moscowrealtime.domain.usecase.questProgress

import com.hotelka.moscowrealtime.domain.model.QuestProgress
import com.hotelka.moscowrealtime.domain.repository.QuestProgressRepository

class CreateQuestProgressUseCase(
    private val repository: QuestProgressRepository
) {
    suspend operator fun invoke(questProgress: QuestProgress): Result<String> {
        return repository.createQuestProgress(questProgress)
    }
}