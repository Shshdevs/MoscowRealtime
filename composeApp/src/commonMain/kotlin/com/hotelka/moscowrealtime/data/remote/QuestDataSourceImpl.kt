package com.hotelka.moscowrealtime.data.remote

import com.hotelka.moscowrealtime.data.dto.QuestDto
import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.FieldPath
import dev.gitlive.firebase.firestore.FieldValue
import dev.gitlive.firebase.firestore.FirebaseFirestore

class QuestDataSourceImpl(
    private val firestore: FirebaseFirestore
) : QuestDataSource {
    override suspend fun createQuest(quest: QuestDto): Result<String> = try {
        val ref = firestore.collection(QUESTS_COLLECTION).add(quest)
        ref.update(FieldPath("id") to ref.id)
        Result.success(ref.id)
    } catch (e: Exception) {
        Result.failure(Exception("Error creating quest", e))
    }

    override suspend fun getQuest(id: String): Result<QuestDto> = try {
        val quest = firestore.collection(QUESTS_COLLECTION).document(id).get().data<QuestDto>()
        Result.success(quest)
    } catch (e: Exception) {
        Result.failure(Exception("Error getting quest", e))
    }

    override suspend fun getQuestsRecommended(): Result<List<QuestDto>> = try {
        val quests = firestore.collection(QUESTS_COLLECTION)
            .orderBy("participantsAmount", Direction.DESCENDING)
            .get().documents.map { it.data<QuestDto>() }
        Result.success(quests)
    } catch (e: Exception) {
        Result.failure(Exception("Fetching quests error", e))
    }

    override suspend fun getQuestsByIds(ids: List<String>): Result<List<QuestDto>> = try {
        if (ids.isEmpty()) {
            Result.success(emptyList())
        } else {
            val quests = firestore.collection(QUESTS_COLLECTION)
                .where { "__name__" inArray ids }
                .get().documents.map { it.data<QuestDto>() }
            Result.success(quests)
        }
    } catch (e: Exception) {
        Result.failure(Exception("Fetching quests error", e))
    }

    override suspend fun addParticipant(
        questId: String,
        userId: String
    ): Result<QuestDto> = try {
        val ref = firestore.collection(QUESTS_COLLECTION).document(questId)
        ref.update(
            FieldPath("participantIds") to FieldValue.arrayUnion(userId),
            FieldPath("participantsAmount") to FieldValue.increment(1)
        )
        val quest = ref.get().data<QuestDto>()
        Result.success(quest)
    } catch (e: Exception) {
        Result.failure(Exception("Error adding user", e))
    }

    override suspend fun removeParticipant(
        questId: String,
        userId: String
    ): Result<QuestDto> = try {
        val ref = firestore.collection(QUESTS_COLLECTION).document(questId)
        ref.update(
            FieldPath("participantIds") to FieldValue.arrayRemove(userId),
            FieldPath("participantsAmount") to FieldValue.increment(-1)
        )
        val quest = ref.get().data<QuestDto>()
        Result.success(quest)
    } catch (e: Exception) {
        Result.failure(Exception("Error adding user", e))
    }

    override suspend fun removeFromParticipants(userId: String): Result<Unit> = try {
        firestore.collection(QUESTS_COLLECTION).where {
            "participantIds" contains userId
        }.get().documents.forEach { removeParticipant(it.reference.id, userId) }
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception("Error removing user", e))
    }

    override suspend fun deleteQuest(questId: String): Result<Unit> = try {
        firestore.collection(QUESTS_COLLECTION).document(questId).delete()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception("Error deleting quest", e))
    }

    override suspend fun getUserQuests(userId: String): Result<List<QuestDto>> = try {
        val quests = firestore.collection(QUESTS_COLLECTION).where {
            (FieldPath("participantIds") contains userId).or(
                FieldPath("authorId") equalTo userId
            )
        }.get().documents.map { it.data<QuestDto>() }
        Result.success(quests)
    } catch (e: Exception) {
        Result.failure(Exception("Error getting user quests", e))
    }

    companion object {
        private const val QUESTS_COLLECTION = "quests"
    }
}