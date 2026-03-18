package com.hotelka.moscowrealtime.data.repository

import com.hotelka.moscowrealtime.data.remote.DiscoveriesDataSource
import com.hotelka.moscowrealtime.data.mapper.api.toDomain
import com.hotelka.moscowrealtime.domain.model.Discover
import com.hotelka.moscowrealtime.domain.repository.DiscoveriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DiscoveriesRepositoryImpl(
    private val discoveriesDataSource: DiscoveriesDataSource
): DiscoveriesRepository {
    override suspend fun getDiscover(discoverId: String): Result<Discover> {
        return discoveriesDataSource.getDiscover(discoverId).map { it.toDomain() }
    }

    override suspend fun getDiscoversByIds(discoverIds: List<String>): Result<List<Discover>> {
        return discoveriesDataSource.getDiscoversByIds(discoverIds).map { list -> list.map { it.toDomain() } }
    }

    override suspend fun gerUserDiscoveries(userId: String): Result<List<Discover>> {
        return discoveriesDataSource.gerUserDiscoveries(userId).map { list -> list.map { it.toDomain() }}
    }

    override suspend fun observeUserDiscoveries(userId: String): Flow<Result<List<Discover>>> {
        return discoveriesDataSource.observeUserDiscoveries(userId).map { flow -> flow.map { list -> list.map { it.toDomain() } } }
    }

    override suspend fun deleteUserDiscover(discoveryId: String): Result<Unit> {
        return discoveriesDataSource.deleteUserDiscover(discoveryId)
    }

    override suspend fun deleteUserDiscovers(userId: String): Result<Unit> {
        return discoveriesDataSource.deleteUserDiscovers(userId)
    }
}