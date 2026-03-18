package com.hotelka.moscowrealtime.data.remote

import com.hotelka.moscowrealtime.data.dto.UserDto
import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    suspend fun getUser(id: String): Result<UserDto>
    suspend fun getUsersByIds(ids: List<String>): Result<List<UserDto>>
    suspend fun validateUsername(username: String): Result<Boolean>
    suspend fun observeUser(id: String): Flow<Result<UserDto>>
    suspend fun updateUser(user: UserDto): Result<Unit>
    suspend fun createUser(user: UserDto): Result<Unit>
    suspend fun addFriend(userId: String, friendId: String): Result<Unit>
    suspend fun removeFriend(userId: String, friendId: String): Result<Unit>
    suspend fun deleteUser(id: String): Result<Unit>
}