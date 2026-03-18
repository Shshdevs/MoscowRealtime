package com.hotelka.moscowrealtime.domain.model.mapData

data class MapRouteState(
    val points: MutableList<GeoPoint> = mutableListOf(),
    var route: Route? = null
)