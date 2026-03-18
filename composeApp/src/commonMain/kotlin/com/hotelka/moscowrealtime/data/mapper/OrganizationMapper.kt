package com.hotelka.moscowrealtime.data.mapper

import com.hotelka.moscowrealtime.data.dto.OrganizationDto
import com.hotelka.moscowrealtime.domain.model.Organization

fun Organization.toDto(): OrganizationDto = OrganizationDto(
    id = id,
    name = name,
    usersHosts = usersHosts,
    users = users
)

fun OrganizationDto.toDomain(): Organization = Organization(
    id = id,
    name = name,
    usersHosts = usersHosts,
    users = users
)