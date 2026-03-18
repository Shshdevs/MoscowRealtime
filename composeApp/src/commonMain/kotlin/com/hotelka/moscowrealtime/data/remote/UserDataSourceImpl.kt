package com.hotelka.moscowrealtime.data.remote

import co.touchlab.kermit.Logger
import com.hotelka.moscowrealtime.data.dto.UserDto
import dev.gitlive.firebase.firestore.FieldPath
import dev.gitlive.firebase.firestore.FieldValue
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class UserDataSourceImpl(
    private val firestore: FirebaseFirestore
) : UserDataSource {

    override suspend fun getUser(id: String): Result<UserDto> = try {
        val user = firestore.collection(USERS_COLLECTION).document(id).get().data<UserDto>()
        Result.success(user)
    } catch (e: Exception) {
        Result.failure(Exception("Fetching user error", e))
    }

    override suspend fun observeUser(id: String): Flow<Result<UserDto>> =
        firestore.collection(USERS_COLLECTION)
            .document(id)
            .snapshots
            .map { documentSnapshot ->
                try {
                    val userData = documentSnapshot.data<UserDto>()
                    Result.success(userData)
                } catch (e: Exception) {
                    Result.failure(e)
                }
            }.catch { e -> emit(Result.failure(Exception("Error observing user", e))) }


    override suspend fun getUsersByIds(ids: List<String>): Result<List<UserDto>> = try {
        if (ids.isEmpty()) {
            Result.success(emptyList())
        } else {
            val result = firestore.collection(USERS_COLLECTION).where {
                FieldPath("__name__") inArray ids
            }.get().documents.map { it.data<UserDto>() }
            Result.success(result)
        }
    } catch (e: Exception) {
        Result.failure(Exception("Error fetching users", e))
    }

    override suspend fun validateUsername(username: String): Result<Boolean> = try {
        val results = firestore.collection(USERS_COLLECTION).where { "username" equalTo username }
            .get().documents
        Result.success(results.isEmpty())
    } catch (e: Exception) {
        Result.failure(Exception("Error validating username", e))
    }

    override suspend fun updateUser(user: UserDto): Result<Unit> = try {
        firestore.collection(USERS_COLLECTION).document(user.id).set(user)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception("Updating user error", e))
    }

    override suspend fun createUser(user: UserDto): Result<Unit> =
        try {
            val docSnapshot = firestore.collection(USERS_COLLECTION).document(user.id)
            if (docSnapshot.get().exists) Result.success(Unit)
            else Result.success(docSnapshot.set(user))
        } catch (e: Exception) {
            Logger.withTag("user create error").d { e.message.toString() }
            Result.failure(Exception("Error Creating User", e))
        }

    override suspend fun addFriend(
        userId: String,
        friendId: String
    ): Result<Unit> = try {
        val result = firestore.collection(USERS_COLLECTION).document(userId).update(
            FieldPath("friends") to FieldValue.arrayUnion(friendId)
        )
        Result.success(result)
    } catch (e: Exception) {
        Result.failure(Exception("Adding friend error", e))
    }

    override suspend fun removeFriend(
        userId: String,
        friendId: String
    ): Result<Unit> = try {
        val result = firestore.collection(USERS_COLLECTION).document(userId).update(
            FieldPath("friends") to FieldValue.arrayRemove(friendId)
        )
        Result.success(result)
    } catch (e: Exception) {
        Result.failure(Exception("Adding friend error", e))
    }


    override suspend fun deleteUser(id: String): Result<Unit> = try {
        firestore.collection(USERS_COLLECTION).document(id).delete()
        firestore.collection(USERS_COLLECTION).where {
            FieldPath("friends") contains id
        }.get().documents.map {
            it.reference.update(
                FieldPath("friends") to FieldValue.arrayRemove(
                    id
                )
            )
        }
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(Exception("Error deleting user", e))
    }

    companion object {
        private const val USERS_COLLECTION = "users"
    }
}