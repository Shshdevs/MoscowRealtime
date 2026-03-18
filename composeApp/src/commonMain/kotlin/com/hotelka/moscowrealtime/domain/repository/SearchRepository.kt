package com.hotelka.moscowrealtime.domain.repository

import com.hotelka.moscowrealtime.data.dto.UserDto
import com.hotelka.moscowrealtime.domain.model.Location
import com.hotelka.moscowrealtime.domain.model.Quest
import com.hotelka.moscowrealtime.domain.model.SearchResult
import com.hotelka.moscowrealtime.domain.model.User

interface SearchRepository {
    suspend fun searchUserFriends(userId:String, query: String): Result<List<User>>
    suspend fun searchUsers(query: String): Result<List<User>>
    suspend fun searchQuests(query: String): Result<List<Quest>>
    suspend fun search(query: String): Result<SearchResult>

    suspend fun searchLocations(query: String): Result<List<Location>>
    suspend fun searchUsersWithIds(query: String, ids:List<String>): Result<List<User>>
}