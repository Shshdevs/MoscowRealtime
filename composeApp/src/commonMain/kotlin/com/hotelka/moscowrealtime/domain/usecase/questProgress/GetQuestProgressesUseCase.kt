package com.hotelka.moscowrealtime.domain.usecase.questProgress

import com.hotelka.moscowrealtime.domain.model.QuestProgress
import com.hotelka.moscowrealtime.domain.repository.QuestProgressRepository

class GetQuestProgressesUseCase(
    private val questProgressRepository: QuestProgressRepository
) {
    suspend operator fun invoke(userIds:List<String>, questIds:List<String>): Result<List<QuestProgress>> {
        return questProgressRepository.getQuestProgresses(userIds, questIds)
    }
}