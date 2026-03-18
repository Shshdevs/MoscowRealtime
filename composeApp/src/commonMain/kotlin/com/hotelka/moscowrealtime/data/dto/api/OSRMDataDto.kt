package com.hotelka.moscowrealtime.data.dto.api

import kotlinx.serialization.Serializable

@Serializable
data class OSRMApiDataDto(
    var code: String? = null,
    var routes: ArrayList<RoutesDto> = arrayListOf(),
    var waypoints: ArrayList<WaypointsDto> = arrayListOf()
)

@Serializable
data class LegsDto(
    var steps: List<String> = arrayListOf(),
    var weight: Double? = null,
    var summary: String? = null,
    var duration: Double? = null,
    var distance: Double? = null
)

@Serializable
data class GeometryDto(
    var coordinates: ArrayList<ArrayList<Double>> = arrayListOf(),
    var type: String? = null
)

@Serializable
data class RoutesDto(
    var legs: ArrayList<LegsDto> = arrayListOf(),
    var weightName: String? = null,
    var geometry: GeometryDto? = GeometryDto(),
    var weight: Double? = null,
    var duration: Double? = null,
    var distance: Double? = null
)
@Serializable
data class WaypointsDto(
    var hint: String? = null,
    var location: ArrayList<Double> = arrayListOf(),
    var name: String? = null,
    var distance: Double? = null
)