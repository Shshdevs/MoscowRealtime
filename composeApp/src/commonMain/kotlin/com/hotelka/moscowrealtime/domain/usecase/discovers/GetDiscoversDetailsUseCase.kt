package com.hotelka.moscowrealtime.domain.usecase.discovers

import com.hotelka.moscowrealtime.domain.model.Discover
import com.hotelka.moscowrealtime.domain.usecase.locations.GetLocationsUseCase
import com.hotelka.moscowrealtime.domain.usecase.user.GetUsersByIdsUseCase
import com.hotelka.moscowrealtime.domain.mappers.detailDiscover
import com.hotelka.moscowrealtime.domain.model.detailed.DiscoverDetailed
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetDiscoversDetailsUseCase(
    private val getUsersByIdsUseCase: GetUsersByIdsUseCase,
    private val getLocationsUseCase: GetLocationsUseCase
) {
    suspend operator fun invoke(discovers: List<Discover>):List<DiscoverDetailed> = coroutineScope {
        if (discovers.isEmpty()) return@coroutineScope emptyList()

        val locationIds = discovers.map { it.detections[0].locationId }.distinct()
        val authorsIds = discovers.map { it.userAuthor }.distinct()
        val locationsDeferred = async {
            val locations = getLocationsUseCase(locationIds).getOrDefault(emptyList())
            locations.associateBy { it.id }
        }

        val authorsDeferred = async {
            val users = getUsersByIdsUseCase(authorsIds).getOrDefault(emptyList())
            users.associateBy { it.id }
        }
        val locations = locationsDeferred.await()
        val authors = authorsDeferred.await()
        discovers.map { discover ->
            detailDiscover(
                discover,
                authors[discover.userAuthor],
                locations[discover.detections[0].locationId]
            )
        }
    }
}