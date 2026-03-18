package com.hotelka.moscowrealtime.domain.usecase.questProgress

import com.hotelka.moscowrealtime.domain.model.VerificationQuestResult
import com.hotelka.moscowrealtime.domain.repository.AuthRepository
import com.hotelka.moscowrealtime.domain.repository.QuestProgressRepository

class AddVerificationQuestResultUseCase(
    private val authRepository: AuthRepository,
    private val questProgressRepository: QuestProgressRepository
) {
    suspend operator fun invoke(verificationQuestResult: VerificationQuestResult, questId: String): Result<Unit> {
        val userId = authRepository.getCurrentUserId()?:return Result.failure(Exception("User not authenticated"))
        return questProgressRepository.addVerificationQuestResult(verificationQuestResult, userId, questId)
    }
}