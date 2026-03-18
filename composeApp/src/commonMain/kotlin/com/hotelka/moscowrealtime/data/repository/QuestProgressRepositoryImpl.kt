package com.hotelka.moscowrealtime.data.repository

import com.hotelka.moscowrealtime.data.remote.QuestProgressDataSource
import com.hotelka.moscowrealtime.data.mapper.toDomain
import com.hotelka.moscowrealtime.data.mapper.toDto
import com.hotelka.moscowrealtime.domain.model.QuestProgress
import com.hotelka.moscowrealtime.domain.model.VerificationQuestResult
import com.hotelka.moscowrealtime.domain.repository.QuestProgressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuestProgressRepositoryImpl(
    private val questProgressDataSource: QuestProgressDataSource
) : QuestProgressRepository {

    override suspend fun getQuestProgressWithId(questProgressId: String): Result<QuestProgress> {
        return questProgressDataSource.getQuestProgressWithId(questProgressId).map { it.toDomain() }
    }

    override suspend fun getQuestProgress(
        userId: String,
        questId: String
    ): Result<QuestProgress?> {
        return questProgressDataSource.getQuestProgress(userId, questId).map { it?.toDomain() }
    }

    override suspend fun getQuestProgresses(
        userIds: List<String>,
        questIds: List<String>
    ): Result<List<QuestProgress>> {
        return questProgressDataSource.getQuestProgresses(userIds, questIds).map { results -> results.map { it.toDomain() } }
    }

    override suspend fun observeQuestProgress(questProgressId: String): Flow<Result<QuestProgress>> {
        return questProgressDataSource.observeQuestProgress(questProgressId).map { result -> result.map { it.toDomain() } }
    }

    override suspend fun createQuestProgress(questProgress: QuestProgress): Result<String> {
        return questProgressDataSource.createQuestProgress(questProgress.toDto())
    }

    override suspend fun addVerificationQuestResult(
        verificationQuestResult: VerificationQuestResult,
        userId: String,
        questId: String
    ): Result<Unit> {
        return questProgressDataSource.addVerificationQuestResult(
            verificationQuestResult.toDto(),
            userId,
            questId
        )
    }

    override suspend fun addUserToQuestProgress(
        userId: String,
        questProgressId: String
    ): Result<Unit> {
        return questProgressDataSource.addUserToQuestProgress(userId, questProgressId)
    }

    override suspend fun removeUserFromQuestProgress(
        userId: String,
        questId: String
    ): Result<Unit> {
        return questProgressDataSource.removeUserFromQuestProgresses(userId, questId)
    }

    override suspend fun deleteQuestProgress(questProgressId: String): Result<Unit> {
        return questProgressDataSource.deleteQuestProgress(questProgressId)
    }

    override suspend fun removeUserFromQuestProgresses(userId: String): Result<Unit> {
        return questProgressDataSource.removeUserFromQuestProgresses(userId)
    }

    override suspend fun deleteQuestProgresses(questId: String): Result<Unit> {
        return questProgressDataSource.deleteQuestProgresses(questId)
    }

}