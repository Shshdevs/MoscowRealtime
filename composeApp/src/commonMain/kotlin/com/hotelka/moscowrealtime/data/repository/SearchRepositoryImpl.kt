package com.hotelka.moscowrealtime.data.repository

import com.hotelka.moscowrealtime.data.remote.SearchDataSource
import com.hotelka.moscowrealtime.data.mapper.LocationMapper
import com.hotelka.moscowrealtime.data.mapper.QuestMapper
import com.hotelka.moscowrealtime.data.mapper.toDomain
import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.domain.model.SearchResult
import com.hotelka.moscowrealtime.domain.model.User
import com.hotelka.moscowrealtime.domain.repository.SearchRepository

class SearchRepositoryImpl(
    private val searchDataSource: SearchDataSource,
    private val questMapper: QuestMapper,
    private val locationMapper: LocationMapper
) : SearchRepository {
    override suspend fun searchUserFriends(
        userId: String,
        query: String
    ): Result<List<User>> {
        return searchDataSource.searchUserFriends(userId, query)
            .map { list -> list.map { it.toDomain() } }
    }

    override suspend fun searchUsers(query: String): Result<List<User>> {
        return searchDataSource.searchUsers(query).map { list -> list.map { it.toDomain() } }
    }

    override suspend fun searchQuests(query: String): Result<List<Quest>> {
        return searchDataSource.searchQuests(query).map { list -> list.map { questMapper.toDomain(it) } }
    }

    override suspend fun search(query: String): Result<SearchResult> {
        return searchDataSource.search(query).map { it.toDomain() }
    }

    override suspend fun searchLocations(query: String): Result<List<Location>> {
        return searchDataSource.searchLocations(query).map { list -> list.map { locationMapper.toDomain(it) } }
    }

    override suspend fun searchUsersWithIds(
        query: String,
        ids: List<String>
    ): Result<List<User>> {
        return searchDataSource.searchUsersWithIds(query, ids).map { users -> users.map { it.toDomain() } }
    }
}