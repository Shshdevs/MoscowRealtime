package com.hotelka.moscowrealtime.data.remote

import com.hotelka.moscowrealtime.data.dto.FriendRequestDto
import dev.gitlive.firebase.firestore.DocumentReference
import dev.gitlive.firebase.firestore.FieldPath
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class FriendRequestDataSourceImpl(
    private val firestore: FirebaseFirestore
) : FriendRequestDataSource {
    override suspend fun isFriendRequestsExists(
        userFrom: String,
        userTo: String
    ): Result<Boolean> = try {
        getFriendRequestRef(userFrom, userTo).getOrElse { e ->
            return Result.success(false)
        }
        Result.success(true)
    } catch (e: Exception) {
        Result.failure(Exception("Error checking friend request existence", e))
    }

    override suspend fun observeFriendRequests(userId: String): Flow<Result<List<FriendRequestDto>>> =
        firestore.collection(FRIEND_REQUESTS_COLLECTION).where {
            (FieldPath("userTo") equalTo userId).and("status" equalTo "sent")
        }.snapshots.map { snapshot ->
            try {
                val requests = snapshot.documents.map { doc ->
                    doc.data<FriendRequestDto>()
                }
                Result.success(requests)
            } catch (e: Exception) {
                Result.failure(Exception("Error observing friend requests", e))
            }
        }.catch { e ->
            emit(Result.failure(Exception("Error observing friend requests", e)))
        }


    override suspend fun updateFriendRequest(friendRequest: FriendRequestDto): Result<Unit> = try {
        val ref = getFriendRequestRef(friendRequest.userFrom, friendRequest.userTo).getOrElse { e ->
            return Result.failure(e)
        }
        ref.set(friendRequest)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception("Error updating friend requests", e))
    }

    override suspend fun deleteFriendRequest(
        userFrom: String,
        userTo: String
    ): Result<Unit> = try {
        val ref = getFriendRequestRef(userFrom, userTo).getOrElse { e ->
            return Result.success(Unit)
        }
        ref.delete()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception("Error deleting friend requests", e))
    }

    override suspend fun sendFriendRequest(friendRequest: FriendRequestDto): Result<Unit> = try {
        isFriendRequestsExists(friendRequest.userFrom, friendRequest.userTo).onSuccess { exists ->
            if (!exists) {
                firestore.collection(FRIEND_REQUESTS_COLLECTION).add(friendRequest)
            }
        }.onFailure { return Result.failure(it) }
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception("Error creating friend requests", e))
    }

    override suspend fun removeFriendInvitationsForUser(userId: String): Result<Unit> = try {
        firestore.collection(FRIEND_REQUESTS_COLLECTION).where {
            (FieldPath("userFrom") equalTo userId).or(FieldPath("userTo") equalTo userId)
        }.get().documents.forEach { it.reference.delete() }
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception("Error deleting friend requests from user $userId", e))
    }

    override fun observeFriendRequest(
        currUserId: String,
        userId: String
    ): Flow<Result<FriendRequestDto?>> =
        flow {
            try {
                firestore.collection(FRIEND_REQUESTS_COLLECTION).where {
                    ((FieldPath("userTo") equalTo userId).and(FieldPath("userFrom") equalTo currUserId))
                        .or((FieldPath("userTo") equalTo currUserId).and(FieldPath("userFrom") equalTo userId))
                }.limit(1).snapshots.collect { collector ->
                    val request = collector.documents.map { doc ->
                        doc.data<FriendRequestDto>()
                    }.getOrNull(0)
                    emit(Result.success(request))
                }
            } catch (e: Exception) {
                emit(Result.failure(Exception("Error observing friend request", e)))
            }
        }

    private suspend fun getFriendRequestRef(
        userFrom: String,
        userTo: String
    ): Result<DocumentReference> = try {
        val docs = firestore.collection(FRIEND_REQUESTS_COLLECTION).where {
            (FieldPath("userFrom") equalTo userFrom).and(FieldPath("userTo") equalTo userTo)
        }.limit(1).get().documents
        if (docs.isEmpty()) Result.failure(Exception("Friend request doesn't exist"))
        else {
            val ref = docs[0].reference
            Result.success(ref)
        }
    } catch (e: Exception) {
        Result.failure(Exception("Error getting friend request document reference", e))
    }

    companion object {
        private const val FRIEND_REQUESTS_COLLECTION = "friend_requests"
    }
}