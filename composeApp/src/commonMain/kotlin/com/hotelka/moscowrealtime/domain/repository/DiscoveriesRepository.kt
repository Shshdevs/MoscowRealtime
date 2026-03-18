package com.hotelka.moscowrealtime.domain.repository

import com.hotelka.moscowrealtime.domain.model.Discover
import kotlinx.coroutines.flow.Flow

interface DiscoveriesRepository {
    suspend fun getDiscover(discoverId: String):Result<Discover>
    suspend fun getDiscoversByIds(discoverIds: List<String>): Result<List<Discover>>
    suspend fun gerUserDiscoveries(userId: String): Result<List<Discover>>
    suspend fun observeUserDiscoveries(userId: String): Flow<Result<List<Discover>>>
    suspend fun deleteUserDiscover(discoveryId: String): Result<Unit>
    suspend fun deleteUserDiscovers(userId: String): Result<Unit>
}