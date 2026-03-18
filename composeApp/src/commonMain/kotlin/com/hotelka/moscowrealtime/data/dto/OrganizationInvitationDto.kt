package com.hotelka.moscowrealtime.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class OrganizationInvitationDto(
    val id: String = "",
    val organizationId: String = "",
    val userTo: String = "",
    val toBeHost: Boolean,
    val isSent: Boolean = false
)