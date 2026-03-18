package com.hotelka.moscowrealtime.data.remote

import com.hotelka.moscowrealtime.data.dto.QuestInvitationDto
import dev.gitlive.firebase.firestore.FieldPath
import dev.gitlive.firebase.firestore.FieldValue
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class QuestInvitationDataSourceImpl(
    private val firestore: FirebaseFirestore
) : QuestInvitationDataSource {
    override suspend fun getQuestInvitation(questProgress: String): Result<QuestInvitationDto> =
        try {
            val docs = firestore.collection(INVITATIONS_COLLECTION).where {
                "questProgress" equalTo questProgress
            }.limit(1).get().documents
            if (docs.isEmpty()) Result.failure(Exception("Quest invitation doesn't exist"))
            else Result.success(docs.get(0).data())
        } catch (e: Exception) {
            Result.failure(Exception("Error getting quest invitation", e))
        }

    override suspend fun observeQuestInvitations(userId: String): Flow<Result<List<QuestInvitationDto>>> =
        firestore.collection(INVITATIONS_COLLECTION).where {
            "usersInvited" contains userId
        }.snapshots.map { snapshot ->
            try {
                val invitations = snapshot.documents.map { it.data<QuestInvitationDto>() }
                Result.success(invitations)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }.catch { e ->
            emit(Result.failure(Exception("Fetching invitations error", e)))
        }

    override suspend fun sendQuestInvitation(questInvitation: QuestInvitationDto): Result<Unit> =
        try {
            firestore.collection(INVITATIONS_COLLECTION).add(questInvitation)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(Exception("Error sending quest invitation", e))
        }

    override suspend fun removeUserFromInvitation(
        userId: String,
        questProgress: String
    ): Result<Unit> =
        try {
            val docs = firestore.collection(INVITATIONS_COLLECTION).where {
                "questProgress" equalTo questProgress
            }.limit(1).get().documents
            if (docs.isEmpty()) Result.failure(Exception("Quest invitation doesn't exist"))
            else {
                val ref = docs[0].reference
                ref.update(FieldPath("usersInvited") to FieldValue.arrayRemove(userId))
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error removing user from quest invitation", e))
        }

    override suspend fun removeUserFromInvitations(userId: String): Result<Unit> = try {
        firestore.collection(INVITATIONS_COLLECTION).where {
            "usersInvited" contains userId
        }.get().documents.forEach { doc ->
            doc.reference.update(FieldPath("usersInvited") to FieldValue.arrayRemove(userId))
        }
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception("Error removing user from quest invitations", e))
    }

    override suspend fun deleteQuestInvitationForQuest(questProgressId: String): Result<Unit> =
        try {
            firestore.collection(INVITATIONS_COLLECTION).where {
                "questProgress" equalTo questProgressId
            }.get().documents.map { it.reference.delete() }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(Exception("Error deleting quest invitations", e))
        }

    companion object {
        private const val INVITATIONS_COLLECTION = "quest_invitations"
    }
}