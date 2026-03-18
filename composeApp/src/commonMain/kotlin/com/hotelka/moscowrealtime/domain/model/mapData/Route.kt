package com.hotelka.moscowrealtime.domain.model.mapData

data class Route(
    val distance: Float,
    val duration: Float,
    val geometry: List<GeoPoint>,
)

