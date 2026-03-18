package com.hotelka.moscowrealtime.domain.usecase.quests

import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.domain.repository.QuestRepository

class RemoveParticipantUseCase(
    val questRepository: QuestRepository
) {
    suspend operator fun invoke(questId: String, userId: String): Result<Quest> {
        return questRepository.removeParticipant(questId, userId)
    }
}