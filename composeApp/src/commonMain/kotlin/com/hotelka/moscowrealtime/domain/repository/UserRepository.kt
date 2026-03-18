package com.hotelka.moscowrealtime.domain.repository

import com.hotelka.moscowrealtime.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUser(id: String): Result<User>
    suspend fun observeUser(id: String): Flow<Result<User>>
    suspend fun validateUsername(username: String): Result<Boolean>
    suspend fun getUsersByIds(ids: List<String>): Result<List<User>>
    suspend fun createUser(user: User): Result<Unit>
    suspend fun updateUser(user: User): Result<Unit>
    suspend fun addFriend(userId: String, friendId: String): Result<Unit>
    suspend fun removeFriend(userId: String, friendId: String): Result<Unit>
    suspend fun deleteUser(id: String): Result<Unit>
}

