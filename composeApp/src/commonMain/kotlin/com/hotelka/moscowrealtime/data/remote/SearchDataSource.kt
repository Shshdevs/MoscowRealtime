package com.hotelka.moscowrealtime.data.remote

import com.hotelka.moscowrealtime.data.dto.LocationDto
import com.hotelka.moscowrealtime.data.dto.QuestDto
import com.hotelka.moscowrealtime.data.dto.SearchResultDto
import com.hotelka.moscowrealtime.data.dto.UserDto

interface SearchDataSource {
    suspend fun searchUserFriends(userId: String, query: String): Result<List<UserDto>>
    suspend fun searchUsers(query: String): Result<List<UserDto>>
    suspend fun searchQuests(query: String): Result<List<QuestDto>>
    suspend fun searchUsersWithIds(query: String, ids:List<String>): Result<List<UserDto>>
    suspend fun searchLocations(query: String): Result<List<LocationDto>>
    suspend fun search(query: String): Result<SearchResultDto>
}