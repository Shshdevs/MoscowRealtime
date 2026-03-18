package com.hotelka.moscowrealtime.domain.repository

import com.hotelka.moscowrealtime.domain.model.QuestProgress
import com.hotelka.moscowrealtime.domain.model.VerificationQuestResult
import kotlinx.coroutines.flow.Flow

interface QuestProgressRepository {
    suspend fun getQuestProgressWithId(questProgressId: String): Result<QuestProgress>
    suspend fun getQuestProgress(userId: String, questId: String): Result<QuestProgress?>
    suspend fun getQuestProgresses(userIds: List<String>, questIds: List<String>): Result<List<QuestProgress>>
    suspend fun observeQuestProgress(questProgressId: String): Flow<Result<QuestProgress>>
    suspend fun createQuestProgress(questProgress: QuestProgress): Result<String>
    suspend fun addVerificationQuestResult(
        verificationQuestResult: VerificationQuestResult,
        userId: String,
        questId: String
    ): Result<Unit>
    suspend fun addUserToQuestProgress(userId: String, questProgressId: String): Result<Unit>
    suspend fun removeUserFromQuestProgress(userId: String, questId: String): Result<Unit>
    suspend fun deleteQuestProgress(questProgressId: String): Result<Unit>
    suspend fun removeUserFromQuestProgresses(userId: String): Result<Unit>
    suspend fun deleteQuestProgresses(questId: String): Result<Unit>
}