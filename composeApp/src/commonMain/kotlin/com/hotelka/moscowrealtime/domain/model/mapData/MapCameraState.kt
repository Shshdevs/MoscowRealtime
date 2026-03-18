package com.hotelka.moscowrealtime.domain.model.mapData

data class MapCameraState(
    val lat: Double,
    val lon: Double,
    val zoom: Float,
    val azimuth: Float,
    val tilt: Float
)
