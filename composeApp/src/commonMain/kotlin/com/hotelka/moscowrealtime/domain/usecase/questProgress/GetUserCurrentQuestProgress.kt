package com.hotelka.moscowrealtime.domain.usecase.questProgress

import com.hotelka.moscowrealtime.domain.model.QuestProgress
import com.hotelka.moscowrealtime.domain.repository.QuestProgressRepository
import com.hotelka.moscowrealtime.domain.repository.UserRepository

class GetUserCurrentQuestProgress(
    private val userRepository: UserRepository,
    private val questProgressRepository: QuestProgressRepository
) {
    suspend operator fun invoke(userId: String): Result<QuestProgress?> =
        userRepository.getUser(userId).fold(
            onSuccess = { user ->
                if (user.currentQuestProgress != null) {
                    questProgressRepository.getQuestProgressWithId(user.currentQuestProgress).fold(
                        onSuccess = { questProgress ->
                            Result.success(questProgress)
                        },
                        onFailure = {
                            Result.failure(it)
                        }
                    )
                } else {
                    Result.success(null)
                }
            },
            onFailure = { Result.failure(it) }
        )
}