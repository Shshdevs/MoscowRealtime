package com.hotelka.moscowrealtime.domain.usecase.questProgress

import com.hotelka.moscowrealtime.domain.exceptions.UserNotAuthenticatedException
import com.hotelka.moscowrealtime.domain.model.QuestInvitation
import com.hotelka.moscowrealtime.domain.model.QuestProgress
import com.hotelka.moscowrealtime.domain.repository.AuthRepository
import com.hotelka.moscowrealtime.domain.repository.QuestInvitationRepository
import com.hotelka.moscowrealtime.domain.repository.QuestProgressRepository
import com.hotelka.moscowrealtime.domain.repository.UserRepository
import com.hotelka.moscowrealtime.domain.model.detailed.QuestProgressDetailed

class RestartQuestProgressUseCase(
    private val authRepository: AuthRepository,
    private val questProgressRepository: QuestProgressRepository,
    private val questInvitationRepository: QuestInvitationRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(questProgressDetailed: QuestProgressDetailed): Result<Unit> = runCatching {
        val currUserId = authRepository.getCurrentUserId() ?: return Result.failure(
            UserNotAuthenticatedException()
        )
        questInvitationRepository.deleteQuestInvitationForQuest(questProgressDetailed.questProgress.id)
        questProgressRepository.deleteQuestProgress(questProgressDetailed.questProgress.id)
        val newQuestProgress = QuestProgress(
            id = "",
            questId = questProgressDetailed.quest.id,
            results = emptyList(),
            users = listOf(currUserId),
        )
        questProgressRepository.createQuestProgress(newQuestProgress).fold(
            onSuccess = { id ->
                val questInvitation = QuestInvitation(
                    questId = newQuestProgress.questId,
                    userFrom = currUserId,
                    usersInvited = questProgressDetailed.questProgress.users.filter { it != currUserId },
                    questProgress = id
                )
                questInvitationRepository.sendQuestInvitation(questInvitation)
                userRepository.getUser(currUserId).getOrNull()?.let { user ->
                    userRepository.updateUser(user.copy(currentQuestProgress = id))
                }
            },
            onFailure = { e ->
                return Result.failure(e)
            }
        )

    }
}