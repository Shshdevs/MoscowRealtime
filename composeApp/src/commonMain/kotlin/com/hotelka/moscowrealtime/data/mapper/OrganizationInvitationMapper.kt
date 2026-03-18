package com.hotelka.moscowrealtime.data.mapper

import com.hotelka.moscowrealtime.data.dto.OrganizationInvitationDto
import com.hotelka.moscowrealtime.domain.model.OrganizationInvitation

fun OrganizationInvitation.toDto(): OrganizationInvitationDto = OrganizationInvitationDto(
    id = id,
    organizationId = organizationId,
    userTo = userTo,
    toBeHost = toBeHost,
    isSent = false
)

fun OrganizationInvitationDto.toDomain(): OrganizationInvitation = OrganizationInvitation(
    id = id,
    organizationId = organizationId,
    userTo = userTo,
    toBeHost = toBeHost
)