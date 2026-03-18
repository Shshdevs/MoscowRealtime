package com.hotelka.moscowrealtime.data.mapper.api

import com.hotelka.moscowrealtime.data.dto.api.GeometryDto
import com.hotelka.moscowrealtime.data.dto.api.LegsDto
import com.hotelka.moscowrealtime.data.dto.api.OSRMApiDataDto
import com.hotelka.moscowrealtime.data.dto.api.RoutesDto
import com.hotelka.moscowrealtime.data.dto.api.WaypointsDto
import com.hotelka.moscowrealtime.domain.model.api.Geometry
import com.hotelka.moscowrealtime.domain.model.api.Legs
import com.hotelka.moscowrealtime.domain.model.api.OSRMApiData
import com.hotelka.moscowrealtime.domain.model.api.Routes
import com.hotelka.moscowrealtime.domain.model.api.Waypoints

fun OSRMApiData.toDto(): OSRMApiDataDto = OSRMApiDataDto(
    code = this.code,
    routes = this.routes?.map { it.toDto() }?.toCollection(ArrayList()) ?: arrayListOf(),
    waypoints = this.waypoints?.map { it.toDto() }?.toCollection(ArrayList()) ?: arrayListOf()
)

fun Routes.toDto(): RoutesDto = RoutesDto(
    legs = this.legs?.map { it.toDto() }?.toCollection(ArrayList()) ?: arrayListOf(),
    weightName = this.weightName,
    geometry = this.geometry?.toDto(),
    weight = this.weight,
    duration = this.duration,
    distance = this.distance
)

fun Legs.toDto(): LegsDto = LegsDto(
    steps = this.steps?.toCollection(ArrayList()) ?: arrayListOf(),
    weight = this.weight,
    summary = this.summary,
    duration = this.duration,
    distance = this.distance
)

fun Geometry.toDto(): GeometryDto = GeometryDto(
    coordinates = this.coordinates?.map { innerList ->
        innerList?.toCollection(ArrayList()) ?: arrayListOf()
    }?.toCollection(ArrayList()) ?: arrayListOf(),
    type = this.type
)

fun Waypoints.toDto(): WaypointsDto = WaypointsDto(
    hint = this.hint,
    location = this.location?.toCollection(ArrayList()) ?: arrayListOf(),
    name = this.name,
    distance = this.distance
)

fun OSRMApiDataDto.toDomain(): OSRMApiData = OSRMApiData(
    code = this.code,
    routes = this.routes.map { it.toDomain() },
    waypoints = this.waypoints.map { it.toDomain() }
)

fun RoutesDto.toDomain(): Routes = Routes(
    legs = this.legs.map { it.toDomain() },
    weightName = this.weightName,
    geometry = this.geometry?.toDomain(),
    weight = this.weight,
    duration = this.duration,
    distance = this.distance
)

fun LegsDto.toDomain(): Legs = Legs(
    steps = this.steps,
    weight = this.weight,
    summary = this.summary,
    duration = this.duration,
    distance = this.distance
)

fun GeometryDto.toDomain(): Geometry = Geometry(
    coordinates = this.coordinates.map { innerList -> innerList },
    type = this.type
)

fun WaypointsDto.toDomain(): Waypoints = Waypoints(
    hint = this.hint,
    location = this.location,
    name = this.name,
    distance = this.distance
)