package com.hotelka.moscowrealtime.data.remote

import co.touchlab.kermit.Logger
import com.hotelka.moscowrealtime.data.dto.LocationDto
import com.hotelka.moscowrealtime.data.dto.QuestDto
import com.hotelka.moscowrealtime.data.dto.SearchResultDto
import com.hotelka.moscowrealtime.data.dto.UserDto
import dev.gitlive.firebase.firestore.FieldPath
import dev.gitlive.firebase.firestore.FirebaseFirestore

class SearchDataSourceImpl(
    private val firestore: FirebaseFirestore
) : SearchDataSource {

    override suspend fun searchUserFriends(
        userId: String,
        query: String
    ): Result<List<UserDto>> = try {
        val query = query.lowercase().split(' ')
        val users = firestore.collection(USERS_COLLECTION).where {
            "friends" contains userId
        }.get().documents.map { it.data<UserDto>() }
            .filter { it.search_prefixes.containsAll(query) }
        Result.success(users)
    } catch (e: Exception) {
        Result.failure(Exception("Error Searching Friends", e))
    }

    override suspend fun searchLocations(query: String): Result<List<LocationDto>> = try {
        if (query.isBlank()){
            val locations = firestore.collection(LOCATIONS_COLLECTION).limit(10)
                .get().documents.map { it.data<LocationDto>() }
            Result.success(locations)
        } else {
            val query = query.lowercase().split(' ')
            val locations = firestore.collection(LOCATIONS_COLLECTION).limit(10).where {
                "search_prefixes" containsAny query
            }.get().documents.map { it.data<LocationDto>() }
            Result.success(locations)
        }
    } catch (e: Exception) {
        Result.failure(Exception("Error searching locations", e))
    }

    override suspend fun searchUsers(query: String): Result<List<UserDto>> = try {
        if (query.isEmpty()) getRecommendedUsers() else getUsersWithQuery(query)
    } catch (e: Exception) {
        Result.failure(Exception("Error Searching Users", e))
    }

    override suspend fun searchQuests(query: String): Result<List<QuestDto>> = try {
        if (query.isNotEmpty()) {
            val query = query.lowercase().split(' ')
            val quests = firestore.collection(QUESTS_COLLECTION).where {
                FieldPath("search_prefixes") containsAny query
            }.get().documents.map { it.data<QuestDto>() }
            Result.success(quests)
        } else {
            val quests = firestore.collection(QUESTS_COLLECTION).limit(20).get().documents
                .map { it.data<QuestDto>() }
            Result.success(quests)
        }
    } catch (e: Exception) {
        Logger.withTag("Index url").d { e.toString() }
        Result.failure(Exception("Error Searching quests", e))
    }

    override suspend fun searchUsersWithIds(
        query: String,
        ids: List<String>
    ): Result<List<UserDto>> = try {
        Logger.withTag("Search participants").d { "$ids\n Query: $query" }
        if (ids.isEmpty()) {
            Result.success(emptyList())
        } else if(query.isBlank()){
            val users = firestore.collection(USERS_COLLECTION).where {
                ("id" inArray ids)
            }.get().documents.map { it.data<UserDto>() }
            Result.success(users)
        }
        else {
            val query = query.lowercase().split(' ')
            val users = firestore.collection(USERS_COLLECTION).where {
                ("id" inArray ids)
            }.get().documents.map { it.data<UserDto>() }
                .filter { it.search_prefixes.containsAll(query) }
            Result.success(users)
        }
    } catch (e: Exception) {
        Logger.withTag("Index url").d { e.toString() }
        Result.failure(Exception("Error Searching quests", e))
    }

    override suspend fun search(query: String): Result<SearchResultDto> {
        return Result.success(SearchResultDto())
    }

    private suspend fun getUsersWithQuery(query: String): Result<List<UserDto>> = try {
        val query = query.lowercase().split(' ')
        val users = firestore.collection(USERS_COLLECTION).where {
            FieldPath("search_prefixes") containsAny query
        }.get().documents.map { it.data<UserDto>() }
        Result.success(users)
    } catch (e: Exception) {
        Result.failure(Exception("Error Searching Users", e))
    }

    private suspend fun getRecommendedUsers(): Result<List<UserDto>> = try {
        val docs = firestore.collection(USERS_COLLECTION).limit(20)
            .get().documents.map { it.data<UserDto>() }
        Result.success(docs)
    } catch (e: Exception) {
        Result.failure(Exception("Error Searching Users", e))
    }

    companion object {
        private const val LOCATIONS_COLLECTION = "locations"
        private const val USERS_COLLECTION = "users"
        private const val QUESTS_COLLECTION = "quests"

    }
}