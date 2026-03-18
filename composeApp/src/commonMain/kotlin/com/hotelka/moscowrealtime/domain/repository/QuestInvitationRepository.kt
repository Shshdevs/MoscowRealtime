package com.hotelka.moscowrealtime.domain.repository

import com.hotelka.moscowrealtime.domain.model.QuestInvitation
import kotlinx.coroutines.flow.Flow

interface QuestInvitationRepository {
    suspend fun getQuestInvitation(questProgress: String): Result<QuestInvitation>
    suspend fun observeQuestInvitations(userId: String): Flow<Result<List<QuestInvitation>>>
    suspend fun sendQuestInvitation(questInvitation: QuestInvitation): Result<Unit>
    suspend fun removeUserFromInvitation(userId: String, questProgress: String): Result<Unit>
    suspend fun removeUserFromInvitations(userId: String): Result<Unit>
    suspend fun deleteQuestInvitationForQuest(questProgressId: String): Result<Unit>

}