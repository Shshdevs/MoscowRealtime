package com.hotelka.moscowrealtime.domain.usecase.questInvitations

import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.domain.model.QuestInvitation
import com.hotelka.moscowrealtime.domain.repository.QuestInvitationRepository
import com.hotelka.moscowrealtime.domain.repository.QuestProgressRepository
import com.hotelka.moscowrealtime.domain.repository.QuestRepository
import com.hotelka.moscowrealtime.domain.repository.UserRepository

class AcceptQuestInvitationUseCase(
    private val userRepository: UserRepository,
    private val questsRepository: QuestRepository,
    private val questProgressRepository: QuestProgressRepository,
    private val questInvitationRepository: QuestInvitationRepository
) {
    suspend operator fun invoke(userId: String, questInvitation: QuestInvitation) = runCatching {
        addUserToQuestProgress(userId, questInvitation.questProgress)
        removeUserFromInvitation(userId, questInvitation.questProgress)
        addParticipantToQuest(userId, questInvitation.questId)
        updateUserCurrentQuestProgress(userId, questInvitation.questProgress)
    }

    private suspend fun updateUserCurrentQuestProgress(userId: String, questProgress: String) {
        userRepository.getUser(userId).getOrNull()
            ?.let { userRepository.updateUser(it.copy(currentQuestProgress = questProgress)) }
    }

    private suspend fun addUserToQuestProgress(
        userId: String,
        questProgressId: String
    ): Result<Unit> {
        return questProgressRepository.addUserToQuestProgress(userId, questProgressId)
    }

    private suspend fun removeUserFromInvitation(
        userId: String,
        questProgressId: String
    ): Result<Unit> {
        return questInvitationRepository.removeUserFromInvitation(userId, questProgressId)
    }

    private suspend fun addParticipantToQuest(userid: String, questId: String): Result<Quest> {
        return questsRepository.addParticipant(userid, questId)
    }
}