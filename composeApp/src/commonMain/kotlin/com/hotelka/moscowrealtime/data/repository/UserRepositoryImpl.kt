package com.hotelka.moscowrealtime.data.repository

import com.hotelka.moscowrealtime.data.remote.UserDataSource
import com.hotelka.moscowrealtime.data.mapper.toDomain
import com.hotelka.moscowrealtime.data.mapper.toDto
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val userDataSource: UserDataSource
) : UserRepository {

    override suspend fun getUser(id: String): Result<User> {
        return userDataSource.getUser(id).map { it.toDomain() }
    }
    override suspend fun observeUser(id: String): Flow<Result<User>> {
        return userDataSource.observeUser(id).map { result -> result.map { it.toDomain() } }
    }

    override suspend fun validateUsername(username: String): Result<Boolean> {
        return userDataSource.validateUsername(username)
    }

    override suspend fun getUsersByIds(ids: List<String>): Result<List<User>> {
        return userDataSource.getUsersByIds(ids).map { list -> list.map { it.toDomain() } }
    }
    override suspend fun createUser(user: User): Result<Unit> {
        return userDataSource.createUser(user.toDto())
    }

    override suspend fun updateUser(user: User): Result<Unit> {
        return userDataSource.updateUser(user.toDto())
    }

    override suspend fun addFriend(
        userId: String,
        friendId: String
    ): Result<Unit> {
       return  userDataSource.addFriend(userId, friendId)
    }

    override suspend fun removeFriend(
        userId: String,
        friendId: String
    ): Result<Unit> {
        return userDataSource.removeFriend(userId, friendId)
    }

    override suspend fun deleteUser(id: String): Result<Unit> {
        return userDataSource.deleteUser(id)
    }

}