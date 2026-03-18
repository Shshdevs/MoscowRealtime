package com.hotelka.moscowrealtime.data.remote

import com.hotelka.moscowrealtime.data.dto.DiscoverDto
import kotlinx.coroutines.flow.Flow

interface DiscoveriesDataSource {
    suspend fun getDiscover(discoverId: String):Result<DiscoverDto>
    suspend fun getDiscoversByIds(discoverIds: List<String>):Result<List<DiscoverDto>>
    suspend fun gerUserDiscoveries(userId: String): Result<List<DiscoverDto>>
    suspend fun observeUserDiscoveries(userId: String): Flow<Result<List<DiscoverDto>>>
    suspend fun deleteUserDiscover(discoveryId: String): Result<Unit>
    suspend fun deleteUserDiscovers(userId: String): Result<Unit>

}