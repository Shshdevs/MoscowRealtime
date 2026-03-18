package com.hotelka.moscowrealtime.data.remote

import com.hotelka.moscowrealtime.data.dto.api.DetailedEventDto
import com.hotelka.moscowrealtime.data.dto.api.EventDto
import com.hotelka.moscowrealtime.data.dto.api.PlaceDetailedDto

interface KudaGoDataSource {
    suspend fun fetchEvents(location: String, actualSince: Long, orderBy:String): Result<List<EventDto>>
    suspend fun fetchEventsInRadius(location: String, actualSince: Long, orderBy:String, lat:Double, lon:Double, radius:Int): Result<List<EventDto>>
    suspend fun getEventDetailing(id: Int): Result<DetailedEventDto>
    suspend fun getPlaceDetailed(id: Int): Result<PlaceDetailedDto>
}