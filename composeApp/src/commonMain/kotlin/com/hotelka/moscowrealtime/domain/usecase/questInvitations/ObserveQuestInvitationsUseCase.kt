package com.hotelka.moscowrealtime.domain.usecase.questInvitations

import com.hotelka.moscowrealtime.domain.exceptions.UserNotAuthenticatedException
import com.hotelka.moscowrealtime.domain.model.QuestInvitation
import com.hotelka.moscowrealtime.domain.repository.AuthRepository
import com.hotelka.moscowrealtime.domain.repository.QuestInvitationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ObserveQuestInvitationsUseCase(
    private val authRepository: AuthRepository,
    private val questInvitationRepository: QuestInvitationRepository
) {
    suspend operator fun invoke(): Flow<Result<List<QuestInvitation>>> {
        val userId = authRepository.getCurrentUserId() ?: return flow {
            emit(Result.failure(UserNotAuthenticatedException()))
        }
        return questInvitationRepository.observeQuestInvitations(userId)
    }
}