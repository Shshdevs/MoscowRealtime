package com.hotelka.moscowrealtime.data.remote

import com.hotelka.moscowrealtime.data.dto.QuestDto

interface QuestDataSource {
    suspend fun createQuest(quest: QuestDto): Result<String>
    suspend fun getQuest(id: String): Result<QuestDto>
    suspend fun getQuestsRecommended(): Result<List<QuestDto>>
    suspend fun getQuestsByIds(ids: List<String>): Result<List<QuestDto>>
    suspend fun addParticipant(questId: String, userId: String): Result<QuestDto>
    suspend fun removeParticipant(questId: String, userId: String): Result<QuestDto>
    suspend fun removeFromParticipants(userId: String): Result<Unit>
    suspend fun deleteQuest(questId: String): Result<Unit>
    suspend fun getUserQuests(userId: String): Result<List<QuestDto>>
}