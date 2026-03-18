package com.hotelka.moscowrealtime.data.repository

import com.hotelka.moscowrealtime.data.remote.KudaGoDataSource
import com.hotelka.moscowrealtime.data.mapper.api.toDomain
import com.hotelka.moscowrealtime.domain.model.api.DetailedEvent
import com.hotelka.moscowrealtime.domain.model.api.Event
import com.hotelka.moscowrealtime.domain.model.api.PlaceDetailed
import com.hotelka.moscowrealtime.domain.repository.KudaGoRepository

class KudaGoRepositoryImpl(
    private val kudaGoDataSource: KudaGoDataSource
): KudaGoRepository {
    override suspend fun fetchEvents(
        location: String,
        actualSince: Long,
        orderBy: String
    ): Result<List<Event>> {
        return kudaGoDataSource.fetchEvents(location, actualSince, orderBy).map { list -> list.map { it.toDomain() } }
    }

    override suspend fun fetchEventInRadius(
        location: String,
        actualSince: Long,
        orderBy: String,
        lat: Double,
        lon: Double,
        radius: Int
    ): Result<List<Event>> {
        return kudaGoDataSource.fetchEventsInRadius(location, actualSince, orderBy, lat, lon, radius).map { list -> list.map { it.toDomain() } }
    }



    override suspend fun getEventDetailing(id: Int): Result<DetailedEvent> {
       return kudaGoDataSource.getEventDetailing(id).map { it.toDomain() }
    }

    override suspend fun getPlaceDetailed(id: Int): Result<PlaceDetailed> {
        return kudaGoDataSource.getPlaceDetailed(id).map { it.toDomain() }
    }
}