package com.hotelka.moscowrealtime.domain.repository

import com.hotelka.moscowrealtime.domain.model.api.DetailedEvent
import com.hotelka.moscowrealtime.domain.model.api.Event
import com.hotelka.moscowrealtime.domain.model.api.PlaceDetailed

interface KudaGoRepository {
    suspend fun fetchEvents(location: String, actualSince: Long, orderBy:String): Result<List<Event>>
    suspend fun fetchEventInRadius(location: String, actualSince: Long, orderBy:String, lat:Double, lon:Double, radius:Int): Result<List<Event>>
    suspend fun getEventDetailing(id: Int): Result<DetailedEvent>
    suspend fun getPlaceDetailed(id: Int): Result<PlaceDetailed>
}