package com.hotelka.moscowrealtime.domain.usecase.questInvitations

import com.hotelka.moscowrealtime.domain.exceptions.UserNotAuthenticatedException
import com.hotelka.moscowrealtime.domain.model.QuestInvitation
import com.hotelka.moscowrealtime.domain.repository.AuthRepository
import com.hotelka.moscowrealtime.domain.repository.QuestInvitationRepository

class DeclineQuestInvitationUseCase(
    private val authRepository: AuthRepository,
    private val questInvitationRepository: QuestInvitationRepository
) {
    suspend operator fun invoke(questInvitation: QuestInvitation): Result<Unit> {
        val currUser = authRepository.getCurrentUserId() ?: return Result.failure(
            UserNotAuthenticatedException()
        )
        return questInvitationRepository.removeUserFromInvitation(currUser, questInvitation.questProgress)
    }
}