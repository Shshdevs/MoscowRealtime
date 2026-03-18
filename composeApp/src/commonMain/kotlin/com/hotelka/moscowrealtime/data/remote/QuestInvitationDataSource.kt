package com.hotelka.moscowrealtime.data.remote

import com.hotelka.moscowrealtime.data.dto.QuestInvitationDto
import kotlinx.coroutines.flow.Flow

interface QuestInvitationDataSource {
    suspend fun getQuestInvitation(questProgress: String): Result<QuestInvitationDto>
    suspend fun observeQuestInvitations(userId: String): Flow<Result<List<QuestInvitationDto>>>
    suspend fun sendQuestInvitation(questInvitation: QuestInvitationDto): Result<Unit>
    suspend fun removeUserFromInvitation(userId: String, questProgress: String): Result<Unit>
    suspend fun removeUserFromInvitations(userId: String): Result<Unit>
    suspend fun deleteQuestInvitationForQuest(questProgressId: String): Result<Unit>

}