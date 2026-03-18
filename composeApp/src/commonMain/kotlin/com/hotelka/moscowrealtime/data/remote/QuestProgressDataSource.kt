package com.hotelka.moscowrealtime.data.remote

import com.hotelka.moscowrealtime.data.dto.QuestProgressDto
import com.hotelka.moscowrealtime.data.dto.VerificationQuestResultDto
import com.hotelka.moscowrealtime.domain.model.QuestProgress
import kotlinx.coroutines.flow.Flow

interface QuestProgressDataSource {
    suspend fun getQuestProgressWithId(questProgressId: String): Result<QuestProgressDto>
    suspend fun getQuestProgress(userId: String, questId: String): Result<QuestProgressDto?>
    suspend fun getQuestProgresses(userIds: List<String>, questIds: List<String>): Result<List<QuestProgressDto>>
    suspend fun observeQuestProgress(questProgressId: String): Flow<Result<QuestProgressDto>>
    suspend fun createQuestProgress(questProgress: QuestProgressDto): Result<String>
    suspend fun addVerificationQuestResult(
        verificationQuestResult: VerificationQuestResultDto,
        userId: String,
        questId: String
    ): Result<Unit>
    suspend fun addUserToQuestProgress(userId:String, questProgressId: String): Result<Unit>
    suspend fun removeUserFromQuestProgresses(userId: String, questId: String): Result<Unit>
    suspend fun deleteQuestProgress(questProgressId: String): Result<Unit>
    suspend fun removeUserFromQuestProgresses(userId: String): Result<Unit>
    suspend fun deleteQuestProgresses(questId: String): Result<Unit>
}