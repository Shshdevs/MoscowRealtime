package com.hotelka.moscowrealtime.domain.usecase.quests

import co.touchlab.kermit.Logger
import com.hotelka.moscowrealtime.domain.model.QuestInvitation
import com.hotelka.moscowrealtime.domain.model.QuestProgress
import com.hotelka.moscowrealtime.domain.repository.QuestInvitationRepository
import com.hotelka.moscowrealtime.domain.repository.QuestProgressRepository
import com.hotelka.moscowrealtime.domain.repository.QuestRepository
import com.hotelka.moscowrealtime.domain.repository.UserRepository

class StartQuestUseCase(
    val questRepository: QuestRepository,
    val questInvitationRepository: QuestInvitationRepository,
    val questProgressRepository: QuestProgressRepository,
    val userRepository: UserRepository
) {
    suspend operator fun invoke(
        questId: String,
        userId: String,
        usersInvited: List<String>
    ): Result<Unit> {
        return startQuest(questId, userId, usersInvited)
    }

    private suspend fun startQuest(
        questId: String,
        userId: String,
        usersInvited: List<String>
    ): Result<Unit>  = runCatching {
        val questProgress = QuestProgress(
            questId = questId,
            results = listOf(),
            users = listOf(userId),
            id = ""
        )
        val questProgressId = questProgressRepository.createQuestProgress(questProgress)
            .getOrElse { e -> throw e }
        questRepository.addParticipant(questId, userId)
            .getOrElse { e ->
                Logger.withTag("Add participant failed").d { e.message.toString() }
                throw e
            }

        updateUserQuestProgress(userId, questProgressId)
            .getOrElse { e -> throw e }

        if (usersInvited.isNotEmpty()) {
            val questInvitation = QuestInvitation(
                questId = questId,
                userFrom = userId,
                usersInvited = usersInvited,
                questProgress = questProgressId
            )
            questInvitationRepository.sendQuestInvitation(questInvitation)
                .getOrElse { e -> throw e }
        }

        Result.success(Unit)
    }.map { Result.success(Unit) }

    private suspend fun updateUserQuestProgress(
        userId: String,
        questProgressId: String
    ): Result<Unit> {
        return userRepository.getUser(userId).fold(
            onSuccess = { user ->
                userRepository.updateUser(user.copy(currentQuestProgress = questProgressId)).fold(
                    onSuccess = {
                        Result.success(Unit)
                    },
                    onFailure = { e ->
                        Result.failure(e)
                    }
                )
            },
            onFailure = { e ->
                Result.failure(e)
            }
        )

    }

}