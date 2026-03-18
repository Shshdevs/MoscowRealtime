package com.hotelka.moscowrealtime.domain.model.api

data class OSRMApiData(
    var code: String?,
    var routes: List<Routes>,
    var waypoints: List<Waypoints>
)

data class Legs(
    var steps: List<String>,
    var weight: Double?,
    var summary: String?,
    var duration: Double?,
    var distance: Double?
)

data class Geometry(
    var coordinates: List<ArrayList<Double>>,
    var type: String?
)

data class Routes(
    var legs: List<Legs>,
    var weightName: String?,
    var geometry: Geometry?,
    var weight: Double?,
    var duration: Double?,
    var distance: Double?
)

data class Waypoints(
    var hint: String?,
    var location: List<Double>,
    var name: String?,
    var distance: Double?
)