package com.hotelka.moscowrealtime.data.repository

import com.hotelka.moscowrealtime.data.remote.QuestDataSource
import com.hotelka.moscowrealtime.data.mapper.QuestMapper
import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.domain.repository.QuestRepository

class QuestRepositoryImpl(
    private val dataSource: QuestDataSource,
    private val questMapper: QuestMapper
) : QuestRepository {
    override suspend fun createQuest(quest: Quest): Result<String> {
        return dataSource.createQuest(questMapper.toDto(quest))
    }

    override suspend fun getQuest(id: String): Result<Quest> {
        return dataSource.getQuest(id).map { questMapper.toDomain(it) }
    }

    override suspend fun getQuests(): Result<List<Quest>> {
        return dataSource.getQuestsRecommended()
            .map { list -> list.map { questMapper.toDomain(it) } }
    }

    override suspend fun getQuestsByIds(ids: List<String>): Result<List<Quest>> {
        return dataSource.getQuestsByIds(ids).map { list -> list.map { questMapper.toDomain(it) } }
    }

    override suspend fun addParticipant(
        questId: String,
        userId: String
    ): Result<Quest> {
        return dataSource.addParticipant(questId, userId).map { questMapper.toDomain(it) }
    }

    override suspend fun removeParticipant(
        questId: String,
        userId: String
    ): Result<Quest> {
        return dataSource.removeParticipant(questId, userId).map { questMapper.toDomain(it) }
    }

    override suspend fun removeFromParticipants(userId: String): Result<Unit> {
        return dataSource.removeFromParticipants(userId)
    }

    override suspend fun deleteQuest(questId: String): Result<Unit> {
        return dataSource.deleteQuest(questId)
    }

    override suspend fun getUserQuests(userId: String): Result<List<Quest>> {
        return dataSource.getUserQuests(userId).map { list -> list.map { dto -> questMapper.toDomain(dto) } }
    }
}