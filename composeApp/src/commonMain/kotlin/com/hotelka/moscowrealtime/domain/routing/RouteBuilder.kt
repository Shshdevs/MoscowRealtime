package com.hotelka.moscowrealtime.domain.routing

import com.hotelka.moscowrealtime.domain.model.mapData.GeoPoint
import com.hotelka.moscowrealtime.domain.model.mapData.Route
import com.hotelka.moscowrealtime.domain.repository.OSRMRepository

class RouteBuilder(
    private val osrmRepository: OSRMRepository
) {
    suspend fun buildRoute(points: List<GeoPoint>): Result<Route> {
        if (points.size < 2)
            return Result.failure(Exception("At least 2 points required"))
        return osrmRepository.setDirections(points).map { api ->
            val route = api.routes.first()
            val geometry = route.geometry?.coordinates?.map {
                GeoPoint(
                    lat = it[1],
                    lon = it[0],
                    title = ""
                )
            } ?: emptyList()

            Route(
                distance = route.distance?.toFloat() ?: 0f,
                duration = route.duration?.toFloat() ?: 0f,
                geometry = geometry,
            )
        }
    }
}