package com.hotelka.moscowrealtime.data.repository

import com.hotelka.moscowrealtime.data.remote.QuestInvitationDataSource
import com.hotelka.moscowrealtime.data.mapper.toDomain
import com.hotelka.moscowrealtime.data.mapper.toDto
import com.hotelka.moscowrealtime.domain.model.QuestInvitation
import com.hotelka.moscowrealtime.domain.repository.QuestInvitationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuestInvitationRepositoryImpl(
    private val questInvitationDataSource: QuestInvitationDataSource
) : QuestInvitationRepository {
    override suspend fun getQuestInvitation(questProgress: String): Result<QuestInvitation> {
        return questInvitationDataSource.getQuestInvitation(questProgress).map { it.toDomain() }
    }

    override suspend fun observeQuestInvitations(userId: String): Flow<Result<List<QuestInvitation>>> {
        return questInvitationDataSource.observeQuestInvitations(userId).map { result -> result.map { list -> list.map { it.toDomain() } } }
    }

    override suspend fun sendQuestInvitation(questInvitation: QuestInvitation): Result<Unit> {
        return questInvitationDataSource.sendQuestInvitation(questInvitation.toDto())
    }

    override suspend fun removeUserFromInvitation(
        userId: String,
        questProgress: String
    ): Result<Unit> {
        return questInvitationDataSource.removeUserFromInvitation(userId, questProgress)
    }

    override suspend fun removeUserFromInvitations(userId: String): Result<Unit> {
        return questInvitationDataSource.removeUserFromInvitations(userId)
    }

    override suspend fun deleteQuestInvitationForQuest(questProgressId: String): Result<Unit> {
        return questInvitationDataSource.deleteQuestInvitationForQuest(questProgressId)
    }
}