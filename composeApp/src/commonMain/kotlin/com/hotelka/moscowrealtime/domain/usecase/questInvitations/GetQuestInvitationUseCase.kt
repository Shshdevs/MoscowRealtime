package com.hotelka.moscowrealtime.domain.usecase.questInvitations

import com.hotelka.moscowrealtime.domain.model.QuestInvitation
import com.hotelka.moscowrealtime.domain.repository.QuestInvitationRepository

class GetQuestInvitationUseCase(
    private val questInvitationRepository: QuestInvitationRepository
) {
    suspend operator fun invoke(questProgressId: String): Result<QuestInvitation> {
        return questInvitationRepository.getQuestInvitation(questProgressId)
    }
}


