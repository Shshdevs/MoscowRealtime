package com.hotelka.moscowrealtime.data.remote

import co.touchlab.kermit.Logger
import com.hotelka.moscowrealtime.data.dto.GroupDto
import dev.gitlive.firebase.firestore.FieldPath
import dev.gitlive.firebase.firestore.FieldValue
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GroupDataSourceImpl(
    private val firestore: FirebaseFirestore
) : GroupDataSource {

    override suspend fun createGroup(group: GroupDto): Result<Unit> = try {
        val docRef = firestore.collection(GROUPS_COLLECTION).add(group)
        docRef.update(mapOf("id" to docRef.id))
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception("Error creating group", e))
    }

    override suspend fun observeOrganizationGroups(organizationId: String): Flow<Result<List<GroupDto>>> =
        firestore.collection(GROUPS_COLLECTION)
            .where { "organizationId" equalTo organizationId }.snapshots.map { snapshot ->
                try {
                    val groups = snapshot.documents.map { it.data<GroupDto>() }
                    Result.success(groups)
                } catch (e: Exception) {
                    Result.failure(
                        Exception(
                            "Error observing groups for organization: $organizationId",
                            e
                        )
                    )
                }
            }.catch { e ->
                emit(
                    Result.failure(
                        Exception(
                            "Error observing groups for organization: $organizationId",
                            e
                        )
                    )
                )
            }

    override suspend fun getOrganizationGroups(organizationId: String): Result<List<GroupDto>> =
        try {
            val group = firestore.collection(GROUPS_COLLECTION)
                .where { "organizationId" equalTo organizationId }
                .get().documents.map { it.data<GroupDto>() }
            Result.success(group)
        } catch (e: Exception) {
            Result.failure(Exception("Error getting group with id $organizationId", e))
        }

    override suspend fun removeUserFromGroup(
        userId: String,
        groupId: String
    ): Result<Unit> = try {
        firestore.collection(GROUPS_COLLECTION).document(groupId).update(
            FieldPath("users") to FieldValue.arrayRemove(userId)
        )
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(
            Exception(
                "Error removing user with id $userId to group with id $groupId",
                e
            )
        )
    }

    override suspend fun deleteUserFromOrganizationGroups(
        userId: String,
        organizationId: String
    ): Result<Unit> = try {
        firestore.collection(GROUPS_COLLECTION)
            .where { ("organizationId" equalTo organizationId).and(FieldPath("users") contains userId) }
            .get().documents.map {
                it.reference.update(
                    FieldPath("users") to FieldValue.arrayRemove(
                        userId
                    )
                )
            }
        firestore.collection(GROUPS_COLLECTION)
            .where { ("organizationId" equalTo organizationId).and(FieldPath("organizerId") equalTo userId) }
            .get().documents.map { it.reference.delete() }
        Result.success(Unit)
    } catch (e: Exception) {
        Logger.withTag("Index").d { e.message.toString() }
        Result.failure(Exception("Error removing user with id $userId from groups", e))
    }

    override suspend fun addUserToGroup(
        userId: String,
        groupId: String
    ): Result<Unit> = try {
        firestore.collection(GROUPS_COLLECTION).document(groupId).update(
            FieldPath("users") to FieldValue.arrayUnion(userId)
        )
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception("Error adding user with id $userId to group with id $groupId", e))
    }

    override suspend fun pinQuest(
        groupId: String,
        questId: String
    ): Result<Unit> = try {
        firestore.collection(GROUPS_COLLECTION).document(groupId).update(
            FieldPath("pinnedQuestId") to questId
        )
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception("Error pin quest with id $questId for group with id $groupId", e))
    }

    override suspend fun deleteGroup(groupId: String): Result<Unit> = try {
        firestore.collection(GROUPS_COLLECTION).document(groupId).delete()
        Result.success(Unit)
    } catch (e: Exception){
        Result.failure(Exception("Error deliting group", e))
    }


    companion object {
        private const val GROUPS_COLLECTION = "groups"
    }
}