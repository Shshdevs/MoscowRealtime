package com.hotelka.moscowrealtime.domain.usecase.questInvitations

import com.hotelka.moscowrealtime.domain.model.QuestInvitation
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.domain.repository.QuestInvitationRepository

class SendQuestInvitationUseCase(
    private val questInvitationRepository: QuestInvitationRepository
) {
    suspend operator fun invoke(questId: String, userFrom: String, usersInvited: List<User>, questProgressId: String): Result<Unit> {
        val questInvitation = QuestInvitation(
            questId = questId,
            userFrom = userFrom,
            usersInvited = usersInvited.map { it.id },
            questProgress = questProgressId
        )
        return questInvitationRepository.sendQuestInvitation(questInvitation)
    }
}