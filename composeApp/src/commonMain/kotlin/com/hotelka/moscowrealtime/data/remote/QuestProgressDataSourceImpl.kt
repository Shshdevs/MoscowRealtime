package com.hotelka.moscowrealtime.data.remote

import com.hotelka.moscowrealtime.data.dto.QuestProgressDto
import com.hotelka.moscowrealtime.data.dto.VerificationQuestResultDto
import dev.gitlive.firebase.firestore.DocumentReference
import dev.gitlive.firebase.firestore.FieldPath
import dev.gitlive.firebase.firestore.FieldValue
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class QuestProgressDataSourceImpl(
    private val firestore: FirebaseFirestore
) : QuestProgressDataSource {

    override suspend fun getQuestProgressWithId(questProgressId: String): Result<QuestProgressDto> =
        try {
            val docRef = firestore.collection(QUEST_PROGRESS_COLLECTION).document(questProgressId)
            Result.success(docRef.get().data<QuestProgressDto>())
        } catch (e: Exception) {
            Result.failure(Exception("Error getting quest progress", e))
        }

    override suspend fun getQuestProgress(
        userId: String,
        questId: String
    ): Result<QuestProgressDto?> = try {
        val ref = getQuestProgressRef(userId, questId).getOrElse { e ->
            return Result.success(null)
        }
        val data = ref.get().data<QuestProgressDto>().copy(id = ref.id)
        Result.success(data)
    } catch (e: Exception) {
        Result.failure(Exception("Error getting quest progress", e))
    }

    override suspend fun getQuestProgresses(
        userIds: List<String>,
        questIds: List<String>
    ): Result<List<QuestProgressDto>> = try {
        if (userIds.isEmpty() || questIds.isEmpty()) {
            Result.success(emptyList())
        } else {
            val questProgresses = firestore.collection(QUEST_PROGRESS_COLLECTION).where {
                (FieldPath("users") containsAny userIds).and(FieldPath("questId") inArray questIds)
            }.get().documents.map { it.data<QuestProgressDto>() }
            Result.success(questProgresses)
        }
    } catch (e: Exception) {
        Result.failure(Exception("Error getting quest progresses", e))
    }

    override suspend fun observeQuestProgress(questProgressId: String): Flow<Result<QuestProgressDto>> =
        flow {
            try {
                firestore.collection(QUEST_PROGRESS_COLLECTION)
                    .document(questProgressId).snapshots.collect { doc ->
                        emit(Result.success(doc.data<QuestProgressDto>()))
                    }
            } catch (e: Exception) {
                emit(Result.failure(Exception("Observing quest progress error", e)))
            }
        }

    override suspend fun createQuestProgress(
        questProgress: QuestProgressDto
    ): Result<String> = try {
        val docRef = firestore.collection(QUEST_PROGRESS_COLLECTION).add(questProgress)
        firestore.collection(QUEST_PROGRESS_COLLECTION).document(docRef.id)
            .update(mapOf("id" to docRef.id))
        Result.success(docRef.id)
    } catch (e: Exception) {
        Result.failure(Exception("Error creating quest progress", e))
    }

    override suspend fun addVerificationQuestResult(
        verificationQuestResult: VerificationQuestResultDto,
        userId: String,
        questId: String
    ): Result<Unit> = try {
        val ref = getQuestProgressRef(userId, questId).getOrElse { e ->
            return Result.failure(e)
        }
        val verifyResultMap = mapOf(
            "awaitedLocation" to verificationQuestResult.awaitedLocation,
            "receivedLocation" to verificationQuestResult.receivedLocation,
            "result" to verificationQuestResult.result,
            "userId" to verificationQuestResult.userId,
            "success" to verificationQuestResult.success
        )
        ref.update(
            FieldPath("results") to FieldValue.arrayUnion(verifyResultMap)
        )
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception("Error updating quest progress verification results", e))
    }

    override suspend fun addUserToQuestProgress(
        userId: String,
        questProgressId: String
    ): Result<Unit> = try {
        firestore.collection(QUEST_PROGRESS_COLLECTION).document(questProgressId).update(
            FieldPath("users") to FieldValue.arrayUnion(userId)
        )
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception("Error updating users", e))
    }

    override suspend fun removeUserFromQuestProgresses(
        userId: String,
        questId: String
    ): Result<Unit> = try {
        val ref = getQuestProgressRef(userId, questId).getOrElse { e ->
            return Result.failure(e)
        }
        if (ref.get().data<QuestProgressDto>().users.size <= 1) {
            ref.delete()
        } else {
            ref.update(FieldPath("users") to FieldValue.arrayRemove(userId))
        }
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception("Error updating users", e))
    }

    override suspend fun deleteQuestProgress(
        questProgressId: String
    ): Result<Unit> = try {
        firestore.collection(QUEST_PROGRESS_COLLECTION).document(questProgressId).delete()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception("Error deleting quest progress", e))
    }

    override suspend fun removeUserFromQuestProgresses(userId: String): Result<Unit> = try {
        firestore.collection(QUEST_PROGRESS_COLLECTION).where { FieldPath("users") contains userId }
            .get().documents.map {
                it.reference.update(
                    FieldPath("users") to FieldValue.arrayRemove(
                        userId
                    )
                )
            }
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception("Error deleting user", e))
    }

    override suspend fun deleteQuestProgresses(questId: String): Result<Unit> = try {
        firestore.collection(QUEST_PROGRESS_COLLECTION)
            .where { FieldPath("questId") equalTo questId }
            .get().documents.forEach { it.reference.delete() }
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception("Error deleting quest progresses for $questId", e))
    }


    private suspend fun getQuestProgressRef(
        userId: String,
        questId: String
    ): Result<DocumentReference> = try {
        val results = firestore.collection(QUEST_PROGRESS_COLLECTION).where {
            (FieldPath("users") contains userId).and(FieldPath("questId") equalTo questId)
        }.get().documents
        if (results.isEmpty()) Result.failure(Exception("Quest progress doesn't exist"))
        else if (results.size > 1) Result.failure(Exception("Quest progress is duplicated"))
        else Result.success(results[0].reference)
    } catch (e: Exception) {
        Result.failure(Exception("Error getting quest progress", e))
    }

    companion object {
        private const val QUEST_PROGRESS_COLLECTION = "quest_progresses"
    }
}