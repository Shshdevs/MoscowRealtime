package com.hotelka.moscowrealtime.domain.repository

import com.hotelka.moscowrealtime.domain.model.Quest

interface QuestRepository {
    suspend fun createQuest(quest: Quest): Result<String>
    suspend fun getQuest(id: String): Result<Quest>
    suspend fun getQuests(): Result<List<Quest>>
    suspend fun getQuestsByIds(ids: List<String>): Result<List<Quest>>
    suspend fun addParticipant(questId: String, userId: String): Result<Quest>
    suspend fun removeParticipant(questId: String, userId: String): Result<Quest>
    suspend fun removeFromParticipants(userId: String): Result<Unit>
    suspend fun deleteQuest(questId: String): Result<Unit>
    suspend fun getUserQuests(userId: String): Result<List<Quest>>
}